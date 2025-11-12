package org.apache.cassandra.db.compaction;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.apache.cassandra.SchemaLoader;
import org.apache.cassandra.Util;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.*;
import org.apache.cassandra.db.columniterator.IColumnIterator;
import org.apache.cassandra.db.columniterator.IdentityQueryFilter;
import org.apache.cassandra.db.compaction.OperationType;
import org.apache.cassandra.db.filter.QueryFilter;
import org.apache.cassandra.db.filter.QueryPath;
import org.apache.cassandra.io.sstable.*;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.FBUtilities;

public class CompactionsTest extends SchemaLoader {
  public static final String TABLE1 = "Keyspace1";

  @Test public void testBlacklistingWithSizeTieredCompactionStrategy() throws Exception {
    testBlacklisting(SizeTieredCompactionStrategy.class.getCanonicalName());
  }

  @Test public void testBlacklistingWithLeveledCompactionStrategy() throws Exception {
    testBlacklisting(LeveledCompactionStrategy.class.getCanonicalName());
  }

  @Test public void testStandardColumnCompactions() throws IOException, ExecutionException, InterruptedException {
    Table table = Table.open(TABLE1);
    ColumnFamilyStore cfs = table.getColumnFamilyStore("Standard1");
    final int ROWS_PER_SSTABLE = 10;
    final int SSTABLES = DatabaseDescriptor.getIndexInterval() * 3 / ROWS_PER_SSTABLE;
    cfs.disableAutoCompaction();
    long maxTimestampExpected = Long.MIN_VALUE;
    Set<DecoratedKey> inserted = new HashSet<DecoratedKey>();
    for (int j = 0; j < SSTABLES; j++) {
      for (int i = 0; i < ROWS_PER_SSTABLE; i++) {
        DecoratedKey key = Util.dk(String.valueOf(i % 2));
        RowMutation rm = new RowMutation(TABLE1, key.key);
        long timestamp = j * ROWS_PER_SSTABLE + i;
        rm.add(new QueryPath("Standard1", null, ByteBufferUtil.bytes(String.valueOf(i / 2))), ByteBufferUtil.EMPTY_BYTE_BUFFER, timestamp);
        maxTimestampExpected = Math.max(timestamp, maxTimestampExpected);
        rm.apply();
        inserted.add(key);
      }
      cfs.forceBlockingFlush();
      assertMaxTimestamp(cfs, maxTimestampExpected);
      assertEquals(inserted.toString(), inserted.size(), Util.getRangeSlice(cfs).size());
    }
    forceCompactions(cfs);
    assertEquals(inserted.size(), Util.getRangeSlice(cfs).size());
    assertMaxTimestamp(cfs, maxTimestampExpected);
    cfs.truncate();
  }

  @Test public void testSuperColumnCompactions() throws IOException, ExecutionException, InterruptedException {
    Table table = Table.open(TABLE1);
    ColumnFamilyStore cfs = table.getColumnFamilyStore("Super1");
    final int ROWS_PER_SSTABLE = 10;
    final int SSTABLES = DatabaseDescriptor.getIndexInterval() * 3 / ROWS_PER_SSTABLE;
    cfs.disableAutoCompaction();
    long maxTimestampExpected = Long.MIN_VALUE;
    Set<DecoratedKey> inserted = new HashSet<DecoratedKey>();
    ByteBuffer superColumn = ByteBufferUtil.bytes("TestSuperColumn");
    for (int j = 0; j < SSTABLES; j++) {
      for (int i = 0; i < ROWS_PER_SSTABLE; i++) {
        DecoratedKey key = Util.dk(String.valueOf(i % 2));
        RowMutation rm = new RowMutation(TABLE1, key.key);
        long timestamp = j * ROWS_PER_SSTABLE + i;
        rm.add(new QueryPath("Super1", superColumn, ByteBufferUtil.bytes((long) (i / 2))), ByteBufferUtil.EMPTY_BYTE_BUFFER, timestamp);
        maxTimestampExpected = Math.max(timestamp, maxTimestampExpected);
        rm.apply();
        inserted.add(key);
      }
      cfs.forceBlockingFlush();
      assertMaxTimestamp(cfs, maxTimestampExpected);
      assertEquals(inserted.toString(), inserted.size(), Util.getRangeSlice(cfs, superColumn).size());
    }
    forceCompactions(cfs);
    assertEquals(inserted.size(), Util.getRangeSlice(cfs, superColumn).size());
    assertMaxTimestamp(cfs, maxTimestampExpected);
  }

  @Test public void testSuperColumnTombstones() throws IOException, ExecutionException, InterruptedException {
    Table table = Table.open(TABLE1);
    ColumnFamilyStore cfs = table.getColumnFamilyStore("Super1");
    cfs.disableAutoCompaction();
    DecoratedKey key = Util.dk("tskey");
    ByteBuffer scName = ByteBufferUtil.bytes("TestSuperColumn");
    RowMutation rm = new RowMutation(TABLE1, key.key);
    rm.add(new QueryPath("Super1", scName, ByteBufferUtil.bytes(0)), ByteBufferUtil.EMPTY_BYTE_BUFFER, FBUtilities.timestampMicros());
    rm.apply();
    cfs.forceBlockingFlush();
    rm = new RowMutation(TABLE1, key.key);
    rm.delete(new QueryPath("Super1", scName), FBUtilities.timestampMicros());
    rm.apply();
    cfs.forceBlockingFlush();
    CompactionManager.instance.performMaximal(cfs);
    assertEquals(1, cfs.getSSTables().size());
    SSTableReader sstable = cfs.getSSTables().iterator().next();
    SSTableScanner scanner = sstable.getScanner(new QueryFilter(null, new QueryPath("Super1", scName), new IdentityQueryFilter()));
    scanner.seekTo(key);
    IColumnIterator iter = scanner.next();
    assertEquals(key, iter.getKey());
    SuperColumn sc = (SuperColumn) iter.next();
    assert sc.getSubColumns().isEmpty();
  }

  public void assertMaxTimestamp(ColumnFamilyStore cfs, long maxTimestampExpected) {
    long maxTimestampObserved = Long.MIN_VALUE;
    for (SSTableReader sstable : cfs.getSSTables()) {
      maxTimestampObserved = Math.max(sstable.getMaxTimestamp(), maxTimestampObserved);
    }
    assertEquals(maxTimestampExpected, maxTimestampObserved);
  }

  private void forceCompactions(ColumnFamilyStore cfs) throws ExecutionException, InterruptedException {
    cfs.setMinimumCompactionThreshold(2);
    cfs.setMaximumCompactionThreshold(4);
    do {
      ArrayList<Future<?>> compactions = new ArrayList<Future<?>>();
      for (int i = 0; i < 10; i++) {
        compactions.add(CompactionManager.instance.submitBackground(cfs));
      }
      FBUtilities.waitOnFutures(compactions);
    } while(CompactionManager.instance.getPendingTasks() > 0 || CompactionManager.instance.getActiveCompactions() > 0);
    if (cfs.getSSTables().size() > 1) {
      CompactionManager.instance.performMaximal(cfs);
    }
  }

  @Test public void testEchoedRow() throws IOException, ExecutionException, InterruptedException {
    Table table = Table.open(TABLE1);
    ColumnFamilyStore cfs = table.getColumnFamilyStore("Standard2");
    cfs.disableAutoCompaction();
    for (int i = 1; i < 5; i++) {
      DecoratedKey key = Util.dk(String.valueOf(i));
      RowMutation rm = new RowMutation(TABLE1, key.key);
      rm.add(new QueryPath("Standard2", null, ByteBufferUtil.bytes(String.valueOf(i))), ByteBufferUtil.EMPTY_BYTE_BUFFER, i);
      rm.apply();
      if (i % 2 == 0) {
        cfs.forceBlockingFlush();
      }
    }
    Collection<SSTableReader> toCompact = cfs.getSSTables();
    assert toCompact.size() == 2;
    for (int i = 1; i < 5; i++) {
      DecoratedKey key = Util.dk(String.valueOf(i));
      RowMutation rm = new RowMutation(TABLE1, key.key);
      rm.add(new QueryPath("Standard2", null, ByteBufferUtil.bytes(String.valueOf(i))), ByteBufferUtil.EMPTY_BYTE_BUFFER, i);
      rm.apply();
    }
    cfs.forceBlockingFlush();
    SSTableReader tmpSSTable = null;
    for (SSTableReader sstable : cfs.getSSTables()) {
      if (!toCompact.contains(sstable)) {
        tmpSSTable = sstable;
      }
    }
    assert tmpSSTable != null;
    Util.compact(cfs, toCompact, false);
    assertEquals(2, cfs.getSSTables().size());
    cfs.markCompacted(Collections.singleton(tmpSSTable), OperationType.UNKNOWN);
    assertEquals(1, cfs.getSSTables().size());
    assertEquals(4, Util.getRangeSlice(cfs).size());
  }

  @Test public void testDontPurgeAccidentaly() throws IOException, ExecutionException, InterruptedException {
    testDontPurgeAccidentaly("test1", "Super5", false);
    testDontPurgeAccidentaly("test2", "Super5", true);
    testDontPurgeAccidentaly("test1", "SuperDirectGC", false);
    testDontPurgeAccidentaly("test2", "SuperDirectGC", true);
  }

  private void testDontPurgeAccidentaly(String k, String cfname, boolean forceDeserialize) throws IOException, ExecutionException, InterruptedException {
    Table table = Table.open(TABLE1);
    ColumnFamilyStore cfs = table.getColumnFamilyStore(cfname);
    cfs.clearUnsafe();
    cfs.disableAutoCompaction();
    DecoratedKey key = Util.dk(k);
    RowMutation rm = new RowMutation(TABLE1, key.key);
    rm.add(new QueryPath(cfname, ByteBufferUtil.bytes("sc"), ByteBufferUtil.bytes("c")), ByteBufferUtil.EMPTY_BYTE_BUFFER, 0);
    rm.apply();
    cfs.forceBlockingFlush();
    Collection<SSTableReader> sstablesBefore = cfs.getSSTables();
    QueryFilter filter = QueryFilter.getIdentityFilter(key, new QueryPath(cfname, null, null));
    assert !cfs.getColumnFamily(filter).isEmpty();
    rm = new RowMutation(TABLE1, key.key);
    rm.delete(new QueryPath(cfname, null, null), 2);
    rm.apply();
    ColumnFamily cf = cfs.getColumnFamily(filter);
    assert cf == null || cf.isEmpty() : "should be empty: " + cf;
    cfs.forceBlockingFlush();
    Collection<SSTableReader> sstablesAfter = cfs.getSSTables();
    Collection<SSTableReader> toCompact = new ArrayList<SSTableReader>();
    for (SSTableReader sstable : sstablesAfter) {
      if (!sstablesBefore.contains(sstable)) {
        toCompact.add(sstable);
      }
    }
    Util.compact(cfs, toCompact, forceDeserialize);
    cf = cfs.getColumnFamily(filter);
    assert cf == null || cf.isEmpty() : "should be empty: " + cf;
  }

  public void testBlacklisting(String compactionStrategy) throws Exception {
    Table table = Table.open(TABLE1);
    final ColumnFamilyStore cfs = table.getColumnFamilyStore("Standard1");
    final int ROWS_PER_SSTABLE = 10;
    final int SSTABLES = DatabaseDescriptor.getIndexInterval() * 2 / ROWS_PER_SSTABLE;
    cfs.setCompactionStrategyClass(compactionStrategy);
    cfs.disableAutoCompaction();
    long maxTimestampExpected = Long.MIN_VALUE;
    Set<DecoratedKey> inserted = new HashSet<DecoratedKey>();
    for (int j = 0; j < SSTABLES; j++) {
      for (int i = 0; i < ROWS_PER_SSTABLE; i++) {
        DecoratedKey key = Util.dk(String.valueOf(i % 2));
        RowMutation rm = new RowMutation(TABLE1, key.key);
        long timestamp = j * ROWS_PER_SSTABLE + i;
        rm.add(new QueryPath("Standard1", null, ByteBufferUtil.bytes(String.valueOf(i / 2))), ByteBufferUtil.EMPTY_BYTE_BUFFER, timestamp);
        maxTimestampExpected = Math.max(timestamp, maxTimestampExpected);
        rm.apply();
        inserted.add(key);
      }
      cfs.forceBlockingFlush();
      assertMaxTimestamp(cfs, maxTimestampExpected);
      assertEquals(inserted.toString(), inserted.size(), Util.getRangeSlice(cfs).size());
    }
    Collection<SSTableReader> sstables = cfs.getSSTables();
    int currentSSTable = 0;
    int sstablesToCorrupt = 8;
    for (SSTableReader sstable : sstables) {
      if (currentSSTable + 1 > sstablesToCorrupt) {
        break;
      }
      RandomAccessFile raf = null;
      try {
        raf = new RandomAccessFile(sstable.getFilename(), "rw");
        assertNotNull(raf);
        raf.write(0xFFFFFF);
      }  finally {
        FileUtils.closeQuietly(raf);
      }
      currentSSTable++;
    }
    int failures = 0;
    System.err.close();
    try {
      for (int i = 0; i < sstables.size(); i++) {
        try {
          cfs.forceMajorCompaction();
        } catch (Exception e) {
          failures++;
          continue;
        }
        assertEquals(sstablesToCorrupt + 1, cfs.getSSTables().size());
        break;
      }
    }  finally {
      System.setErr(new PrintStream(new ByteArrayOutputStream()));
    }
    cfs.truncate();
    assertEquals(failures, sstablesToCorrupt);
  }
}