package org.apache.cassandra.db;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import javax.management.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.common.util.concurrent.*;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.Uninterruptibles;
import org.json.simple.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cassandra.cache.*;
import org.apache.cassandra.concurrent.*;
import org.apache.cassandra.config.*;
import org.apache.cassandra.config.CFMetaData.SpeculativeRetry;
import org.apache.cassandra.db.commitlog.CommitLog;
import org.apache.cassandra.db.commitlog.ReplayPosition;
import org.apache.cassandra.db.compaction.*;
import org.apache.cassandra.db.composites.CellName;
import org.apache.cassandra.db.composites.CellNameType;
import org.apache.cassandra.db.composites.Composite;
import org.apache.cassandra.db.filter.ColumnSlice;
import org.apache.cassandra.db.filter.ExtendedFilter;
import org.apache.cassandra.db.filter.IDiskAtomFilter;
import org.apache.cassandra.db.filter.QueryFilter;
import org.apache.cassandra.db.filter.SliceQueryFilter;
import org.apache.cassandra.db.index.SecondaryIndex;
import org.apache.cassandra.db.index.SecondaryIndexManager;
import org.apache.cassandra.dht.*;
import org.apache.cassandra.dht.Range;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.io.FSReadError;
import org.apache.cassandra.io.FSWriteError;
import org.apache.cassandra.io.compress.CompressionParameters;
import org.apache.cassandra.io.sstable.Descriptor;
import org.apache.cassandra.io.sstable.*;
import org.apache.cassandra.io.sstable.format.*;
import org.apache.cassandra.io.sstable.metadata.CompactionMetadata;
import org.apache.cassandra.io.sstable.metadata.MetadataType;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.metrics.ColumnFamilyMetrics;
import org.apache.cassandra.service.CacheService;
import org.apache.cassandra.service.StorageService;
import org.apache.cassandra.streaming.StreamLockfile;
import org.apache.cassandra.tracing.Tracing;
import org.apache.cassandra.utils.*;
import org.apache.cassandra.utils.concurrent.OpOrder;
import org.apache.cassandra.utils.memory.MemtableAllocator;

public class ColumnFamilyStore implements ColumnFamilyStoreMBean {
  private static final Logger logger = LoggerFactory.getLogger(ColumnFamilyStore.class);

  private static final ExecutorService flushExecutor = new JMXEnabledThreadPoolExecutor(DatabaseDescriptor.getFlushWriters(), StageManager.KEEPALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("MemtableFlushWriter"), "internal");

  private static final ExecutorService postFlushExecutor = new JMXEnabledThreadPoolExecutor(1, StageManager.KEEPALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("MemtablePostFlush"), "internal");

  private static final ExecutorService reclaimExecutor = new JMXEnabledThreadPoolExecutor(1, StageManager.KEEPALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("MemtableReclaimMemory"), "internal");

  public final Keyspace keyspace;

  public final String name;

  public final CFMetaData metadata;

  public final IPartitioner partitioner;

  private final String mbeanName;

  private volatile boolean valid = true;

  /**
     * Memtables and SSTables on disk for this column family.
     *
     * We synchronize on the DataTracker to ensure isolation when we want to make sure
     * that the memtable we're acting on doesn't change out from under us.  I.e., flush
     * syncronizes on it to make sure it can submit on both executors atomically,
     * so anyone else who wants to make sure flush doesn't interfere should as well.
     */
  private final DataTracker data;

  public final OpOrder readOrdering = new OpOrder();

  private final AtomicInteger fileIndexGenerator = new AtomicInteger(0);

  public final SecondaryIndexManager indexManager;

  private volatile DefaultInteger minCompactionThreshold;

  private volatile DefaultInteger maxCompactionThreshold;

  private final WrappingCompactionStrategy compactionStrategyWrapper;

  public final Directories directories;

  public final ColumnFamilyMetrics metric;

  public volatile long sampleLatencyNanos;

  private final ScheduledFuture<?> latencyCalculator;

  public static void shutdownPostFlushExecutor() throws InterruptedException {
    postFlushExecutor.shutdown();
    postFlushExecutor.awaitTermination(60, TimeUnit.SECONDS);
  }

  public void reload() {
    if (!minCompactionThreshold.isModified()) {
      for (ColumnFamilyStore cfs : concatWithIndexes()) {
        cfs.minCompactionThreshold = new DefaultInteger(metadata.getMinCompactionThreshold());
      }
    }
    if (!maxCompactionThreshold.isModified()) {
      for (ColumnFamilyStore cfs : concatWithIndexes()) {
        cfs.maxCompactionThreshold = new DefaultInteger(metadata.getMaxCompactionThreshold());
      }
    }
    compactionStrategyWrapper.maybeReloadCompactionStrategy(metadata);
    scheduleFlush();
    indexManager.reload();
    if (data.getView().getCurrentMemtable().initialComparator != metadata.comparator) {
      switchMemtable();
    }
  }

  void scheduleFlush() {
    int period = metadata.getMemtableFlushPeriod();
    if (period > 0) {
      logger.debug("scheduling flush in {} ms", period);
      WrappedRunnable runnable = new WrappedRunnable() {
        protected void runMayThrow() throws Exception {
          synchronized (data) {
            Memtable current = data.getView().getCurrentMemtable();
            if (current.isExpired()) {
              if (current.isClean()) {
                scheduleFlush();
              } else {
                forceFlush();
              }
            }
          }
        }
      };
      ScheduledExecutors.scheduledTasks.schedule(runnable, period, TimeUnit.MILLISECONDS);
    }
  }

  public void setCompactionStrategyClass(String compactionStrategyClass) {
    try {
      metadata.compactionStrategyClass = CFMetaData.createCompactionStrategy(compactionStrategyClass);
      compactionStrategyWrapper.maybeReloadCompactionStrategy(metadata);
    } catch (ConfigurationException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public String getCompactionStrategyClass() {
    return metadata.compactionStrategyClass.getName();
  }

  public Map<String, String> getCompressionParameters() {
    return metadata.compressionParameters().asThriftOptions();
  }

  public void setCompressionParameters(Map<String, String> opts) {
    try {
      metadata.compressionParameters = CompressionParameters.create(opts);
    } catch (ConfigurationException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public void setCrcCheckChance(double crcCheckChance) {
    try {
      for (SSTableReader sstable : keyspace.getAllSSTables()) {
        if (sstable.compression) {
          sstable.getCompressionMetadata().parameters.setCrcCheckChance(crcCheckChance);
        }
      }
    } catch (ConfigurationException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private ColumnFamilyStore(Keyspace keyspace, String columnFamilyName, IPartitioner partitioner, int generation, CFMetaData metadata, Directories directories, boolean loadSSTables) {
    assert metadata != null : "null metadata for " + keyspace + ":" + columnFamilyName;
    this.keyspace = keyspace;
    name = columnFamilyName;
    this.metadata = metadata;
    this.minCompactionThreshold = new DefaultInteger(metadata.getMinCompactionThreshold());
    this.maxCompactionThreshold = new DefaultInteger(metadata.getMaxCompactionThreshold());
    this.partitioner = partitioner;
    this.directories = directories;
    this.indexManager = new SecondaryIndexManager(this);
    this.metric = new ColumnFamilyMetrics(this);
    fileIndexGenerator.set(generation);
    sampleLatencyNanos = DatabaseDescriptor.getReadRpcTimeout() / 2;
    CachingOptions caching = metadata.getCaching();
    logger.info("Initializing {}.{}", keyspace.getName(), name);
    data = new DataTracker(this);
    if (loadSSTables) {
      Directories.SSTableLister sstableFiles = directories.sstableLister().skipTemporary(true);
      Collection<SSTableReader> sstables = SSTableReader.openAll(sstableFiles.list().entrySet(), metadata, this.partitioner);
      data.addInitialSSTables(sstables);
    }
    if (caching.keyCache.isEnabled()) {
      CacheService.instance.keyCache.loadSaved(this);
    }
    this.compactionStrategyWrapper = new WrappingCompactionStrategy(this);
    if (maxCompactionThreshold.value() <= 0 || minCompactionThreshold.value() <= 0) {
      logger.warn("Disabling compaction strategy by setting compaction thresholds to 0 is deprecated, set the compaction option \'enabled\' to \'false\' instead.");
      this.compactionStrategyWrapper.disable();
    }
    for (ColumnDefinition info : metadata.allColumns()) {
      if (info.getIndexType() != null) {
        indexManager.addIndexedColumn(info);
      }
    }
    String type = this.partitioner instanceof LocalPartitioner ? "IndexColumnFamilies" : "ColumnFamilies";
    mbeanName = "org.apache.cassandra.db:type=" + type + ",keyspace=" + this.keyspace.getName() + ",columnfamily=" + name;
    try {
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      ObjectName nameObj = new ObjectName(mbeanName);
      mbs.registerMBean(this, nameObj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    logger.debug("retryPolicy for {} is {}", name, this.metadata.getSpeculativeRetry());
    latencyCalculator = ScheduledExecutors.optionalTasks.scheduleWithFixedDelay(new Runnable() {
      public void run() {
        SpeculativeRetry retryPolicy = ColumnFamilyStore.this.metadata.getSpeculativeRetry();
        switch (retryPolicy.type) {
          case PERCENTILE:
          assert metric.coordinatorReadLatency.durationUnit() == TimeUnit.MICROSECONDS;
          sampleLatencyNanos = (long) (metric.coordinatorReadLatency.getSnapshot().getValue(retryPolicy.value) * 1000d);
          break;
          case CUSTOM:
          sampleLatencyNanos = (long) (retryPolicy.value * 1000d * 1000d);
          break;
          default:
          sampleLatencyNanos = Long.MAX_VALUE;
          break;
        }
      }
    }, DatabaseDescriptor.getReadRpcTimeout(), DatabaseDescriptor.getReadRpcTimeout(), TimeUnit.MILLISECONDS);
  }

  /** call when dropping or renaming a CF. Performs mbean housekeeping and invalidates CFS to other operations */
  public void invalidate() {
    valid = false;
    try {
      unregisterMBean();
    } catch (Exception e) {
      JVMStabilityInspector.inspectThrowable(e);
      logger.warn("Failed unregistering mbean: {}", mbeanName, e);
    }
    latencyCalculator.cancel(false);
    compactionStrategyWrapper.shutdown();
    SystemKeyspace.removeTruncationRecord(metadata.cfId);
    data.unreferenceSSTables();
    indexManager.invalidate();
    invalidateCaches();
  }

  /**
     * Removes every SSTable in the directory from the DataTracker's view.
     * @param directory the unreadable directory, possibly with SSTables in it, but not necessarily.
     */
  void maybeRemoveUnreadableSSTables(File directory) {
    data.removeUnreadableSSTables(directory);
  }

  void unregisterMBean() throws MalformedObjectNameException, InstanceNotFoundException, MBeanRegistrationException {
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    ObjectName nameObj = new ObjectName(mbeanName);
    if (mbs.isRegistered(nameObj)) {
      mbs.unregisterMBean(nameObj);
    }
    metric.release();
  }

  public long getMinRowSize() {
    return metric.minRowSize.value();
  }

  public long getMaxRowSize() {
    return metric.maxRowSize.value();
  }

  public long getMeanRowSize() {
    return metric.meanRowSize.value();
  }

  public int getMeanColumns() {
    return data.getMeanColumns();
  }

  public static ColumnFamilyStore createColumnFamilyStore(Keyspace keyspace, String columnFamily, boolean loadSSTables) {
    return createColumnFamilyStore(keyspace, columnFamily, StorageService.getPartitioner(), Schema.instance.getCFMetaData(keyspace.getName(), columnFamily), loadSSTables);
  }

  public static ColumnFamilyStore createColumnFamilyStore(Keyspace keyspace, String columnFamily, IPartitioner partitioner, CFMetaData metadata) {
    return createColumnFamilyStore(keyspace, columnFamily, partitioner, metadata, true);
  }

  private static synchronized ColumnFamilyStore createColumnFamilyStore(Keyspace keyspace, String columnFamily, IPartitioner partitioner, CFMetaData metadata, boolean loadSSTables) {
    Directories directories = new Directories(metadata);
    Directories.SSTableLister lister = directories.sstableLister().includeBackups(true);
    List<Integer> generations = new ArrayList<Integer>();
    for (Map.Entry<Descriptor, Set<Component>> entry : lister.list().entrySet()) {
      Descriptor desc = entry.getKey();
      generations.add(desc.generation);
      if (!desc.isCompatible()) {
        throw new RuntimeException(String.format("Incompatible SSTable found. Current version %s is unable to read file: %s. Please run upgradesstables.", desc.getFormat().getLatestVersion(), desc));
      }
    }
    Collections.sort(generations);
    int value = (generations.size() > 0) ? (generations.get(generations.size() - 1)) : 0;
    return new ColumnFamilyStore(keyspace, columnFamily, partitioner, value, metadata, directories, loadSSTables);
  }

  /**
     * Removes unnecessary files from the cf directory at startup: these include temp files, orphans, zero-length files
     * and compacted sstables. Files that cannot be recognized will be ignored.
     */
  public static void scrubDataDirectories(CFMetaData metadata) {
    Directories directories = new Directories(metadata);
    FileFilter filter = new FileFilter() {
      public boolean accept(File pathname) {
        return pathname.getPath().endsWith(StreamLockfile.FILE_EXT);
      }
    };
    for (File dir : directories.getCFDirectories()) {
      File[] lockfiles = dir.listFiles(filter);
      if (lockfiles == null || lockfiles.length == 0) {
        continue;
      }
      logger.info("Removing SSTables from failed streaming session. Found {} files to cleanup.", lockfiles.length);
      for (File lockfile : lockfiles) {
        StreamLockfile streamLockfile = new StreamLockfile(lockfile);
        streamLockfile.cleanup();
        streamLockfile.delete();
      }
    }
    logger.debug("Removing compacted SSTable files from {} (see http://wiki.apache.org/cassandra/MemtableSSTable)", metadata.cfName);
    for (Map.Entry<Descriptor, Set<Component>> sstableFiles : directories.sstableLister().list().entrySet()) {
      Descriptor desc = sstableFiles.getKey();
      Set<Component> components = sstableFiles.getValue();
      if (desc.type.isTemporary) {
        SSTable.delete(desc, components);
        continue;
      }
      File dataFile = new File(desc.filenameFor(Component.DATA));
      if (components.contains(Component.DATA) && dataFile.length() > 0) {
        continue;
      }
      logger.warn("Removing orphans for {}: {}", desc, components);
      for (Component component : components) {
        FileUtils.deleteWithConfirm(desc.filenameFor(component));
      }
    }
    Pattern tmpCacheFilePattern = Pattern.compile(metadata.ksName + "-" + metadata.cfName + "-(Key|Row)Cache.*\\.tmp$");
    File dir = new File(DatabaseDescriptor.getSavedCachesLocation());
    if (dir.exists()) {
      assert dir.isDirectory();
      for (File file : dir.listFiles()) {
        if (tmpCacheFilePattern.matcher(file.getName()).matches()) {
          if (!file.delete()) {
            logger.warn("could not delete {}", file.getAbsolutePath());
          }
        }
      }
    }
    for (ColumnDefinition def : metadata.allColumns()) {
      if (def.isIndexed()) {
        CellNameType indexComparator = SecondaryIndex.getIndexComparator(metadata, def);
        if (indexComparator != null) {
          CFMetaData indexMetadata = CFMetaData.newIndexMetadata(metadata, def, indexComparator);
          scrubDataDirectories(indexMetadata);
        }
      }
    }
  }

  /**
     * Replacing compacted sstables is atomic as far as observers of DataTracker are concerned, but not on the
     * filesystem: first the new sstables are renamed to "live" status (i.e., the tmp marker is removed), then
     * their ancestors are removed.
     *
     * If an unclean shutdown happens at the right time, we can thus end up with both the new ones and their
     * ancestors "live" in the system.  This is harmless for normal data, but for counters it can cause overcounts.
     *
     * To prevent this, we record sstables being compacted in the system keyspace.  If we find unfinished
     * compactions, we remove the new ones (since those may be incomplete -- under LCS, we may create multiple
     * sstables from any given ancestor).
     */
  public static void removeUnfinishedCompactionLeftovers(CFMetaData metadata, Map<Integer, UUID> unfinishedCompactions) {
    Directories directories = new Directories(metadata);
    Set<Integer> allGenerations = new HashSet<>();
    for (Descriptor desc : directories.sstableLister().list().keySet()) {
      allGenerations.add(desc.generation);
    }
    Set<Integer> unfinishedGenerations = unfinishedCompactions.keySet();
    if (!allGenerations.containsAll(unfinishedGenerations)) {
      HashSet<Integer> missingGenerations = new HashSet<>(unfinishedGenerations);
      missingGenerations.removeAll(allGenerations);
      logger.debug("Unfinished compactions of {}.{} reference missing sstables of generations {}", metadata.ksName, metadata.cfName, missingGenerations);
    }
    Set<Integer> completedAncestors = new HashSet<>();
    for (Map.Entry<Descriptor, Set<Component>> sstableFiles : directories.sstableLister().skipTemporary(true).list().entrySet()) {
      Descriptor desc = sstableFiles.getKey();
      Set<Integer> ancestors;
      try {
        CompactionMetadata compactionMetadata = (CompactionMetadata) desc.getMetadataSerializer().deserialize(desc, MetadataType.COMPACTION);
        ancestors = compactionMetadata.ancestors;
      } catch (IOException e) {
        throw new FSReadError(e, desc.filenameFor(Component.STATS));
      }
      if (!ancestors.isEmpty() && unfinishedGenerations.containsAll(ancestors) && allGenerations.containsAll(ancestors)) {
        UUID compactionTaskID = unfinishedCompactions.get(ancestors.iterator().next());
        assert compactionTaskID != null;
        logger.debug("Going to delete unfinished compaction product {}", desc);
        SSTable.delete(desc, sstableFiles.getValue());
        SystemKeyspace.finishCompaction(compactionTaskID);
      } else {
        completedAncestors.addAll(ancestors);
      }
    }
    for (Map.Entry<Descriptor, Set<Component>> sstableFiles : directories.sstableLister().list().entrySet()) {
      Descriptor desc = sstableFiles.getKey();
      if (completedAncestors.contains(desc.generation)) {
        logger.debug("Going to delete leftover compaction ancestor {}", desc);
        SSTable.delete(desc, sstableFiles.getValue());
        UUID compactionTaskID = unfinishedCompactions.get(desc.generation);
        if (compactionTaskID != null) {
          SystemKeyspace.finishCompaction(unfinishedCompactions.get(desc.generation));
        }
      }
    }
  }

  public void initRowCache() {
    if (!isRowCacheEnabled()) {
      return;
    }
    long start = System.nanoTime();
    int cachedRowsRead = CacheService.instance.rowCache.loadSaved(this);
    if (cachedRowsRead > 0) {
      logger.info("Completed loading ({} ms; {} keys) row cache for {}.{}", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start), cachedRowsRead, keyspace.getName(), name);
    }
  }

  public void initCounterCache() {
    if (!metadata.isCounter() || CacheService.instance.counterCache.getCapacity() == 0) {
      return;
    }
    long start = System.nanoTime();
    int cachedShardsRead = CacheService.instance.counterCache.loadSaved(this);
    if (cachedShardsRead > 0) {
      logger.info("Completed loading ({} ms; {} shards) counter cache for {}.{}", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start), cachedShardsRead, keyspace.getName(), name);
    }
  }

  /**
     * See #{@code StorageService.loadNewSSTables(String, String)} for more info
     *
     * @param ksName The keyspace name
     * @param cfName The columnFamily name
     */
  public static synchronized void loadNewSSTables(String ksName, String cfName) {
    Keyspace keyspace = Keyspace.open(ksName);
    keyspace.getColumnFamilyStore(cfName).loadNewSSTables();
  }

  /**
     * #{@inheritDoc}
     */
  public synchronized void loadNewSSTables() {
    logger.info("Loading new SSTables for {}/{}...", keyspace.getName(), name);
    Set<Descriptor> currentDescriptors = new HashSet<Descriptor>();
    for (SSTableReader sstable : data.getView().sstables) {
      currentDescriptors.add(sstable.descriptor);
    }
    Set<SSTableReader> newSSTables = new HashSet<>();
    Directories.SSTableLister lister = directories.sstableLister().skipTemporary(true);
    for (Map.Entry<Descriptor, Set<Component>> entry : lister.list().entrySet()) {
      Descriptor descriptor = entry.getKey();
      if (currentDescriptors.contains(descriptor)) {
        continue;
      }
      if (descriptor.type.isTemporary) {
        continue;
      }
      if (!descriptor.isCompatible()) {
        throw new RuntimeException(String.format("Can\'t open incompatible SSTable! Current version %s, found file: %s", descriptor.getFormat().getLatestVersion(), descriptor));
      }
      try {
        if (new File(descriptor.filenameFor(Component.STATS)).exists()) {
          descriptor.getMetadataSerializer().mutateLevel(descriptor, 0);
        }
      } catch (IOException e) {
        SSTableReader.logOpenException(entry.getKey(), e);
        continue;
      }
      Descriptor newDescriptor;
      do {
        newDescriptor = new Descriptor(descriptor.version, descriptor.directory, descriptor.ksname, descriptor.cfname, fileIndexGenerator.incrementAndGet(), Descriptor.Type.FINAL, descriptor.formatType);
      } while(new File(newDescriptor.filenameFor(Component.DATA)).exists());
      logger.info("Renaming new SSTable {} to {}", descriptor, newDescriptor);
      SSTableWriter.rename(descriptor, newDescriptor, entry.getValue());
      SSTableReader reader;
      try {
        reader = SSTableReader.open(newDescriptor, entry.getValue(), metadata, partitioner);
      } catch (IOException e) {
        SSTableReader.logOpenException(entry.getKey(), e);
        continue;
      }
      newSSTables.add(reader);
    }
    if (newSSTables.isEmpty()) {
      logger.info("No new SSTables were found for {}/{}", keyspace.getName(), name);
      return;
    }
    logger.info("Loading new SSTables and building secondary indexes for {}/{}: {}", keyspace.getName(), name, newSSTables);
    SSTableReader.acquireReferences(newSSTables);
    data.addSSTables(newSSTables);
    try {
      indexManager.maybeBuildSecondaryIndexes(newSSTables, indexManager.allIndexesNames());
    }  finally {
      SSTableReader.releaseReferences(newSSTables);
    }
    logger.info("Done loading load new SSTables for {}/{}", keyspace.getName(), name);
  }

  public static void rebuildSecondaryIndex(String ksName, String cfName, String... idxNames) {
    ColumnFamilyStore cfs = Keyspace.open(ksName).getColumnFamilyStore(cfName);
    Set<String> indexes = new HashSet<String>(Arrays.asList(idxNames));
    Collection<SSTableReader> sstables = cfs.getSSTables();
    try {
      cfs.indexManager.setIndexRemoved(indexes);
      SSTableReader.acquireReferences(sstables);
      logger.info(String.format("User Requested secondary index re-build for %s/%s indexes", ksName, cfName));
      cfs.indexManager.maybeBuildSecondaryIndexes(sstables, indexes);
      cfs.indexManager.setIndexBuilt(indexes);
    }  finally {
      SSTableReader.releaseReferences(sstables);
    }
  }

  public String getColumnFamilyName() {
    return name;
  }

  public String getTempSSTablePath(File directory) {
    return getTempSSTablePath(directory, DatabaseDescriptor.getSSTableFormat().info.getLatestVersion(), DatabaseDescriptor.getSSTableFormat());
  }

  public String getTempSSTablePath(File directory, SSTableFormat.Type format) {
    return getTempSSTablePath(directory, format.info.getLatestVersion(), format);
  }

  private String getTempSSTablePath(File directory, Version version, SSTableFormat.Type format) {
    Descriptor desc = new Descriptor(version, directory, keyspace.getName(), name, fileIndexGenerator.incrementAndGet(), Descriptor.Type.TEMP, format);
    return desc.filenameFor(Component.DATA);
  }

  /**
     * Switches the memtable iff the live memtable is the one provided
     *
     * @param memtable
     */
  public Future<?> switchMemtableIfCurrent(Memtable memtable) {
    synchronized (data) {
      if (data.getView().getCurrentMemtable() == memtable) {
        return switchMemtable();
      }
    }
    return Futures.immediateFuture(null);
  }

  public ListenableFuture<?> switchMemtable() {
    synchronized (data) {
      logFlush();
      Flush flush = new Flush(false);
      flushExecutor.execute(flush);
      ListenableFutureTask<?> task = ListenableFutureTask.create(flush.postFlush, null);
      postFlushExecutor.submit(task);
      return task;
    }
  }

  private void logFlush() {
    float onHeapRatio = 0, offHeapRatio = 0;
    long onHeapTotal = 0, offHeapTotal = 0;
    Memtable memtable = getDataTracker().getView().getCurrentMemtable();
    onHeapRatio += memtable.getAllocator().onHeap().ownershipRatio();
    offHeapRatio += memtable.getAllocator().offHeap().ownershipRatio();
    onHeapTotal += memtable.getAllocator().onHeap().owns();
    offHeapTotal += memtable.getAllocator().offHeap().owns();
    for (SecondaryIndex index : indexManager.getIndexes()) {
      if (index.getIndexCfs() != null) {
        MemtableAllocator allocator = index.getIndexCfs().getDataTracker().getView().getCurrentMemtable().getAllocator();
        onHeapRatio += allocator.onHeap().ownershipRatio();
        offHeapRatio += allocator.offHeap().ownershipRatio();
        onHeapTotal += allocator.onHeap().owns();
        offHeapTotal += allocator.offHeap().owns();
      }
    }
    logger.info("Enqueuing flush of {}: {}", name, String.format("%d (%.0f%%) on-heap, %d (%.0f%%) off-heap", onHeapTotal, onHeapRatio * 100, offHeapTotal, offHeapRatio * 100));
  }

  public ListenableFuture<?> forceFlush() {
    return forceFlush(null);
  }

  /**
     * Flush if there is unflushed data that was written to the CommitLog before @param flushIfDirtyBefore
     * (inclusive).  If @param flushIfDirtyBefore is null, flush if there is any unflushed data.
     *
     * @return a Future such that when the future completes, all data inserted before forceFlush was called,
     * will be flushed.
     */
  public ListenableFuture<?> forceFlush(ReplayPosition flushIfDirtyBefore) {
    synchronized (data) {
      boolean clean = true;
      for (ColumnFamilyStore cfs : concatWithIndexes()) {
        clean &= cfs.data.getView().getCurrentMemtable().isCleanAfter(flushIfDirtyBefore);
      }
      if (clean) {
        ListenableFutureTask<?> task = ListenableFutureTask.create(new Runnable() {
          public void run() {
            logger.debug("forceFlush requested but everything is clean in {}", name);
          }
        }, null);
        postFlushExecutor.execute(task);
        return task;
      }
      return switchMemtable();
    }
  }

  public void forceBlockingFlush() {
    FBUtilities.waitOnFuture(forceFlush());
  }

  private final class PostFlush implements Runnable {
    final boolean flushSecondaryIndexes;

    final OpOrder.Barrier writeBarrier;

    final CountDownLatch latch = new CountDownLatch(1);

    volatile ReplayPosition lastReplayPosition;

    private PostFlush(boolean flushSecondaryIndexes, OpOrder.Barrier writeBarrier) {
      this.writeBarrier = writeBarrier;
      this.flushSecondaryIndexes = flushSecondaryIndexes;
    }

    public void run() {
      writeBarrier.await();
      if (flushSecondaryIndexes) {
        for (SecondaryIndex index : indexManager.getIndexesNotBackedByCfs()) {
          logger.info("Flushing SecondaryIndex {}", index);
          index.forceBlockingFlush();
        }
      }
      try {
        latch.await();
      } catch (InterruptedException e) {
        throw new IllegalStateException();
      }
      if (lastReplayPosition != null) {
        CommitLog.instance.discardCompletedSegments(metadata.cfId, lastReplayPosition);
      }
      metric.pendingFlushes.dec();
    }
  }

  private final class Flush implements Runnable {
    final OpOrder.Barrier writeBarrier;

    final List<Memtable> memtables;

    final PostFlush postFlush;

    final boolean truncate;

    private Flush(boolean truncate) {
      this.truncate = truncate;
      metric.pendingFlushes.inc();
      writeBarrier = keyspace.writeOrder.newBarrier();
      memtables = new ArrayList<>();
      final ReplayPosition minReplayPosition = CommitLog.instance.getContext();
      for (ColumnFamilyStore cfs : concatWithIndexes()) {
        Memtable mt = cfs.data.switchMemtable(truncate);
        mt.setDiscarding(writeBarrier, minReplayPosition);
        memtables.add(mt);
      }
      writeBarrier.issue();
      postFlush = new PostFlush(!truncate, writeBarrier);
    }

    public void run() {
      writeBarrier.markBlocking();
      writeBarrier.await();
      Iterator<Memtable> iter = memtables.iterator();
      while (iter.hasNext()) {
        Memtable memtable = iter.next();
        memtable.cfs.data.markFlushing(memtable);
        if (memtable.isClean() || truncate) {
          memtable.cfs.replaceFlushed(memtable, null);
          memtable.setDiscarded();
          iter.remove();
        }
      }
      if (memtables.isEmpty()) {
        postFlush.latch.countDown();
        return;
      }
      metric.memtableSwitchCount.inc();
      for (final Memtable memtable : memtables) {
        MoreExecutors.sameThreadExecutor().execute(memtable.flushRunnable());
        final OpOrder.Barrier readBarrier = readOrdering.newBarrier();
        readBarrier.issue();
        reclaimExecutor.execute(new WrappedRunnable() {
          public void runMayThrow() throws InterruptedException, ExecutionException {
            readBarrier.await();
            memtable.setDiscarded();
          }
        });
      }
      postFlush.lastReplayPosition = memtables.get(0).getLastReplayPosition();
      postFlush.latch.countDown();
    }
  }

  public static class FlushLargestColumnFamily implements Runnable {
    public void run() {
      float largestRatio = 0f;
      Memtable largest = null;
      for (ColumnFamilyStore cfs : ColumnFamilyStore.all()) {
        Memtable current = cfs.getDataTracker().getView().getCurrentMemtable();
        float onHeap = 0f, offHeap = 0f;
        onHeap += current.getAllocator().onHeap().ownershipRatio();
        offHeap += current.getAllocator().offHeap().ownershipRatio();
        for (SecondaryIndex index : cfs.indexManager.getIndexes()) {
          if (index.getIndexCfs() != null) {
            MemtableAllocator allocator = index.getIndexCfs().getDataTracker().getView().getCurrentMemtable().getAllocator();
            onHeap += allocator.onHeap().ownershipRatio();
            offHeap += allocator.offHeap().ownershipRatio();
          }
        }
        float ratio = Math.max(onHeap, offHeap);
        if (ratio > largestRatio) {
          largest = current;
          largestRatio = ratio;
        }
      }
      if (largest != null) {
        largest.cfs.switchMemtableIfCurrent(largest);
      }
    }
  }

  public void maybeUpdateRowCache(DecoratedKey key) {
    if (!isRowCacheEnabled()) {
      return;
    }
    RowCacheKey cacheKey = new RowCacheKey(metadata.cfId, key);
    invalidateCachedRow(cacheKey);
  }

  /**
     * Insert/Update the column family for this key.
     * Caller is responsible for acquiring Keyspace.switchLock
     * param @ lock - lock that needs to be used.
     * param @ key - key for update/insert
     * param @ columnFamily - columnFamily changes
     */
  public void apply(DecoratedKey key, ColumnFamily columnFamily, SecondaryIndexManager.Updater indexer, OpOrder.Group opGroup, ReplayPosition replayPosition) {
    long start = System.nanoTime();
    Memtable mt = data.getMemtableFor(opGroup);
    final long timeDelta = mt.put(key, columnFamily, indexer, opGroup, replayPosition);
    maybeUpdateRowCache(key);
    metric.writeLatency.addNano(System.nanoTime() - start);
    if (timeDelta < Long.MAX_VALUE) {
      metric.colUpdateTimeDeltaHistogram.update(timeDelta);
    }
  }

  /**
     * Purges gc-able top-level and range tombstones, returning `cf` if there are any columns or tombstones left,
     * null otherwise.
     * @param gcBefore a timestamp (in seconds); tombstones with a localDeletionTime before this will be purged
     */
  public static ColumnFamily removeDeletedCF(ColumnFamily cf, int gcBefore) {
    cf.purgeTombstones(gcBefore);
    return !cf.hasColumns() && !cf.isMarkedForDelete() ? null : cf;
  }

  /**
     * Removes deleted columns and purges gc-able tombstones.
     * @return an updated `cf` if any columns or tombstones remain, null otherwise
     */
  public static ColumnFamily removeDeleted(ColumnFamily cf, int gcBefore) {
    return removeDeleted(cf, gcBefore, SecondaryIndexManager.nullUpdater);
  }

  public static ColumnFamily removeDeleted(ColumnFamily cf, int gcBefore, SecondaryIndexManager.Updater indexer) {
    if (cf == null) {
      return null;
    }
    return removeDeletedCF(removeDeletedColumnsOnly(cf, gcBefore, indexer), gcBefore);
  }

  /**
     * Removes only per-cell tombstones, cells that are shadowed by a row-level or range tombstone, or
     * columns that have been dropped from the schema (for CQL3 tables only).
     * @return the updated ColumnFamily
     */
  public static ColumnFamily removeDeletedColumnsOnly(ColumnFamily cf, int gcBefore, SecondaryIndexManager.Updater indexer) {
    Iterator<Cell> iter = cf.iterator();
    DeletionInfo.InOrderTester tester = cf.inOrderDeletionTester();
    boolean hasDroppedColumns = !cf.metadata.getDroppedColumns().isEmpty();
    while (iter.hasNext()) {
      Cell c = iter.next();
      if (c.getLocalDeletionTime() < gcBefore || tester.isDeleted(c) || (hasDroppedColumns && isDroppedColumn(c, cf.metadata()))) {
        iter.remove();
        indexer.remove(c);
      }
    }
    return cf;
  }

  private static boolean isDroppedColumn(Cell c, CFMetaData meta) {
    Long droppedAt = meta.getDroppedColumns().get(c.name().cql3ColumnName(meta));
    return droppedAt != null && c.timestamp() <= droppedAt;
  }

  private void removeDroppedColumns(ColumnFamily cf) {
    if (cf == null || cf.metadata.getDroppedColumns().isEmpty()) {
      return;
    }
    Iterator<Cell> iter = cf.iterator();
    while (iter.hasNext()) {
      if (isDroppedColumn(iter.next(), metadata)) {
        iter.remove();
      }
    }
  }

  /**
     * @param sstables
     * @return sstables whose key range overlaps with that of the given sstables, not including itself.
     * (The given sstables may or may not overlap with each other.)
     */
  public Set<SSTableReader> getOverlappingSSTables(Collection<SSTableReader> sstables) {
    logger.debug("Checking for sstables overlapping {}", sstables);
    if (sstables.isEmpty()) {
      return ImmutableSet.of();
    }
    DataTracker.SSTableIntervalTree tree = data.getView().intervalTree;
    Set<SSTableReader> results = null;
    for (SSTableReader sstable : sstables) {
      Set<SSTableReader> overlaps = ImmutableSet.copyOf(tree.search(Interval.<RowPosition, SSTableReader>create(sstable.first, sstable.last)));
      results = results == null ? overlaps : Sets.union(results, overlaps).immutableCopy();
    }
    results = Sets.difference(results, ImmutableSet.copyOf(sstables));
    return results;
  }

  /**
     * like getOverlappingSSTables, but acquires references before returning
     */
  public Set<SSTableReader> getAndReferenceOverlappingSSTables(Collection<SSTableReader> sstables) {
    while (true) {
      Set<SSTableReader> overlapped = getOverlappingSSTables(sstables);
      if (SSTableReader.acquireReferences(overlapped)) {
        return overlapped;
      }
    }
  }

  public void addSSTable(SSTableReader sstable) {
    assert sstable.getColumnFamilyName().equals(name);
    addSSTables(Arrays.asList(sstable));
  }

  public void addSSTables(Collection<SSTableReader> sstables) {
    data.addSSTables(sstables);
    CompactionManager.instance.submitBackground(this);
  }

  /**
     * Calculate expected file size of SSTable after compaction.
     *
     * If operation type is {@code CLEANUP} and we're not dealing with an index sstable,
     * then we calculate expected file size with checking token range to be eliminated.
     *
     * Otherwise, we just add up all the files' size, which is the worst case file
     * size for compaction of all the list of files given.
     *
     * @param sstables SSTables to calculate expected compacted file size
     * @param operation Operation type
     * @return Expected file size of SSTable after compaction
     */
  public long getExpectedCompactedFileSize(Iterable<SSTableReader> sstables, OperationType operation) {
    if (operation != OperationType.CLEANUP || isIndex()) {
      return SSTableReader.getTotalBytes(sstables);
    }
    long expectedFileSize = 0;
    Collection<Range<Token>> ranges = StorageService.instance.getLocalRanges(keyspace.getName());
    for (SSTableReader sstable : sstables) {
      List<Pair<Long, Long>> positions = sstable.getPositionsForRanges(ranges);
      for (Pair<Long, Long> position : positions) {
        expectedFileSize += position.right - position.left;
      }
    }
    return expectedFileSize;
  }

  public SSTableReader getMaxSizeFile(Iterable<SSTableReader> sstables) {
    long maxSize = 0L;
    SSTableReader maxFile = null;
    for (SSTableReader sstable : sstables) {
      if (sstable.onDiskLength() > maxSize) {
        maxSize = sstable.onDiskLength();
        maxFile = sstable;
      }
    }
    return maxFile;
  }

  public CompactionManager.AllSSTableOpStatus forceCleanup() throws ExecutionException, InterruptedException {
    return CompactionManager.instance.performCleanup(ColumnFamilyStore.this);
  }

  public CompactionManager.AllSSTableOpStatus scrub(boolean disableSnapshot, boolean skipCorrupted) throws ExecutionException, InterruptedException {
    if (!disableSnapshot) {
      snapshotWithoutFlush("pre-scrub-" + System.currentTimeMillis());
    }
    return CompactionManager.instance.performScrub(ColumnFamilyStore.this, skipCorrupted);
  }

  public CompactionManager.AllSSTableOpStatus sstablesRewrite(boolean excludeCurrentVersion) throws ExecutionException, InterruptedException {
    return CompactionManager.instance.performSSTableRewrite(ColumnFamilyStore.this, excludeCurrentVersion);
  }

  public void markObsolete(Collection<SSTableReader> sstables, OperationType compactionType) {
    assert !sstables.isEmpty();
    data.markObsolete(sstables, compactionType);
  }

  void replaceFlushed(Memtable memtable, SSTableReader sstable) {
    compactionStrategyWrapper.replaceFlushed(memtable, sstable);
  }

  public boolean isValid() {
    return valid;
  }

  public long getMemtableColumnsCount() {
    return metric.memtableColumnsCount.value();
  }

  public long getMemtableDataSize() {
    return metric.memtableOnHeapSize.value();
  }

  public int getMemtableSwitchCount() {
    return (int) metric.memtableSwitchCount.count();
  }

  /**
     * Package protected for access from the CompactionManager.
     */
  public DataTracker getDataTracker() {
    return data;
  }

  public Collection<SSTableReader> getSSTables() {
    return data.getSSTables();
  }

  public Set<SSTableReader> getUncompactingSSTables() {
    return data.getUncompactingSSTables();
  }

  public long[] getRecentSSTablesPerReadHistogram() {
    return metric.recentSSTablesPerRead.getBuckets(true);
  }

  public long[] getSSTablesPerReadHistogram() {
    return metric.sstablesPerRead.getBuckets(false);
  }

  public long getReadCount() {
    return metric.readLatency.latency.count();
  }

  public double getRecentReadLatencyMicros() {
    return metric.readLatency.getRecentLatency();
  }

  public long[] getLifetimeReadLatencyHistogramMicros() {
    return metric.readLatency.totalLatencyHistogram.getBuckets(false);
  }

  public long[] getRecentReadLatencyHistogramMicros() {
    return metric.readLatency.recentLatencyHistogram.getBuckets(true);
  }

  public long getTotalReadLatencyMicros() {
    return metric.readLatency.totalLatency.count();
  }

  public int getPendingTasks() {
    return (int) metric.pendingFlushes.count();
  }

  public long getWriteCount() {
    return metric.writeLatency.latency.count();
  }

  public long getTotalWriteLatencyMicros() {
    return metric.writeLatency.totalLatency.count();
  }

  public double getRecentWriteLatencyMicros() {
    return metric.writeLatency.getRecentLatency();
  }

  public long[] getLifetimeWriteLatencyHistogramMicros() {
    return metric.writeLatency.totalLatencyHistogram.getBuckets(false);
  }

  public long[] getRecentWriteLatencyHistogramMicros() {
    return metric.writeLatency.recentLatencyHistogram.getBuckets(true);
  }

  public ColumnFamily getColumnFamily(DecoratedKey key, Composite start, Composite finish, boolean reversed, int limit, long timestamp) {
    return getColumnFamily(QueryFilter.getSliceFilter(key, name, start, finish, reversed, limit, timestamp));
  }

  /**
     * Fetch the row and columns given by filter.key if it is in the cache; if not, read it from disk and cache it
     *
     * If row is cached, and the filter given is within its bounds, we return from cache, otherwise from disk
     *
     * If row is not cached, we figure out what filter is "biggest", read that from disk, then
     * filter the result and either cache that or return it.
     *
     * @param cfId the column family to read the row from
     * @param filter the columns being queried.
     * @return the requested data for the filter provided
     */
  private ColumnFamily getThroughCache(UUID cfId, QueryFilter filter) {
    assert isRowCacheEnabled() : String.format("Row cache is not enabled on table [" + name + "]");
    RowCacheKey key = new RowCacheKey(cfId, filter.key);
    IRowCacheEntry cached = CacheService.instance.rowCache.get(key);
    if (cached != null) {
      if (cached instanceof RowCacheSentinel) {
        Tracing.trace("Row cache miss (race)");
        metric.rowCacheMiss.inc();
        return getTopLevelColumns(filter, Integer.MIN_VALUE);
      }
      ColumnFamily cachedCf = (ColumnFamily) cached;
      if (isFilterFullyCoveredBy(filter.filter, cachedCf, filter.timestamp)) {
        metric.rowCacheHit.inc();
        Tracing.trace("Row cache hit");
        return filterColumnFamily(cachedCf, filter);
      }
      metric.rowCacheHitOutOfRange.inc();
      Tracing.trace("Ignoring row cache as cached value could not satisfy query");
      return getTopLevelColumns(filter, Integer.MIN_VALUE);
    }
    metric.rowCacheMiss.inc();
    Tracing.trace("Row cache miss");
    RowCacheSentinel sentinel = new RowCacheSentinel();
    boolean sentinelSuccess = CacheService.instance.rowCache.putIfAbsent(key, sentinel);
    ColumnFamily data = null;
    ColumnFamily toCache = null;
    try {
      if (metadata.getCaching().rowCache.cacheFullPartitions()) {
        data = getTopLevelColumns(QueryFilter.getIdentityFilter(filter.key, name, filter.timestamp), Integer.MIN_VALUE);
        toCache = data;
        Tracing.trace("Populating row cache with the whole partition");
        if (sentinelSuccess && toCache != null) {
          CacheService.instance.rowCache.replace(key, sentinel, toCache);
        }
        return filterColumnFamily(data, filter);
      }
      if (filter.filter.isHeadFilter() && filter.filter.countCQL3Rows(metadata.comparator)) {
        SliceQueryFilter sliceFilter = (SliceQueryFilter) filter.filter;
        int rowsToCache = metadata.getCaching().rowCache.rowsToCache;
        SliceQueryFilter cacheSlice = readFilterForCache();
        QueryFilter cacheFilter = new QueryFilter(filter.key, name, cacheSlice, filter.timestamp);
        if (sliceFilter.count < rowsToCache) {
          toCache = getTopLevelColumns(cacheFilter, Integer.MIN_VALUE);
          if (toCache != null) {
            Tracing.trace("Populating row cache ({} rows cached)", cacheSlice.lastCounted());
            data = filterColumnFamily(toCache, filter);
          }
        } else {
          data = getTopLevelColumns(filter, Integer.MIN_VALUE);
          if (data != null) {
            if (sliceFilter.finish().isEmpty() || sliceFilter.lastCounted() >= rowsToCache) {
              toCache = filterColumnFamily(data, cacheFilter);
              Tracing.trace("Caching {} rows (out of {} requested)", cacheSlice.lastCounted(), sliceFilter.count);
            } else {
              Tracing.trace("Not populating row cache, not enough rows fetched ({} fetched but {} required for the cache)", sliceFilter.lastCounted(), rowsToCache);
            }
          }
        }
        if (sentinelSuccess && toCache != null) {
          CacheService.instance.rowCache.replace(key, sentinel, toCache);
        }
        return data;
      } else {
        Tracing.trace("Fetching data but not populating cache as query does not query from the start of the partition");
        return getTopLevelColumns(filter, Integer.MIN_VALUE);
      }
    }  finally {
      if (sentinelSuccess && toCache == null) {
        invalidateCachedRow(key);
      }
    }
  }

  public SliceQueryFilter readFilterForCache() {
    return new SliceQueryFilter(ColumnSlice.ALL_COLUMNS_ARRAY, false, metadata.getCaching().rowCache.rowsToCache, metadata.clusteringColumns().size());
  }

  public boolean isFilterFullyCoveredBy(IDiskAtomFilter filter, ColumnFamily cachedCf, long now) {
    boolean wholePartitionCached = cachedCf.liveCQL3RowCount(Integer.MIN_VALUE) < metadata.getCaching().rowCache.rowsToCache;
    return wholePartitionCached || filter.isFullyCoveredBy(cachedCf, now);
  }

  public int gcBefore(long now) {
    return (int) (now / 1000) - metadata.getGcGraceSeconds();
  }

  /**
     * get a list of columns starting from a given column, in a specified order.
     * only the latest version of a column is returned.
     * @return null if there is no data and no tombstones; otherwise a ColumnFamily
     */
  public ColumnFamily getColumnFamily(QueryFilter filter) {
    assert name.equals(filter.getColumnFamilyName()) : filter.getColumnFamilyName();
    ColumnFamily result = null;
    long start = System.nanoTime();
    try {
      int gcBefore = gcBefore(filter.timestamp);
      if (isRowCacheEnabled()) {
        assert !isIndex();
        UUID cfId = metadata.cfId;
        ColumnFamily cached = getThroughCache(cfId, filter);
        if (cached == null) {
          logger.trace("cached row is empty");
          return null;
        }
        result = cached;
      } else {
        ColumnFamily cf = getTopLevelColumns(filter, gcBefore);
        if (cf == null) {
          return null;
        }
        result = removeDeletedCF(cf, gcBefore);
      }
      removeDroppedColumns(result);
      if (filter.filter instanceof SliceQueryFilter) {
        metric.tombstoneScannedHistogram.update(((SliceQueryFilter) filter.filter).lastIgnored());
        metric.liveScannedHistogram.update(((SliceQueryFilter) filter.filter).lastLive());
      }
    }  finally {
      metric.readLatency.addNano(System.nanoTime() - start);
    }
    return result;
  }

  /**
     *  Filter a cached row, which will not be modified by the filter, but may be modified by throwing out
     *  tombstones that are no longer relevant.
     *  The returned column family won't be thread safe.
     */
  ColumnFamily filterColumnFamily(ColumnFamily cached, QueryFilter filter) {
    if (cached == null) {
      return null;
    }
    ColumnFamily cf = cached.cloneMeShallow(ArrayBackedSortedColumns.factory, filter.filter.isReversed());
    int gcBefore = gcBefore(filter.timestamp);
    filter.collateOnDiskAtom(cf, filter.getIterator(cached), gcBefore);
    return removeDeletedCF(cf, gcBefore);
  }

  /**
     * Get the current view and acquires references on all its sstables.
     * This is a bit tricky because we must ensure that between the time we
     * get the current view and the time we acquire the references the set of
     * sstables hasn't changed. Otherwise we could get a view for which an
     * sstable have been deleted in the meantime.
     *
     * At the end of this method, a reference on all the sstables of the
     * returned view will have been acquired and must thus be released when
     * appropriate.
     */
  private DataTracker.View markCurrentViewReferenced() {
    while (true) {
      DataTracker.View currentView = data.getView();
      if (SSTableReader.acquireReferences(currentView.sstables)) {
        return currentView;
      }
    }
  }

  /**
     * Get the current sstables, acquiring references on all of them.
     * The caller is in charge of releasing the references on the sstables.
     *
     * See markCurrentViewReferenced() above.
     */
  public Collection<SSTableReader> markCurrentSSTablesReferenced() {
    return markCurrentViewReferenced().sstables;
  }

  public Set<SSTableReader> getUnrepairedSSTables() {
    Set<SSTableReader> unRepairedSSTables = new HashSet<>(getSSTables());
    Iterator<SSTableReader> sstableIterator = unRepairedSSTables.iterator();
    while (sstableIterator.hasNext()) {
      SSTableReader sstable = sstableIterator.next();
      if (sstable.isRepaired()) {
        sstableIterator.remove();
      }
    }
    return unRepairedSSTables;
  }

  public Set<SSTableReader> getRepairedSSTables() {
    Set<SSTableReader> repairedSSTables = new HashSet<>(getSSTables());
    Iterator<SSTableReader> sstableIterator = repairedSSTables.iterator();
    while (sstableIterator.hasNext()) {
      SSTableReader sstable = sstableIterator.next();
      if (!sstable.isRepaired()) {
        sstableIterator.remove();
      }
    }
    return repairedSSTables;
  }

  public ViewFragment selectAndReference(Function<DataTracker.View, List<SSTableReader>> filter) {
    while (true) {
      ViewFragment view = select(filter);
      if (view.sstables.isEmpty() || SSTableReader.acquireReferences(view.sstables)) {
        return view;
      }
    }
  }

  public ViewFragment select(Function<DataTracker.View, List<SSTableReader>> filter) {
    DataTracker.View view = data.getView();
    List<SSTableReader> sstables = view.intervalTree.isEmpty() ? Collections.<SSTableReader>emptyList() : filter.apply(view);
    return new ViewFragment(sstables, view.getAllMemtables());
  }

  /**
     * @return a ViewFragment containing the sstables and memtables that may need to be merged
     * for the given @param key, according to the interval tree
     */
  public Function<DataTracker.View, List<SSTableReader>> viewFilter(final DecoratedKey key) {
    assert !key.isMinimum();
    return new Function<DataTracker.View, List<SSTableReader>>() {
      public List<SSTableReader> apply(DataTracker.View view) {
        return compactionStrategyWrapper.filterSSTablesForReads(view.intervalTree.search(key));
      }
    };
  }

  /**
     * @return a ViewFragment containing the sstables and memtables that may need to be merged
     * for rows within @param rowBounds, inclusive, according to the interval tree.
     */
  public Function<DataTracker.View, List<SSTableReader>> viewFilter(final AbstractBounds<RowPosition> rowBounds) {
    return new Function<DataTracker.View, List<SSTableReader>>() {
      public List<SSTableReader> apply(DataTracker.View view) {
        return compactionStrategyWrapper.filterSSTablesForReads(view.sstablesInBounds(rowBounds));
      }
    };
  }

  /**
     * @return a ViewFragment containing the sstables and memtables that may need to be merged
     * for rows for all of @param rowBoundsCollection, inclusive, according to the interval tree.
     */
  public Function<DataTracker.View, List<SSTableReader>> viewFilter(final Collection<AbstractBounds<RowPosition>> rowBoundsCollection) {
    return new Function<DataTracker.View, List<SSTableReader>>() {
      public List<SSTableReader> apply(DataTracker.View view) {
        Set<SSTableReader> sstables = Sets.newHashSet();
        for (AbstractBounds<RowPosition> rowBounds : rowBoundsCollection) {
          sstables.addAll(view.sstablesInBounds(rowBounds));
        }
        return ImmutableList.copyOf(sstables);
      }
    };
  }

  public List<String> getSSTablesForKey(String key) {
    DecoratedKey dk = partitioner.decorateKey(metadata.getKeyValidator().fromString(key));
    try (OpOrder.Group op = readOrdering.start()) {
      List<String> files = new ArrayList<>();
      for (SSTableReader sstr : select(viewFilter(dk)).sstables) {
        if (sstr.getPosition(dk, SSTableReader.Operator.EQ, false) != null) {
          files.add(sstr.getFilename());
        }
      }
      return files;
    }
  }

  public ColumnFamily getTopLevelColumns(QueryFilter filter, int gcBefore) {
    Tracing.trace("Executing single-partition query on {}", name);
    CollationController controller = new CollationController(this, filter, gcBefore);
    ColumnFamily columns;
    try (OpOrder.Group op = readOrdering.start()) {
      columns = controller.getTopLevelColumns(Memtable.MEMORY_POOL.needToCopyOnHeap());
    }
    metric.updateSSTableIterated(controller.getSstablesIterated());
    return columns;
  }

  public void cleanupCache() {
    Collection<Range<Token>> ranges = StorageService.instance.getLocalRanges(keyspace.getName());
    for (RowCacheKey key : CacheService.instance.rowCache.getKeySet()) {
      DecoratedKey dk = partitioner.decorateKey(ByteBuffer.wrap(key.key));
      if (key.cfId == metadata.cfId && !Range.isInRanges(dk.getToken(), ranges)) {
        invalidateCachedRow(dk);
      }
    }
    if (metadata.isCounter()) {
      for (CounterCacheKey key : CacheService.instance.counterCache.getKeySet()) {
        DecoratedKey dk = partitioner.decorateKey(ByteBuffer.wrap(key.partitionKey));
        if (key.cfId == metadata.cfId && !Range.isInRanges(dk.getToken(), ranges)) {
          CacheService.instance.counterCache.remove(key);
        }
      }
    }
  }

  public static abstract class AbstractScanIterator extends AbstractIterator<Row> implements CloseableIterator<Row> {
    public boolean needsFiltering() {
      return true;
    }
  }

  /**
      * Iterate over a range of rows and columns from memtables/sstables.
      *
      * @param range The range of keys and columns within those keys to fetch
     */
  private AbstractScanIterator getSequentialIterator(final DataRange range, long now) {
    assert !(range.keyRange() instanceof Range) || !((Range) range.keyRange()).isWrapAround() || range.keyRange().right.isMinimum() : range.keyRange();
    final ViewFragment view = select(viewFilter(range.keyRange()));
    Tracing.trace("Executing seq scan across {} sstables for {}", view.sstables.size(), range.keyRange().getString(metadata.getKeyValidator()));
    final CloseableIterator<Row> iterator = RowIteratorFactory.getIterator(view.memtables, view.sstables, range, this, now);
    return new AbstractScanIterator() {
      protected Row computeNext() {
        if (!iterator.hasNext()) {
          return endOfData();
        }
        Row current = iterator.next();
        DecoratedKey key = current.key;
        if (!range.stopKey().isMinimum() && range.stopKey().compareTo(key) < 0) {
          return endOfData();
        }
        if (!range.contains(key)) {
          return computeNext();
        }
        if (logger.isTraceEnabled()) {
          logger.trace("scanned {}", metadata.getKeyValidator().getString(key.getKey()));
        }
        return current;
      }

      public void close() throws IOException {
        iterator.close();
      }
    };
  }

  @VisibleForTesting public List<Row> getRangeSlice(final AbstractBounds<RowPosition> range, List<IndexExpression> rowFilter, IDiskAtomFilter columnFilter, int maxResults) {
    return getRangeSlice(range, rowFilter, columnFilter, maxResults, System.currentTimeMillis());
  }

  public List<Row> getRangeSlice(final AbstractBounds<RowPosition> range, List<IndexExpression> rowFilter, IDiskAtomFilter columnFilter, int maxResults, long now) {
    return getRangeSlice(makeExtendedFilter(range, columnFilter, rowFilter, maxResults, false, false, now));
  }

  /**
     * Allows generic range paging with the slice column filter.
     * Typically, suppose we have rows A, B, C ... Z having each some columns in [1, 100].
     * And suppose we want to page throught the query that for all rows returns the columns
     * within [25, 75]. For that, we need to be able to do a range slice starting at (row r, column c)
     * and ending at (row Z, column 75), *but* that only return columns in [25, 75].
     * That is what this method allows. The columnRange is the "window" of  columns we are interested
     * in each row, and columnStart (resp. columnEnd) is the start (resp. end) for the first
     * (resp. end) requested row.
     */
  public ExtendedFilter makeExtendedFilter(AbstractBounds<RowPosition> keyRange, SliceQueryFilter columnRange, Composite columnStart, Composite columnStop, List<IndexExpression> rowFilter, int maxResults, boolean countCQL3Rows, long now) {
    DataRange dataRange = new DataRange.Paging(keyRange, columnRange, columnStart, columnStop, metadata.comparator);
    return ExtendedFilter.create(this, dataRange, rowFilter, maxResults, countCQL3Rows, now);
  }

  public List<Row> getRangeSlice(AbstractBounds<RowPosition> range, List<IndexExpression> rowFilter, IDiskAtomFilter columnFilter, int maxResults, long now, boolean countCQL3Rows, boolean isPaging) {
    return getRangeSlice(makeExtendedFilter(range, columnFilter, rowFilter, maxResults, countCQL3Rows, isPaging, now));
  }

  public ExtendedFilter makeExtendedFilter(AbstractBounds<RowPosition> range, IDiskAtomFilter columnFilter, List<IndexExpression> rowFilter, int maxResults, boolean countCQL3Rows, boolean isPaging, long timestamp) {
    DataRange dataRange;
    if (isPaging) {
      assert columnFilter instanceof SliceQueryFilter;
      SliceQueryFilter sfilter = (SliceQueryFilter) columnFilter;
      assert sfilter.slices.length == 1;
      SliceQueryFilter newFilter = new SliceQueryFilter(ColumnSlice.ALL_COLUMNS_ARRAY, sfilter.isReversed(), sfilter.count);
      dataRange = new DataRange.Paging(range, newFilter, sfilter.start(), sfilter.finish(), metadata.comparator);
    } else {
      dataRange = new DataRange(range, columnFilter);
    }
    return ExtendedFilter.create(this, dataRange, rowFilter, maxResults, countCQL3Rows, timestamp);
  }

  public List<Row> getRangeSlice(ExtendedFilter filter) {
    long start = System.nanoTime();
    try (OpOrder.Group op = readOrdering.start()) {
      return filter(getSequentialIterator(filter.dataRange, filter.timestamp), filter);
    } finally {
      metric.rangeLatency.addNano(System.nanoTime() - start);
    }
  }

  @VisibleForTesting public List<Row> search(AbstractBounds<RowPosition> range, List<IndexExpression> clause, IDiskAtomFilter dataFilter, int maxResults) {
    return search(range, clause, dataFilter, maxResults, System.currentTimeMillis());
  }

  public List<Row> search(AbstractBounds<RowPosition> range, List<IndexExpression> clause, IDiskAtomFilter dataFilter, int maxResults, long now) {
    return search(makeExtendedFilter(range, dataFilter, clause, maxResults, false, false, now));
  }

  public List<Row> search(ExtendedFilter filter) {
    Tracing.trace("Executing indexed scan for {}", filter.dataRange.keyRange().getString(metadata.getKeyValidator()));
    return indexManager.search(filter);
  }

  public List<Row> filter(AbstractScanIterator rowIterator, ExtendedFilter filter) {
    logger.trace("Filtering {} for rows matching {}", rowIterator, filter);
    List<Row> rows = new ArrayList<Row>();
    int columnsCount = 0;
    int total = 0, matched = 0;
    try {
      while (rowIterator.hasNext() && matched < filter.maxRows() && columnsCount < filter.maxColumns()) {
        Row rawRow = rowIterator.next();
        total++;
        ColumnFamily data = rawRow.cf;
        if (rowIterator.needsFiltering()) {
          IDiskAtomFilter extraFilter = filter.getExtraFilter(rawRow.key, data);
          if (extraFilter != null) {
            ColumnFamily cf = filter.cfs.getColumnFamily(new QueryFilter(rawRow.key, name, extraFilter, filter.timestamp));
            if (cf != null) {
              data.addAll(cf);
            }
          }
          removeDroppedColumns(data);
          if (!filter.isSatisfiedBy(rawRow.key, data, null, null)) {
            continue;
          }
          logger.trace("{} satisfies all filter expressions", data);
          data = filter.prune(rawRow.key, data);
        } else {
          removeDroppedColumns(data);
        }
        rows.add(new Row(rawRow.key, data));
        matched++;
        if (data != null) {
          columnsCount += filter.lastCounted(data);
        }
        filter.updateFilter(columnsCount);
      }
      return rows;
    }  finally {
      try {
        rowIterator.close();
        Tracing.trace("Scanned {} rows and matched {}", total, matched);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public CellNameType getComparator() {
    return metadata.comparator;
  }

  public void snapshotWithoutFlush(String snapshotName) {
    snapshotWithoutFlush(snapshotName, null);
  }

  public Set<SSTableReader> snapshotWithoutFlush(String snapshotName, Predicate<SSTableReader> predicate) {
    Set<SSTableReader> snapshottedSSTables = new HashSet<>();
    for (ColumnFamilyStore cfs : concatWithIndexes()) {
      DataTracker.View currentView = cfs.markCurrentViewReferenced();
      final JSONArray filesJSONArr = new JSONArray();
      try {
        for (SSTableReader ssTable : currentView.sstables) {
          if (ssTable.openReason == SSTableReader.OpenReason.EARLY || (predicate != null && !predicate.apply(ssTable))) {
            continue;
          }
          File snapshotDirectory = Directories.getSnapshotDirectory(ssTable.descriptor, snapshotName);
          ssTable.createLinks(snapshotDirectory.getPath());
          filesJSONArr.add(ssTable.descriptor.relativeFilenameFor(Component.DATA));
          if (logger.isDebugEnabled()) {
            logger.debug("Snapshot for {} keyspace data file {} created in {}", keyspace, ssTable.getFilename(), snapshotDirectory);
          }
          snapshottedSSTables.add(ssTable);
        }
        writeSnapshotManifest(filesJSONArr, snapshotName);
      }  finally {
        SSTableReader.releaseReferences(currentView.sstables);
      }
    }
    return snapshottedSSTables;
  }

  private void writeSnapshotManifest(final JSONArray filesJSONArr, final String snapshotName) {
    final File manifestFile = directories.getSnapshotManifestFile(snapshotName);
    try {
      if (!manifestFile.getParentFile().exists()) {
        manifestFile.getParentFile().mkdirs();
      }
      try (PrintStream out = new PrintStream(manifestFile)) {
        final JSONObject manifestJSON = new JSONObject();
        manifestJSON.put("files", filesJSONArr);
        out.println(manifestJSON.toJSONString());
      }
    } catch (IOException e) {
      throw new FSWriteError(e, manifestFile);
    }
  }

  public List<SSTableReader> getSnapshotSSTableReader(String tag) throws IOException {
    Map<Integer, SSTableReader> active = new HashMap<>();
    for (SSTableReader sstable : data.getView().sstables) {
      active.put(sstable.descriptor.generation, sstable);
    }
    Map<Descriptor, Set<Component>> snapshots = directories.sstableLister().snapshots(tag).list();
    List<SSTableReader> readers = new ArrayList<>(snapshots.size());
    try {
      for (Map.Entry<Descriptor, Set<Component>> entries : snapshots.entrySet()) {
        SSTableReader sstable = active.get(entries.getKey().generation);
        if (sstable == null || !sstable.acquireReference()) {
          if (logger.isDebugEnabled()) {
            logger.debug("using snapshot sstable " + entries.getKey());
          }
          sstable = SSTableReader.open(entries.getKey(), entries.getValue(), metadata, partitioner);
          sstable.acquireReference();
        } else {
          if (logger.isDebugEnabled()) {
            logger.debug("using active sstable " + entries.getKey());
          }
        }
        readers.add(sstable);
      }
    } catch (IOException | RuntimeException e) {
      SSTableReader.releaseReferences(readers);
      throw e;
    }
    return readers;
  }

  /**
     * Take a snap shot of this columnfamily store.
     *
     * @param snapshotName the name of the associated with the snapshot
     */
  public Set<SSTableReader> snapshot(String snapshotName) {
    return snapshot(snapshotName, null);
  }

  public Set<SSTableReader> snapshot(String snapshotName, Predicate<SSTableReader> predicate) {
    forceBlockingFlush();
    return snapshotWithoutFlush(snapshotName, predicate);
  }

  public boolean snapshotExists(String snapshotName) {
    return directories.snapshotExists(snapshotName);
  }

  public long getSnapshotCreationTime(String snapshotName) {
    return directories.snapshotCreationTime(snapshotName);
  }

  /**
     * Clear all the snapshots for a given column family.
     *
     * @param snapshotName the user supplied snapshot name. If left empty,
     *                     all the snapshots will be cleaned.
     */
  public void clearSnapshot(String snapshotName) {
    List<File> snapshotDirs = directories.getCFDirectories();
    Directories.clearSnapshot(snapshotName, snapshotDirs);
  }

  /**
     *
     * @return  Return a map of all snapshots to space being used
     * The pair for a snapshot has true size and size on disk.
     */
  public Map<String, Pair<Long, Long>> getSnapshotDetails() {
    return directories.getSnapshotDetails();
  }

  public long getTotalDiskSpaceUsed() {
    return metric.totalDiskSpaceUsed.count();
  }

  public long getLiveDiskSpaceUsed() {
    return metric.liveDiskSpaceUsed.count();
  }

  public int getLiveSSTableCount() {
    return metric.liveSSTableCount.value();
  }

  /**
     * @return the cached row for @param key if it is already present in the cache.
     * That is, unlike getThroughCache, it will not readAndCache the row if it is not present, nor
     * are these calls counted in cache statistics.
     *
     * Note that this WILL cause deserialization of a SerializingCache row, so if all you
     * need to know is whether a row is present or not, use containsCachedRow instead.
     */
  public ColumnFamily getRawCachedRow(DecoratedKey key) {
    if (!isRowCacheEnabled()) {
      return null;
    }
    IRowCacheEntry cached = CacheService.instance.rowCache.getInternal(new RowCacheKey(metadata.cfId, key));
    return cached == null || cached instanceof RowCacheSentinel ? null : (ColumnFamily) cached;
  }

  private void invalidateCaches() {
    CacheService.instance.invalidateKeyCacheForCf(metadata.cfId);
    CacheService.instance.invalidateRowCacheForCf(metadata.cfId);
    if (metadata.isCounter()) {
      CacheService.instance.invalidateCounterCacheForCf(metadata.cfId);
    }
  }

  /**
     * @return true if @param key is contained in the row cache
     */
  public boolean containsCachedRow(DecoratedKey key) {
    return CacheService.instance.rowCache.getCapacity() != 0 && CacheService.instance.rowCache.containsKey(new RowCacheKey(metadata.cfId, key));
  }

  public void invalidateCachedRow(RowCacheKey key) {
    CacheService.instance.rowCache.remove(key);
  }

  public void invalidateCachedRow(DecoratedKey key) {
    UUID cfId = Schema.instance.getId(keyspace.getName(), this.name);
    if (cfId == null) {
      return;
    }
    invalidateCachedRow(new RowCacheKey(cfId, key));
  }

  public ClockAndCount getCachedCounter(ByteBuffer partitionKey, CellName cellName) {
    if (CacheService.instance.counterCache.getCapacity() == 0L) {
      return null;
    }
    return CacheService.instance.counterCache.get(CounterCacheKey.create(metadata.cfId, partitionKey, cellName));
  }

  public void putCachedCounter(ByteBuffer partitionKey, CellName cellName, ClockAndCount clockAndCount) {
    if (CacheService.instance.counterCache.getCapacity() == 0L) {
      return;
    }
    CacheService.instance.counterCache.put(CounterCacheKey.create(metadata.cfId, partitionKey, cellName), clockAndCount);
  }

  public void forceMajorCompaction() throws InterruptedException, ExecutionException {
    CompactionManager.instance.performMaximal(this);
  }

  public static Iterable<ColumnFamilyStore> all() {
    List<Iterable<ColumnFamilyStore>> stores = new ArrayList<Iterable<ColumnFamilyStore>>(Schema.instance.getKeyspaces().size());
    for (Keyspace keyspace : Keyspace.all()) {
      stores.add(keyspace.getColumnFamilyStores());
    }
    return Iterables.concat(stores);
  }

  public Iterable<DecoratedKey> keySamples(Range<Token> range) {
    Collection<SSTableReader> sstables = markCurrentSSTablesReferenced();
    try {
      Iterable<DecoratedKey>[] samples = new Iterable[sstables.size()];
      int i = 0;
      for (SSTableReader sstable : sstables) {
        samples[i++] = sstable.getKeySamples(range);
      }
      return Iterables.concat(samples);
    }  finally {
      SSTableReader.releaseReferences(sstables);
    }
  }

  public long estimatedKeysForRange(Range<Token> range) {
    Collection<SSTableReader> sstables = markCurrentSSTablesReferenced();
    try {
      long count = 0;
      for (SSTableReader sstable : sstables) {
        count += sstable.estimatedKeysForRanges(Collections.singleton(range));
      }
      return count;
    }  finally {
      SSTableReader.releaseReferences(sstables);
    }
  }

  /**
     * For testing.  No effort is made to clear historical or even the current memtables, nor for
     * thread safety.  All we do is wipe the sstable containers clean, while leaving the actual
     * data files present on disk.  (This allows tests to easily call loadNewSSTables on them.)
     */
  public void clearUnsafe() {
    for (final ColumnFamilyStore cfs : concatWithIndexes()) {
      cfs.runWithCompactionsDisabled(new Callable<Void>() {
        public Void call() {
          cfs.data.init();
          return null;
        }
      }, true);
    }
  }

  /**
     * Truncate deletes the entire column family's data with no expensive tombstone creation
     */
  public void truncateBlocking() {
    logger.debug("truncating {}", name);
    if (keyspace.metadata.durableWrites || DatabaseDescriptor.isAutoSnapshot()) {
      forceBlockingFlush();
      Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MILLISECONDS);
    } else {
      synchronized (data) {
        final Flush flush = new Flush(true);
        flushExecutor.execute(flush);
        postFlushExecutor.submit(flush.postFlush);
      }
    }
    Runnable truncateRunnable = new Runnable() {
      public void run() {
        logger.debug("Discarding sstable data for truncated CF + indexes");
        final long truncatedAt = System.currentTimeMillis();
        data.notifyTruncated(truncatedAt);
        if (DatabaseDescriptor.isAutoSnapshot()) {
          snapshot(Keyspace.getTimestampedSnapshotName(name));
        }
        ReplayPosition replayAfter = discardSSTables(truncatedAt);
        for (SecondaryIndex index : indexManager.getIndexes()) {
          index.truncateBlocking(truncatedAt);
        }
        SystemKeyspace.saveTruncationRecord(ColumnFamilyStore.this, truncatedAt, replayAfter);
        logger.debug("cleaning out row cache");
        invalidateCaches();
      }
    };
    runWithCompactionsDisabled(Executors.callable(truncateRunnable), true);
    logger.debug("truncate complete");
  }

  public <V extends java.lang.Object> V runWithCompactionsDisabled(Callable<V> callable, boolean interruptValidation) {
    synchronized (this) {
      logger.debug("Cancelling in-progress compactions for {}", metadata.cfName);
      Iterable<ColumnFamilyStore> selfWithIndexes = concatWithIndexes();
      for (ColumnFamilyStore cfs : selfWithIndexes) {
        cfs.getCompactionStrategy().pause();
      }
      try {
        Function<ColumnFamilyStore, CFMetaData> f = new Function<ColumnFamilyStore, CFMetaData>() {
          public CFMetaData apply(ColumnFamilyStore cfs) {
            return cfs.metadata;
          }
        };
        Iterable<CFMetaData> allMetadata = Iterables.transform(selfWithIndexes, f);
        CompactionManager.instance.interruptCompactionFor(allMetadata, interruptValidation);
        long start = System.nanoTime();
        long delay = TimeUnit.MINUTES.toNanos(1);
        while (System.nanoTime() - start < delay) {
          if (CompactionManager.instance.isCompacting(selfWithIndexes)) {
            Uninterruptibles.sleepUninterruptibly(100, TimeUnit.MILLISECONDS);
          } else {
            break;
          }
        }
        for (ColumnFamilyStore cfs : selfWithIndexes) {
          if (!cfs.getDataTracker().getCompacting().isEmpty()) {
            logger.warn("Unable to cancel in-progress compactions for {}.  Perhaps there is an unusually large row in progress somewhere, or the system is simply overloaded.", metadata.cfName);
            return null;
          }
        }
        logger.debug("Compactions successfully cancelled");
        try {
          return callable.call();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }  finally {
        for (ColumnFamilyStore cfs : selfWithIndexes) {
          cfs.getCompactionStrategy().resume();
        }
      }
    }
  }

  public Iterable<SSTableReader> markAllCompacting() {
    Callable<Iterable<SSTableReader>> callable = new Callable<Iterable<SSTableReader>>() {
      public Iterable<SSTableReader> call() throws Exception {
        assert data.getCompacting().isEmpty() : data.getCompacting();
        Iterable<SSTableReader> sstables = Lists.newArrayList(AbstractCompactionStrategy.filterSuspectSSTables(getSSTables()));
        if (Iterables.isEmpty(sstables)) {
          return Collections.emptyList();
        }
        boolean success = data.markCompacting(sstables);
        assert success : "something marked things compacting while compactions are disabled";
        return sstables;
      }
    };
    return runWithCompactionsDisabled(callable, false);
  }

  public long getBloomFilterFalsePositives() {
    return metric.bloomFilterFalsePositives.value();
  }

  public long getRecentBloomFilterFalsePositives() {
    return metric.recentBloomFilterFalsePositives.value();
  }

  public double getBloomFilterFalseRatio() {
    return metric.bloomFilterFalseRatio.value();
  }

  public double getRecentBloomFilterFalseRatio() {
    return metric.recentBloomFilterFalseRatio.value();
  }

  public long getBloomFilterDiskSpaceUsed() {
    return metric.bloomFilterDiskSpaceUsed.value();
  }

  public long getBloomFilterOffHeapMemoryUsed() {
    return metric.bloomFilterOffHeapMemoryUsed.value();
  }

  public long getIndexSummaryOffHeapMemoryUsed() {
    return metric.indexSummaryOffHeapMemoryUsed.value();
  }

  public long getCompressionMetadataOffHeapMemoryUsed() {
    return metric.compressionMetadataOffHeapMemoryUsed.value();
  }

  @Override public String toString() {
    return "CFS(" + "Keyspace=\'" + keyspace.getName() + '\'' + ", ColumnFamily=\'" + name + '\'' + ')';
  }

  public void disableAutoCompaction() {
    this.compactionStrategyWrapper.disable();
  }

  public void enableAutoCompaction() {
    enableAutoCompaction(false);
  }

  /**
     * used for tests - to be able to check things after a minor compaction
     * @param waitForFutures if we should block until autocompaction is done
     */
  @VisibleForTesting public void enableAutoCompaction(boolean waitForFutures) {
    this.compactionStrategyWrapper.enable();
    List<Future<?>> futures = CompactionManager.instance.submitBackground(this);
    if (waitForFutures) {
      FBUtilities.waitOnFutures(futures);
    }
  }

  public boolean isAutoCompactionDisabled() {
    return !this.compactionStrategyWrapper.isEnabled();
  }

  public AbstractCompactionStrategy getCompactionStrategy() {
    return compactionStrategyWrapper;
  }

  public void setCompactionThresholds(int minThreshold, int maxThreshold) {
    validateCompactionThresholds(minThreshold, maxThreshold);
    minCompactionThreshold.set(minThreshold);
    maxCompactionThreshold.set(maxThreshold);
    CompactionManager.instance.submitBackground(this);
  }

  public int getMinimumCompactionThreshold() {
    return minCompactionThreshold.value();
  }

  public void setMinimumCompactionThreshold(int minCompactionThreshold) {
    validateCompactionThresholds(minCompactionThreshold, maxCompactionThreshold.value());
    this.minCompactionThreshold.set(minCompactionThreshold);
  }

  public int getMaximumCompactionThreshold() {
    return maxCompactionThreshold.value();
  }

  public void setMaximumCompactionThreshold(int maxCompactionThreshold) {
    validateCompactionThresholds(minCompactionThreshold.value(), maxCompactionThreshold);
    this.maxCompactionThreshold.set(maxCompactionThreshold);
  }

  private void validateCompactionThresholds(int minThreshold, int maxThreshold) {
    if (minThreshold > maxThreshold) {
      throw new RuntimeException(String.format("The min_compaction_threshold cannot be larger than the max_compaction_threshold. " + "Min is \'%d\', Max is \'%d\'.", minThreshold, maxThreshold));
    }
    if (maxThreshold == 0 || minThreshold == 0) {
      throw new RuntimeException("Disabling compaction by setting min_compaction_threshold or max_compaction_threshold to 0 " + "is deprecated, set the compaction strategy option \'enabled\' to \'false\' instead or use the nodetool command \'disableautocompaction\'.");
    }
  }

  public double getTombstonesPerSlice() {
    return metric.tombstoneScannedHistogram.cf.getSnapshot().getMedian();
  }

  public double getLiveCellsPerSlice() {
    return metric.liveScannedHistogram.cf.getSnapshot().getMedian();
  }

  public long estimateKeys() {
    return data.estimatedKeys();
  }

  public long[] getEstimatedRowSizeHistogram() {
    return metric.estimatedRowSizeHistogram.value();
  }

  public long[] getEstimatedColumnCountHistogram() {
    return metric.estimatedColumnCountHistogram.value();
  }

  public double getCompressionRatio() {
    return metric.compressionRatio.value();
  }

  /** true if this CFS contains secondary index data */
  public boolean isIndex() {
    return partitioner instanceof LocalPartitioner;
  }

  public Iterable<ColumnFamilyStore> concatWithIndexes() {
    return Iterables.concat(Collections.singleton(this), indexManager.getIndexesBackedByCfs());
  }

  public List<String> getBuiltIndexes() {
    return indexManager.getBuiltIndexes();
  }

  public int getUnleveledSSTables() {
    return this.compactionStrategyWrapper.getUnleveledSSTables();
  }

  public int[] getSSTableCountPerLevel() {
    return compactionStrategyWrapper.getSSTableCountPerLevel();
  }

  public static class ViewFragment {
    public final List<SSTableReader> sstables;

    public final Iterable<Memtable> memtables;

    public ViewFragment(List<SSTableReader> sstables, Iterable<Memtable> memtables) {
      this.sstables = sstables;
      this.memtables = memtables;
    }
  }

  /**
     * Returns the creation time of the oldest memtable not fully flushed yet.
     */
  public long oldestUnflushedMemtable() {
    return data.getView().getOldestMemtable().creationTime();
  }

  public boolean isEmpty() {
    DataTracker.View view = data.getView();
    return view.sstables.isEmpty() && view.getCurrentMemtable().getOperations() == 0 && view.getCurrentMemtable() == view.getOldestMemtable();
  }

  private boolean isRowCacheEnabled() {
    return metadata.getCaching().rowCache.isEnabled() && CacheService.instance.rowCache.getCapacity() > 0;
  }

  /**
     * Discard all SSTables that were created before given timestamp.
     *
     * Caller should first ensure that comapctions have quiesced.
     *
     * @param truncatedAt The timestamp of the truncation
     *                    (all SSTables before that timestamp are going be marked as compacted)
     *
     * @return the most recent replay position of the truncated data
     */
  public ReplayPosition discardSSTables(long truncatedAt) {
    assert data.getCompacting().isEmpty() : data.getCompacting();
    List<SSTableReader> truncatedSSTables = new ArrayList<>();
    for (SSTableReader sstable : getSSTables()) {
      if (!sstable.newSince(truncatedAt)) {
        truncatedSSTables.add(sstable);
      }
    }
    if (truncatedSSTables.isEmpty()) {
      return ReplayPosition.NONE;
    }
    markObsolete(truncatedSSTables, OperationType.UNKNOWN);
    return ReplayPosition.getReplayPosition(truncatedSSTables);
  }

  public double getDroppableTombstoneRatio() {
    return getDataTracker().getDroppableTombstoneRatio();
  }

  public long trueSnapshotsSize() {
    return directories.trueSnapshotsSize();
  }

  @VisibleForTesting void resetFileIndexGenerator() {
    fileIndexGenerator.set(0);
  }
}