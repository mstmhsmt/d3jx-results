package org.apache.cassandra.db.compaction;

import java.util.*;
import org.apache.cassandra.config.Config;
import org.apache.cassandra.db.rows.UnfilteredRowIterator;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.cassandra.db.partitions.Partition;
import org.apache.cassandra.io.sstable.format.SSTableReader;
import org.apache.cassandra.io.util.FileDataInput;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.schema.CompactionParams.TombstoneOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cassandra.db.*;
import org.apache.cassandra.utils.AlwaysPresentFilter;
import org.apache.cassandra.utils.OverlapIterator;
import org.apache.cassandra.utils.concurrent.Refs;
import static org.apache.cassandra.db.lifecycle.SSTableIntervalTree.buildIntervals;
import java.util.function.LongPredicate;

public class CompactionController extends AbstractCompactionController {

    private static final Logger logger = LoggerFactory.getLogger(CompactionController.class);

    private static final String NEVER_PURGE_TOMBSTONES_PROPERTY = Config.PROPERTY_PREFIX + "never_purge_tombstones";

    static final boolean NEVER_PURGE_TOMBSTONES = Boolean.getBoolean(NEVER_PURGE_TOMBSTONES_PROPERTY);

    private final boolean compactingRepaired;

    private Refs<SSTableReader> overlappingSSTables;

    private OverlapIterator<PartitionPosition, SSTableReader> overlapIterator;

    private final Iterable<SSTableReader> compacting;

    private final RateLimiter limiter;

    private final long minTimestamp;

    final Map<SSTableReader, FileDataInput> openDataFiles = new HashMap<>();

    protected CompactionController(ColumnFamilyStore cfs, int maxValue) {
        this(cfs, null, maxValue);
    }

    public CompactionController(ColumnFamilyStore cfs, Set<SSTableReader> compacting, int gcBefore) {
        this(cfs, compacting, gcBefore, null, cfs.getCompactionStrategyManager().getCompactionParams().tombstoneOption());
    }

    public CompactionController(ColumnFamilyStore cfs, Set<SSTableReader> compacting, int gcBefore, RateLimiter limiter, TombstoneOption tombstoneOption) {
        super(cfs, gcBefore, tombstoneOption);
        this.compacting = compacting;
        this.limiter = limiter;
        compactingRepaired = compacting != null && compacting.stream().allMatch(SSTableReader::isRepaired);
        this.minTimestamp = compacting != null && !compacting.isEmpty() ? compacting.stream().mapToLong(SSTableReader::getMinTimestamp).min().getAsLong() : 0;
        refreshOverlaps();
        if (NEVER_PURGE_TOMBSTONES)
            logger.warn("You are running with -Dcassandra.never_purge_tombstones=true, this is dangerous!");
    }

    public void maybeRefreshOverlaps() {
        if (NEVER_PURGE_TOMBSTONES) {
            logger.debug("not refreshing overlaps - running with -D{}=true", NEVER_PURGE_TOMBSTONES_PROPERTY);
            return;
        }
        if (cfs.getNeverPurgeTombstones()) {
            logger.debug("not refreshing overlaps for {}.{} - neverPurgeTombstones is enabled", cfs.keyspace.getName(), cfs.getTableName());
            return;
        }
        for (SSTableReader reader : overlappingSSTables) {
            if (reader.isMarkedCompacted()) {
                refreshOverlaps();
                return;
            }
        }
    }

    void refreshOverlaps() {
        if (NEVER_PURGE_TOMBSTONES || cfs.getNeverPurgeTombstones())
            return;
        if (this.overlappingSSTables != null)
            close();
        if (compacting == null)
            overlappingSSTables = Refs.tryRef(Collections.<SSTableReader>emptyList());
        else
            overlappingSSTables = cfs.getAndReferenceOverlappingLiveSSTables(compacting);
        this.overlapIterator = new OverlapIterator<>(buildIntervals(overlappingSSTables));
    }

    public Set<SSTableReader> getFullyExpiredSSTables() {
        return getFullyExpiredSSTables(cfs, compacting, overlappingSSTables, gcBefore, ignoreOverlaps());
    }

    static public Set<SSTableReader> getFullyExpiredSSTables(ColumnFamilyStore cfStore, Iterable<SSTableReader> compacting, Iterable<SSTableReader> overlapping, int gcBefore, boolean ignoreOverlaps) {
        logger.trace("Checking droppable sstables in {}", cfStore);
        if (NEVER_PURGE_TOMBSTONES || compacting == null || cfStore.getNeverPurgeTombstones())
            return Collections.<SSTableReader>emptySet();
        if (cfStore.getCompactionStrategyManager().onlyPurgeRepairedTombstones() && !Iterables.all(compacting, SSTableReader::isRepaired))
            return Collections.emptySet();
        if (ignoreOverlaps) {
            Set<SSTableReader> fullyExpired = new HashSet<>();
            for (SSTableReader candidate : compacting) {
                if (candidate.getSSTableMetadata().maxLocalDeletionTime < gcBefore) {
                    fullyExpired.add(candidate);
                    logger.trace("Dropping overlap ignored expired SSTable {} (maxLocalDeletionTime={}, gcBefore={})", candidate, candidate.getSSTableMetadata().maxLocalDeletionTime, gcBefore);
                }
            }
            return fullyExpired;
        }
        List<SSTableReader> candidates = new ArrayList<>();
        long minTimestamp = Long.MAX_VALUE;
        for (SSTableReader sstable : overlapping) {
            if (sstable.getSSTableMetadata().maxLocalDeletionTime >= gcBefore)
                minTimestamp = Math.min(minTimestamp, sstable.getMinTimestamp());
        }
        for (SSTableReader candidate : compacting) {
            if (candidate.getSSTableMetadata().maxLocalDeletionTime < gcBefore)
                candidates.add(candidate);
            else
                minTimestamp = Math.min(minTimestamp, candidate.getMinTimestamp());
        }
        for (Memtable memtable : cfStore.getTracker().getView().getAllMemtables()) {
            if (memtable.getMinTimestamp() != Memtable.NO_MIN_TIMESTAMP)
                minTimestamp = Math.min(minTimestamp, memtable.getMinTimestamp());
        }
        Iterator<SSTableReader> iterator = candidates.iterator();
        while (iterator.hasNext()) {
            SSTableReader candidate = iterator.next();
            if (candidate.getMaxTimestamp() >= minTimestamp) {
                iterator.remove();
            } else {
                logger.trace("Dropping expired SSTable {} (maxLocalDeletionTime={}, gcBefore={})", candidate, candidate.getSSTableMetadata().maxLocalDeletionTime, gcBefore);
            }
        }
        return new HashSet<>(candidates);
    }

    static public Set<SSTableReader> getFullyExpiredSSTables(ColumnFamilyStore cfStore, Iterable<SSTableReader> compacting, Iterable<SSTableReader> overlapping, int gcBefore) {
        return getFullyExpiredSSTables(cfStore, compacting, overlapping, gcBefore, false);
    }

    @Override
    public LongPredicate getPurgeEvaluator(DecoratedKey key) {
        if (NEVER_PURGE_TOMBSTONES || !compactingRepaired() || cfs.getNeverPurgeTombstones())
            return time -> false;
        overlapIterator.update(key);
        Set<SSTableReader> filteredSSTables = overlapIterator.overlaps();
        Iterable<Memtable> memtables = cfs.getTracker().getView().getAllMemtables();
        long minTimestampSeen = Long.MAX_VALUE;
        boolean hasTimestamp = false;
        for (SSTableReader sstable : filteredSSTables) {
            if (sstable.getBloomFilter() instanceof AlwaysPresentFilter && sstable.getPosition(key, SSTableReader.Operator.EQ, false) != null || sstable.getBloomFilter().isPresent(key)) {
                minTimestampSeen = Math.min(minTimestampSeen, sstable.getMinTimestamp());
                hasTimestamp = true;
            }
        }
        for (Memtable memtable : memtables) {
            if (memtable.getMinTimestamp() != Memtable.NO_MIN_TIMESTAMP) {
                Partition partition = memtable.getPartition(key);
                if (partition != null) {
                    minTimestampSeen = Math.min(minTimestampSeen, partition.stats().minTimestamp);
                    hasTimestamp = true;
                }
            }
        }
        if (!hasTimestamp)
            return time -> true;
        else {
            final long finalTimestamp = minTimestampSeen;
            return time -> time < finalTimestamp;
        }
    }

    public void close() {
        if (overlappingSSTables != null)
            overlappingSSTables.release();
        FileUtils.closeQuietly(openDataFiles.values());
        openDataFiles.clear();
    }

    public boolean compactingRepaired() {
        return !cfs.getCompactionStrategyManager().onlyPurgeRepairedTombstones() || compactingRepaired;
    }

    boolean provideTombstoneSources() {
        return tombstoneOption != TombstoneOption.NONE;
    }

    public Iterable<UnfilteredRowIterator> shadowSources(DecoratedKey key, boolean tombstoneOnly) {
        if (!provideTombstoneSources() || !compactingRepaired() || NEVER_PURGE_TOMBSTONES || cfs.getNeverPurgeTombstones())
            return null;
        overlapIterator.update(key);
        return Iterables.filter(Iterables.transform(overlapIterator.overlaps(), reader -> getShadowIterator(reader, key, tombstoneOnly)), Predicates.notNull());
    }

    @SuppressWarnings("resource")
    private UnfilteredRowIterator getShadowIterator(SSTableReader reader, DecoratedKey key, boolean tombstoneOnly) {
        if (reader.isMarkedSuspect() || reader.getMaxTimestamp() <= minTimestamp || tombstoneOnly && !reader.mayHaveTombstones())
            return null;
        RowIndexEntry<?> position = reader.getPosition(key, SSTableReader.Operator.EQ);
        if (position == null)
            return null;
        FileDataInput dfile = openDataFiles.computeIfAbsent(reader, this::openDataFile);
        return reader.simpleIterator(dfile, key, position, tombstoneOnly);
    }

    protected boolean ignoreOverlaps() {
        return false;
    }

    private FileDataInput openDataFile(SSTableReader reader) {
        return limiter != null ? reader.openDataReader(limiter) : reader.openDataReader();
    }
}
