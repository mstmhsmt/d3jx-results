package org.apache.cassandra.db;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.cassandra.cache.IRowCacheEntry;
import org.apache.cassandra.cache.RowCacheKey;
import org.apache.cassandra.cache.RowCacheSentinel;
import org.apache.cassandra.concurrent.Stage;
import org.apache.cassandra.concurrent.StageManager;
import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.lifecycle.*;
import org.apache.cassandra.db.filter.*;
import org.apache.cassandra.db.partitions.*;
import org.apache.cassandra.db.rows.*;
import org.apache.cassandra.db.transform.Transformation;
import org.apache.cassandra.exceptions.RequestExecutionException;
import org.apache.cassandra.io.sstable.format.SSTableReader;
import org.apache.cassandra.io.util.DataInputPlus;
import org.apache.cassandra.io.util.DataOutputPlus;
import org.apache.cassandra.metrics.TableMetrics;
import org.apache.cassandra.net.MessageOut;
import org.apache.cassandra.net.MessagingService;
import org.apache.cassandra.schema.IndexMetadata;
import org.apache.cassandra.service.CacheService;
import org.apache.cassandra.service.ClientState;
import org.apache.cassandra.service.StorageProxy;
import org.apache.cassandra.service.pager.*;
import org.apache.cassandra.thrift.ThriftResultsMerger;
import org.apache.cassandra.tracing.Tracing;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.SearchIterator;
import org.apache.cassandra.utils.btree.BTreeSet;

/**
 * A read command that selects a (part of a) single partition.
 */
public class SinglePartitionReadCommand extends ReadCommand {
  protected static final SelectionDeserializer selectionDeserializer = new Deserializer();

  private final DecoratedKey partitionKey;

  private final ClusteringIndexFilter clusteringIndexFilter;

  private int oldestUnrepairedTombstone = Integer.MAX_VALUE;

  public SinglePartitionReadCommand(boolean isDigest, int digestVersion, boolean isForThrift, CFMetaData metadata, int nowInSec, ColumnFilter columnFilter, RowFilter rowFilter, DataLimits limits, DecoratedKey partitionKey, ClusteringIndexFilter clusteringIndexFilter) {
    super(Kind.SINGLE_PARTITION, isDigest, digestVersion, isForThrift, metadata, nowInSec, columnFilter, rowFilter, limits);
    assert partitionKey.getPartitioner() == metadata.partitioner;
    this.partitionKey = partitionKey;
    this.clusteringIndexFilter = clusteringIndexFilter;
  }

  /**
     * Creates a new read command on a single partition.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param columnFilter the column filter to use for the query.
     * @param rowFilter the row filter to use for the query.
     * @param limits the limits to use for the query.
     * @param partitionKey the partition key for the partition to query.
     * @param clusteringIndexFilter the clustering index filter to use for the query.
     *
     * @return a newly created read command.
     */
  public static SinglePartitionReadCommand create(CFMetaData metadata, int nowInSec, ColumnFilter columnFilter, RowFilter rowFilter, DataLimits limits, DecoratedKey partitionKey, ClusteringIndexFilter clusteringIndexFilter) {
    return create(false, metadata, nowInSec, columnFilter, rowFilter, limits, partitionKey, clusteringIndexFilter);
  }

  /**
     * Creates a new read command on a single partition for thrift.
     *
     * @param isForThrift whether the query is for thrift or not.
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param columnFilter the column filter to use for the query.
     * @param rowFilter the row filter to use for the query.
     * @param limits the limits to use for the query.
     * @param partitionKey the partition key for the partition to query.
     * @param clusteringIndexFilter the clustering index filter to use for the query.
     *
     * @return a newly created read command.
     */
  public static SinglePartitionReadCommand create(boolean isForThrift, CFMetaData metadata, int nowInSec, ColumnFilter columnFilter, RowFilter rowFilter, DataLimits limits, DecoratedKey partitionKey, ClusteringIndexFilter clusteringIndexFilter) {
    return new SinglePartitionReadCommand(false, 0, isForThrift, metadata, nowInSec, columnFilter, rowFilter, limits, partitionKey, clusteringIndexFilter);
  }

  /**
     * Creates a new read command on a single partition.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param key the partition key for the partition to query.
     * @param columnFilter the column filter to use for the query.
     * @param filter the clustering index filter to use for the query.
     *
     * @return a newly created read command. The returned command will use no row filter and have no limits.
     */
  public static SinglePartitionReadCommand create(CFMetaData metadata, int nowInSec, DecoratedKey key, ColumnFilter columnFilter, ClusteringIndexFilter filter) {
    return create(metadata, nowInSec, columnFilter, RowFilter.NONE, DataLimits.NONE, key, filter);
  }

  /**
     * Creates a new read command that queries a single partition in its entirety.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param key the partition key for the partition to query.
     *
     * @return a newly created read command that queries all the rows of {@code key}.
     */
  public static SinglePartitionReadCommand fullPartitionRead(CFMetaData metadata, int nowInSec, DecoratedKey key) {
    return SinglePartitionReadCommand.create(metadata, nowInSec, key, Slices.ALL);
  }

  /**
     * Creates a new read command that queries a single partition in its entirety.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param key the partition key for the partition to query.
     *
     * @return a newly created read command that queries all the rows of {@code key}.
     */
  public static SinglePartitionReadCommand fullPartitionRead(CFMetaData metadata, int nowInSec, ByteBuffer key) {
    return SinglePartitionReadCommand.create(metadata, nowInSec, metadata.decorateKey(key), Slices.ALL);
  }

  /**
     * Creates a new single partition slice command for the provided single slice.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param key the partition key for the partition to query.
     * @param slice the slice of rows to query.
     *
     * @return a newly created read command that queries {@code slice} in {@code key}. The returned query will
     * query every columns for the table (without limit or row filtering) and be in forward order.
     */
  public static SinglePartitionReadCommand create(CFMetaData metadata, int nowInSec, DecoratedKey key, Slice slice) {
    return create(metadata, nowInSec, key, Slices.with(metadata.comparator, slice));
  }

  /**
     * Creates a new single partition slice command for the provided slices.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param key the partition key for the partition to query.
     * @param slices the slices of rows to query.
     *
     * @return a newly created read command that queries the {@code slices} in {@code key}. The returned query will
     * query every columns for the table (without limit or row filtering) and be in forward order.
     */
  public static SinglePartitionReadCommand create(CFMetaData metadata, int nowInSec, DecoratedKey key, Slices slices) {
    ClusteringIndexSliceFilter filter = new ClusteringIndexSliceFilter(slices, false);
    return SinglePartitionReadCommand.create(metadata, nowInSec, ColumnFilter.all(metadata), RowFilter.NONE, DataLimits.NONE, key, filter);
  }

  /**
     * Creates a new single partition slice command for the provided slices.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param key the partition key for the partition to query.
     * @param slices the slices of rows to query.
     *
     * @return a newly created read command that queries the {@code slices} in {@code key}. The returned query will
     * query every columns for the table (without limit or row filtering) and be in forward order.
     */
  public static SinglePartitionReadCommand create(CFMetaData metadata, int nowInSec, ByteBuffer key, Slices slices) {
    return create(metadata, nowInSec, metadata.decorateKey(key), slices);
  }

  /**
     * Creates a new single partition name command for the provided rows.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param key the partition key for the partition to query.
     * @param names the clustering for the rows to query.
     *
     * @return a newly created read command that queries the {@code names} in {@code key}. The returned query will
     * query every columns (without limit or row filtering) and be in forward order.
     */
  public static SinglePartitionReadCommand create(CFMetaData metadata, int nowInSec, DecoratedKey key, NavigableSet<Clustering> names) {
    ClusteringIndexNamesFilter filter = new ClusteringIndexNamesFilter(names, false);
    return SinglePartitionReadCommand.create(metadata, nowInSec, ColumnFilter.all(metadata), RowFilter.NONE, DataLimits.NONE, key, filter);
  }

  /**
     * Creates a new single partition name command for the provided row.
     *
     * @param metadata the table to query.
     * @param nowInSec the time in seconds to use are "now" for this query.
     * @param key the partition key for the partition to query.
     * @param name the clustering for the row to query.
     *
     * @return a newly created read command that queries {@code name} in {@code key}. The returned query will
     * query every columns (without limit or row filtering).
     */
  public static SinglePartitionReadCommand create(CFMetaData metadata, int nowInSec, DecoratedKey key, Clustering name) {
    return create(metadata, nowInSec, key, FBUtilities.singleton(name, metadata.comparator));
  }

  public SinglePartitionReadCommand copy() {
    return new SinglePartitionReadCommand(isDigestQuery(), digestVersion(), isForThrift(), metadata(), nowInSec(), columnFilter(), rowFilter(), limits(), partitionKey(), clusteringIndexFilter());
  }

  public DecoratedKey partitionKey() {
    return partitionKey;
  }

  public ClusteringIndexFilter clusteringIndexFilter() {
    return clusteringIndexFilter;
  }

  public ClusteringIndexFilter clusteringIndexFilter(DecoratedKey key) {
    return clusteringIndexFilter;
  }

  public long getTimeout() {
    return DatabaseDescriptor.getReadRpcTimeout();
  }

  public boolean selectsKey(DecoratedKey key) {
    if (!this.partitionKey().equals(key)) {
      return false;
    }
    return rowFilter().partitionKeyRestrictionsAreSatisfiedBy(key, metadata().getKeyValidator());
  }

  public boolean selectsClustering(DecoratedKey key, Clustering clustering) {
    if (clustering == Clustering.STATIC_CLUSTERING) {
      return !columnFilter().fetchedColumns().statics.isEmpty();
    }
    if (!clusteringIndexFilter().selects(clustering)) {
      return false;
    }
    return rowFilter().clusteringKeyRestrictionsAreSatisfiedBy(clustering);
  }

  /**
     * Returns a new command suitable to paging from the last returned row.
     *
     * @param lastReturned the last row returned by the previous page. The newly created command
     * will only query row that comes after this (in query order). This can be {@code null} if this
     * is the first page.
     * @param limits the limits to use for the page to query.
     *
     * @return the newly create command.
     */
  public SinglePartitionReadCommand forPaging(Clustering lastReturned, DataLimits limits) {
    assert !isDigestQuery();
    return create(isForThrift(), metadata(), nowInSec(), columnFilter(), rowFilter(), limits, partitionKey(), lastReturned == null ? clusteringIndexFilter() : clusteringIndexFilter.forPaging(metadata().comparator, lastReturned, false));
  }

  public SinglePartitionReadCommand withUpdatedLimit(DataLimits newLimits) {
    return new SinglePartitionReadCommand(isDigestQuery(), digestVersion(), isForThrift(), metadata(), nowInSec(), columnFilter(), rowFilter(), newLimits, partitionKey, clusteringIndexFilter);
  }

  public PartitionIterator execute(ConsistencyLevel consistency, ClientState clientState, long queryStartNanoTime) throws RequestExecutionException {
    return StorageProxy.read(Group.one(this), consistency, clientState, queryStartNanoTime);
  }

  public SinglePartitionPager getPager(PagingState pagingState, int protocolVersion) {
    return getPager(this, pagingState, protocolVersion);
  }

  private static SinglePartitionPager getPager(SinglePartitionReadCommand command, PagingState pagingState, int protocolVersion) {
    return new SinglePartitionPager(command, pagingState, protocolVersion);
  }

  protected void recordLatency(TableMetrics metric, long latencyNanos) {
    metric.readLatency.addNano(latencyNanos);
  }

  @SuppressWarnings(value = { "resource" }) protected UnfilteredPartitionIterator queryStorage(final ColumnFamilyStore cfs, ReadExecutionController executionController) {
    UnfilteredRowIterator partition = cfs.isRowCacheEnabled() ? getThroughCache(cfs, executionController) : queryMemtableAndDisk(cfs, executionController);
    return new SingletonUnfilteredPartitionIterator(partition, isForThrift());
  }

  /**
     * Fetch the rows requested if in cache; if not, read it from disk and cache it.
     * <p>
     * If the partition is cached, and the filter given is within its bounds, we return
     * from cache, otherwise from disk.
     * <p>
     * If the partition is is not cached, we figure out what filter is "biggest", read
     * that from disk, then filter the result and either cache that or return it.
     */
  private UnfilteredRowIterator getThroughCache(ColumnFamilyStore cfs, ReadExecutionController executionController) {
    assert !cfs.isIndex();
    assert cfs.isRowCacheEnabled() : String.format("Row cache is not enabled on table [%s]", cfs.name);
    RowCacheKey key = new RowCacheKey(metadata().ksAndCFName, partitionKey());
    IRowCacheEntry cached = CacheService.instance.rowCache.get(key);
    if (cached != null) {
      if (cached instanceof RowCacheSentinel) {
        Tracing.trace("Row cache miss (race)");
        cfs.metric.rowCacheMiss.inc();
        return queryMemtableAndDisk(cfs, executionController);
      }
      CachedPartition cachedPartition = (CachedPartition) cached;
      if (cfs.isFilterFullyCoveredBy(clusteringIndexFilter(), limits(), cachedPartition, nowInSec())) {
        cfs.metric.rowCacheHit.inc();
        Tracing.trace("Row cache hit");
        UnfilteredRowIterator unfilteredRowIterator = clusteringIndexFilter().getUnfilteredRowIterator(columnFilter(), cachedPartition);
        cfs.metric.updateSSTableIterated(0);
        return unfilteredRowIterator;
      }
      cfs.metric.rowCacheHitOutOfRange.inc();
      Tracing.trace("Ignoring row cache as cached value could not satisfy query");
      return queryMemtableAndDisk(cfs, executionController);
    }
    cfs.metric.rowCacheMiss.inc();
    Tracing.trace("Row cache miss");
    boolean cacheFullPartitions = metadata().clusteringColumns().size() > 0 ? metadata().params.caching.cacheAllRows() : metadata().params.caching.cacheRows();
    if (cacheFullPartitions || clusteringIndexFilter().isHeadFilter()) {
      RowCacheSentinel sentinel = new RowCacheSentinel();
      boolean sentinelSuccess = CacheService.instance.rowCache.putIfAbsent(key, sentinel);
      boolean sentinelReplaced = false;
      try {
        int rowsToCache = metadata().params.caching.rowsPerPartitionToCache();
        @SuppressWarnings(value = { "resource" }) UnfilteredRowIterator iter = SinglePartitionReadCommand.fullPartitionRead(metadata(), nowInSec(), partitionKey()).queryMemtableAndDisk(cfs, executionController);
        try {
          CachedPartition toCache = CachedBTreePartition.create(DataLimits.cqlLimits(rowsToCache).filter(iter, nowInSec()), nowInSec());
          if (sentinelSuccess && !toCache.isEmpty()) {
            Tracing.trace("Caching {} rows", toCache.rowCount());
            CacheService.instance.rowCache.replace(key, sentinel, toCache);
            sentinelReplaced = true;
          }
          UnfilteredRowIterator cacheIterator = clusteringIndexFilter().getUnfilteredRowIterator(columnFilter(), toCache);
          if (cacheFullPartitions) {
            assert !iter.hasNext();
            iter.close();
            return cacheIterator;
          }
          return UnfilteredRowIterators.concat(cacheIterator, clusteringIndexFilter().filterNotIndexed(columnFilter(), iter));
        } catch (RuntimeException | Error e) {
          iter.close();
          throw e;
        }
      }  finally {
        if (sentinelSuccess && !sentinelReplaced) {
          cfs.invalidateCachedPartition(key);
        }
      }
    }
    Tracing.trace("Fetching data but not populating cache as query does not query from the start of the partition");
    return queryMemtableAndDisk(cfs, executionController);
  }

  /**
     * Queries both memtable and sstables to fetch the result of this query.
     * <p>
     * Please note that this method:
     *   1) does not check the row cache.
     *   2) does not apply the query limit, nor the row filter (and so ignore 2ndary indexes).
     *      Those are applied in {@link ReadCommand#executeLocally}.
     *   3) does not record some of the read metrics (latency, scanned cells histograms) nor
     *      throws TombstoneOverwhelmingException.
     * It is publicly exposed because there is a few places where that is exactly what we want,
     * but it should be used only where you know you don't need thoses things.
     * <p>
     * Also note that one must have created a {@code ReadExecutionController} on the queried table and we require it as
     * a parameter to enforce that fact, even though it's not explicitlly used by the method.
     */
  public UnfilteredRowIterator queryMemtableAndDisk(ColumnFamilyStore cfs, ReadExecutionController executionController) {
    assert executionController != null && executionController.validForReadOn(cfs);
    Tracing.trace("Executing single-partition query on {}", cfs.name);
    return queryMemtableAndDiskInternal(cfs);
  }

  @Override protected int oldestUnrepairedTombstone() {
    return oldestUnrepairedTombstone;
  }

  private UnfilteredRowIterator queryMemtableAndDiskInternal(ColumnFamilyStore cfs) {
    if (clusteringIndexFilter() instanceof ClusteringIndexNamesFilter && !queriesMulticellType()) {
      return queryMemtableAndSSTablesInTimestampOrder(cfs, (ClusteringIndexNamesFilter) clusteringIndexFilter());
    }
    Tracing.trace("Acquiring sstable references");
    ColumnFamilyStore.ViewFragment view = cfs.select(View.select(SSTableSet.LIVE, partitionKey()));
    List<UnfilteredRowIterator> iterators = new ArrayList<>(Iterables.size(view.memtables) + view.sstables.size());
    ClusteringIndexFilter filter = clusteringIndexFilter();
    long minTimestamp = Long.MAX_VALUE;
    try {
      for (Memtable memtable : view.memtables) {
        Partition partition = memtable.getPartition(partitionKey());
        if (partition == null) {
          continue;
        }
        minTimestamp = Math.min(minTimestamp, memtable.getMinTimestamp());
        @SuppressWarnings(value = { "resource" }) UnfilteredRowIterator iter = filter.getUnfilteredRowIterator(columnFilter(), partition);
        oldestUnrepairedTombstone = Math.min(oldestUnrepairedTombstone, partition.stats().minLocalDeletionTime);
        iterators.add(isForThrift() ? ThriftResultsMerger.maybeWrap(iter, nowInSec()) : iter);
      }
      Collections.sort(view.sstables, SSTableReader.maxTimestampComparator);
      long mostRecentPartitionTombstone = Long.MIN_VALUE;
      int nonIntersectingSSTables = 0;
      List<SSTableReader> skippedSSTablesWithTombstones = null;
      for (SSTableReader sstable : view.sstables) {
        if (sstable.getMaxTimestamp() < mostRecentPartitionTombstone) {
          break;
        }
        if (!shouldInclude(sstable)) {
          nonIntersectingSSTables++;
          if (sstable.hasTombstones()) {
            if (skippedSSTablesWithTombstones == null) {
              skippedSSTablesWithTombstones = new ArrayList<>();
            }
            skippedSSTablesWithTombstones.add(sstable);
          }
          continue;
        }
        minTimestamp = Math.min(minTimestamp, sstable.getMinTimestamp());
        @SuppressWarnings(value = { "resource" }) UnfilteredRowIteratorWithLowerBound iter = makeIterator(cfs, sstable, true);
        if (!sstable.isRepaired()) {
          oldestUnrepairedTombstone = Math.min(oldestUnrepairedTombstone, sstable.getMinLocalDeletionTime());
        }
        iterators.add(iter);
        mostRecentPartitionTombstone = Math.max(mostRecentPartitionTombstone, iter.partitionLevelDeletion().markedForDeleteAt());
      }
      int includedDueToTombstones = 0;
      if (skippedSSTablesWithTombstones != null) {
        for (SSTableReader sstable : skippedSSTablesWithTombstones) {
          if (sstable.getMaxTimestamp() <= minTimestamp) {
            continue;
          }
          @SuppressWarnings(value = { "resource" }) UnfilteredRowIteratorWithLowerBound iter = makeIterator(cfs, sstable, false);
          if (!sstable.isRepaired()) {
            oldestUnrepairedTombstone = Math.min(oldestUnrepairedTombstone, sstable.getMinLocalDeletionTime());
          }
          iterators.add(iter);
          includedDueToTombstones++;
        }
      }
      if (Tracing.isTracing()) {
        Tracing.trace("Skipped {}/{} non-slice-intersecting sstables, included {} due to tombstones", nonIntersectingSSTables, view.sstables.size(), includedDueToTombstones);
      }
      if (iterators.isEmpty()) {
        return EmptyIterators.unfilteredRow(cfs.metadata, partitionKey(), filter.isReversed());
      }
      StorageHook.instance.reportRead(cfs.metadata.cfId, partitionKey());
      return withStateTracking(withSSTablesIterated(iterators, cfs.metric));
    } catch (RuntimeException | Error e) {
      try {
        FBUtilities.closeAll(iterators);
      } catch (Exception suppressed) {
        e.addSuppressed(suppressed);
      }
      throw e;
    }
  }

  private boolean shouldInclude(SSTableReader sstable) {
    if (!columnFilter().fetchedColumns().statics.isEmpty()) {
      return true;
    }
    return clusteringIndexFilter().shouldInclude(sstable);
  }

  private UnfilteredRowIteratorWithLowerBound makeIterator(ColumnFamilyStore cfs, final SSTableReader sstable, boolean applyThriftTransformation) {
    return StorageHook.instance.makeRowIteratorWithLowerBound(cfs, partitionKey(), sstable, clusteringIndexFilter(), columnFilter(), isForThrift(), nowInSec(), applyThriftTransformation);
  }

  private boolean queriesMulticellType() {
    for (ColumnDefinition column : columnFilter().fetchedColumns()) {
      if (column.type.isMultiCell() || column.type.isCounter()) {
        return true;
      }
    }
    return false;
  }

  /**
     * Return a wrapped iterator that when closed will update the sstables iterated and READ sample metrics.
     * Note that we cannot use the Transformations framework because they greedily get the static row, which
     * would cause all iterators to be initialized and hence all sstables to be accessed.
     */
  private UnfilteredRowIterator withSSTablesIterated(List<UnfilteredRowIterator> iterators, TableMetrics metrics) {
    @SuppressWarnings(value = { "resource" }) UnfilteredRowIterator merged = UnfilteredRowIterators.merge(iterators, nowInSec());
    if (!merged.isEmpty()) {
      DecoratedKey key = merged.partitionKey();
      metrics.samplers.get(TableMetrics.Sampler.READS).addSample(key.getKey(), key.hashCode(), 1);
    }
    class UpdateSstablesIterated extends Transformation {
      public void onPartitionClose() {
        int sstablesIterated = (int) iterators.stream().filter((it) -> it instanceof LazilyInitializedUnfilteredRowIterator).filter((it) -> ((LazilyInitializedUnfilteredRowIterator) it).initialized()).count();
        metrics.updateSSTableIterated(sstablesIterated);
        Tracing.trace("Merged data from memtables and {} sstables", sstablesIterated);
      }
    }
    ;
    return Transformation.apply(merged, new UpdateSstablesIterated());
  }

  /**
     * Do a read by querying the memtable(s) first, and then each relevant sstables sequentially by order of the sstable
     * max timestamp.
     *
     * This is used for names query in the hope of only having to query the 1 or 2 most recent query and then knowing nothing
     * more recent could be in the older sstables (which we can only guarantee if we know exactly which row we queries, and if
     * no collection or counters are included).
     * This method assumes the filter is a {@code ClusteringIndexNamesFilter}.
     */
  private UnfilteredRowIterator queryMemtableAndSSTablesInTimestampOrder(ColumnFamilyStore cfs, ClusteringIndexNamesFilter filter) {
    Tracing.trace("Acquiring sstable references");
    ColumnFamilyStore.ViewFragment view = cfs.select(View.select(SSTableSet.LIVE, partitionKey()));
    ImmutableBTreePartition result = null;
    Tracing.trace("Merging memtable contents");
    for (Memtable memtable : view.memtables) {
      Partition partition = memtable.getPartition(partitionKey());
      if (partition == null) {
        continue;
      }
      try (UnfilteredRowIterator iter = filter.getUnfilteredRowIterator(columnFilter(), partition)) {
        if (iter.isEmpty()) {
          continue;
        }
        result = add(isForThrift() ? ThriftResultsMerger.maybeWrap(iter, nowInSec()) : iter, result, filter, false);
      }
    }
    Collections.sort(view.sstables, SSTableReader.maxTimestampComparator);
    int sstablesIterated = 0;
    boolean onlyUnrepaired = true;
    for (SSTableReader sstable : view.sstables) {
      if (result != null && sstable.getMaxTimestamp() < result.partitionLevelDeletion().markedForDeleteAt()) {
        break;
      }
      long currentMaxTs = sstable.getMaxTimestamp();
      filter = reduceFilter(filter, result, currentMaxTs);
      if (filter == null) {
        break;
      }
      if (!shouldInclude(sstable)) {
        if (!sstable.hasTombstones()) {
          continue;
        }
        sstable.incrementReadCount();
        try (UnfilteredRowIterator iter = StorageHook.instance.makeRowIterator(cfs, sstable, partitionKey(), Slices.ALL, columnFilter(), filter.isReversed(), isForThrift())) {
          if (iter.partitionLevelDeletion().isLive()) {
            sstablesIterated++;
            result = add(UnfilteredRowIterators.noRowsIterator(iter.metadata(), iter.partitionKey(), Rows.EMPTY_STATIC_ROW, iter.partitionLevelDeletion(), filter.isReversed()), result, filter, sstable.isRepaired());
          }
        }
        continue;
      }
      Tracing.trace("Merging data from sstable {}", sstable.descriptor.generation);
      sstable.incrementReadCount();
      try (UnfilteredRowIterator iter = StorageHook.instance.makeRowIterator(cfs, sstable, partitionKey(), filter.getSlices(metadata()), columnFilter(), filter.isReversed(), isForThrift())) {
        if (iter.isEmpty()) {
          continue;
        }
        if (sstable.isRepaired()) {
          onlyUnrepaired = false;
        }
        sstablesIterated++;
        result = add(isForThrift() ? ThriftResultsMerger.maybeWrap(iter, nowInSec()) : iter, result, filter, sstable.isRepaired());
      }
    }
    cfs.metric.updateSSTableIterated(sstablesIterated);
    if (result == null || result.isEmpty()) {
      return EmptyIterators.unfilteredRow(metadata(), partitionKey(), false);
    }
    DecoratedKey key = result.partitionKey();
    cfs.metric.samplers.get(TableMetrics.Sampler.READS).addSample(key.getKey(), key.hashCode(), 1);
    StorageHook.instance.reportRead(cfs.metadata.cfId, partitionKey());
    if (sstablesIterated > cfs.getMinimumCompactionThreshold() && onlyUnrepaired && !cfs.isAutoCompactionDisabled() && cfs.getCompactionStrategyManager().shouldDefragment()) {
      Tracing.trace("Defragmenting requested data");
      try (UnfilteredRowIterator iter = result.unfilteredIterator(columnFilter(), Slices.ALL, false)) {
        final Mutation mutation = new Mutation(PartitionUpdate.fromIterator(iter, columnFilter()));
        StageManager.getStage(Stage.MUTATION).execute(() -> {
          Keyspace.open(mutation.getKeyspaceName()).apply(mutation, false, false);
        });
      }
    }
    return withStateTracking(result.unfilteredIterator(columnFilter(), Slices.ALL, clusteringIndexFilter().isReversed()));
  }

  private ImmutableBTreePartition add(UnfilteredRowIterator iter, ImmutableBTreePartition result, ClusteringIndexNamesFilter filter, boolean isRepaired) {
    if (!isRepaired) {
      oldestUnrepairedTombstone = Math.min(oldestUnrepairedTombstone, iter.stats().minLocalDeletionTime);
    }
    int maxRows = Math.max(filter.requestedRows().size(), 1);
    if (result == null) {
      return ImmutableBTreePartition.create(iter, maxRows);
    }
    try (UnfilteredRowIterator merged = UnfilteredRowIterators.merge(Arrays.asList(iter, result.unfilteredIterator(columnFilter(), Slices.ALL, filter.isReversed())), nowInSec())) {
      return ImmutableBTreePartition.create(merged, maxRows);
    }
  }

  private ClusteringIndexNamesFilter reduceFilter(ClusteringIndexNamesFilter filter, Partition result, long sstableTimestamp) {
    if (result == null) {
      return filter;
    }
    SearchIterator<Clustering, Row> searchIter = result.searchIterator(columnFilter(), false);
    PartitionColumns columns = columnFilter().fetchedColumns();
    NavigableSet<Clustering> clusterings = filter.requestedRows();
    boolean removeStatic = false;
    if (!columns.statics.isEmpty()) {
      Row staticRow = searchIter.next(Clustering.STATIC_CLUSTERING);
      removeStatic = staticRow != null && canRemoveRow(staticRow, columns.statics, sstableTimestamp);
    }
    NavigableSet<Clustering> toRemove = null;
    for (Clustering clustering : clusterings) {
      if (!searchIter.hasNext()) {
        break;
      }
      Row row = searchIter.next(clustering);
      if (row == null || !canRemoveRow(row, columns.regulars, sstableTimestamp)) {
        continue;
      }
      if (toRemove == null) {
        toRemove = new TreeSet<>(result.metadata().comparator);
      }
      toRemove.add(clustering);
    }
    if (!removeStatic && toRemove == null) {
      return filter;
    }
    boolean hasNoMoreStatic = columns.statics.isEmpty() || removeStatic;
    boolean hasNoMoreClusterings = clusterings.isEmpty() || (toRemove != null && toRemove.size() == clusterings.size());
    if (hasNoMoreStatic && hasNoMoreClusterings) {
      return null;
    }
    if (toRemove != null) {
      BTreeSet.Builder<Clustering> newClusterings = BTreeSet.builder(result.metadata().comparator);
      newClusterings.addAll(Sets.difference(clusterings, toRemove));
      clusterings = newClusterings.build();
    }
    return new ClusteringIndexNamesFilter(clusterings, filter.isReversed());
  }

  private boolean canRemoveRow(Row row, Columns requestedColumns, long sstableTimestamp) {
    if (row.primaryKeyLivenessInfo().isEmpty() || row.primaryKeyLivenessInfo().timestamp() <= sstableTimestamp) {
      return false;
    }
    for (ColumnDefinition column : requestedColumns) {
      Cell cell = row.getCell(column);
      if (cell == null || cell.timestamp() <= sstableTimestamp) {
        return false;
      }
    }
    return true;
  }

  @Override public String toString() {
    return String.format("Read(%s.%s columns=%s rowFilter=%s limits=%s key=%s filter=%s, nowInSec=%d)", metadata().ksName, metadata().cfName, columnFilter(), rowFilter(), limits(), metadata().getKeyValidator().getString(partitionKey().getKey()), clusteringIndexFilter.toString(metadata()), nowInSec());
  }

  public MessageOut<ReadCommand> createMessage(int version) {
    return new MessageOut<>(MessagingService.Verb.READ, this, readSerializer);
  }

  protected void appendCQLWhereClause(StringBuilder sb) {
    sb.append(" WHERE ");
    sb.append(ColumnDefinition.toCQLString(metadata().partitionKeyColumns())).append(" = ");
    DataRange.appendKeyString(sb, metadata().getKeyValidator(), partitionKey().getKey());
    if (!rowFilter().isEmpty()) {
      sb.append(" AND ").append(rowFilter());
    }
    String filterString = clusteringIndexFilter().toCQLString(metadata());
    if (!filterString.isEmpty()) {
      sb.append(" AND ").append(filterString);
    }
  }

  protected void serializeSelection(DataOutputPlus out, int version) throws IOException {
    metadata().getKeyValidator().writeValue(partitionKey().getKey(), out);
    ClusteringIndexFilter.serializer.serialize(clusteringIndexFilter(), out, version);
  }

  protected long selectionSerializedSize(int version) {
    return metadata().getKeyValidator().writtenLength(partitionKey().getKey()) + ClusteringIndexFilter.serializer.serializedSize(clusteringIndexFilter(), version);
  }

  public static class Group implements ReadQuery {
    public final List<SinglePartitionReadCommand> commands;

    private final DataLimits limits;

    private final int nowInSec;

    public Group(List<SinglePartitionReadCommand> commands, DataLimits limits) {
      assert !commands.isEmpty();
      this.commands = commands;
      this.limits = limits;
      this.nowInSec = commands.get(0).nowInSec();
      for (int i = 1; i < commands.size(); i++) {
        assert commands.get(i).nowInSec() == nowInSec;
      }
    }

    public static Group one(SinglePartitionReadCommand command) {
      return new Group(Collections.singletonList(command), command.limits());
    }

    public PartitionIterator execute(ConsistencyLevel consistency, ClientState clientState, long queryStartNanoTime) throws RequestExecutionException {
      return StorageProxy.read(this, consistency, clientState, queryStartNanoTime);
    }

    public int nowInSec() {
      return nowInSec;
    }

    public DataLimits limits() {
      return limits;
    }

    public CFMetaData metadata() {
      return commands.get(0).metadata();
    }

    public ReadExecutionController executionController() {
      return commands.get(0).executionController();
    }

    public PartitionIterator executeInternal(ReadExecutionController controller) {
      return limits.filter(UnfilteredPartitionIterators.filter(executeLocally(controller, false), nowInSec), nowInSec);
    }

    public UnfilteredPartitionIterator executeLocally(ReadExecutionController executionController) {
      return executeLocally(executionController, true);
    }

    /**
         * Implementation of {@link ReadQuery#executeLocally(ReadExecutionController)}.
         *
         * @param executionController - the {@code ReadExecutionController} protecting the read.
         * @param sort - whether to sort the inner commands by partition key, required for merging the iterator
         *               later on. This will be false when called by {@link ReadQuery#executeInternal(ReadExecutionController)}
         *               because in this case it is safe to do so as there is no merging involved and we don't want to
         *               change the old behavior which was to not sort by partition.
         *
         * @return - the iterator that can be used to retrieve the query result.
         */
    private UnfilteredPartitionIterator executeLocally(ReadExecutionController executionController, boolean sort) {
      List<Pair<DecoratedKey, UnfilteredPartitionIterator>> partitions = new ArrayList<>(commands.size());
      for (SinglePartitionReadCommand cmd : commands) {
        partitions.add(Pair.of(cmd.partitionKey, cmd.executeLocally(executionController)));
      }
      if (sort) {
        Collections.sort(partitions, (p1, p2) -> p1.getLeft().compareTo(p2.getLeft()));
      }
      return UnfilteredPartitionIterators.concat(partitions.stream().map((p) -> p.getRight()).collect(Collectors.toList()));
    }

    public QueryPager getPager(PagingState pagingState, int protocolVersion) {
      if (commands.size() == 1) {
        return SinglePartitionReadCommand.getPager(commands.get(0), pagingState, protocolVersion);
      }
      return new MultiPartitionPager(this, pagingState, protocolVersion);
    }

    public boolean selectsKey(DecoratedKey key) {
      return Iterables.any(commands, (c) -> c.selectsKey(key));
    }

    public boolean selectsClustering(DecoratedKey key, Clustering clustering) {
      return Iterables.any(commands, (c) -> c.selectsClustering(key, clustering));
    }

    @Override public String toString() {
      return commands.toString();
    }
  }

  private static class Deserializer extends SelectionDeserializer {
    public ReadCommand deserialize(DataInputPlus in, int version, boolean isDigest, int digestVersion, boolean isForThrift, CFMetaData metadata, int nowInSec, ColumnFilter columnFilter, RowFilter rowFilter, DataLimits limits, Optional<IndexMetadata> index) throws IOException {
      DecoratedKey key = metadata.decorateKey(metadata.getKeyValidator().readValue(in, DatabaseDescriptor.getMaxValueSize()));
      ClusteringIndexFilter filter = ClusteringIndexFilter.serializer.deserialize(in, version, metadata);
      return new SinglePartitionReadCommand(isDigest, digestVersion, isForThrift, metadata, nowInSec, columnFilter, rowFilter, limits, key, filter);
    }
  }
}