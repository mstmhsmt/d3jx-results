package org.apache.cassandra.distributed.impl;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.apache.cassandra.batchlog.BatchlogManager;
import org.apache.cassandra.concurrent.ScheduledExecutors;
import org.apache.cassandra.concurrent.SharedExecutorPool;
import org.apache.cassandra.concurrent.StageManager;
import org.apache.cassandra.config.Config;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.Schema;
import org.apache.cassandra.cql3.CQLStatement;
import org.apache.cassandra.cql3.QueryOptions;
import org.apache.cassandra.cql3.QueryProcessor;
import org.apache.cassandra.cql3.statements.ParsedStatement;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.Keyspace;
import org.apache.cassandra.db.Memtable;
import org.apache.cassandra.db.SystemKeyspace;
import org.apache.cassandra.db.commitlog.CommitLog;
import org.apache.cassandra.db.compaction.CompactionManager;
import org.apache.cassandra.dht.IPartitioner;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.distributed.api.Feature;
import org.apache.cassandra.distributed.api.ICluster;
import org.apache.cassandra.distributed.api.ICoordinator;
import org.apache.cassandra.distributed.api.IInstanceConfig;
import org.apache.cassandra.distributed.api.IListen;
import org.apache.cassandra.distributed.api.IMessage;
import org.apache.cassandra.gms.ApplicationState;
import org.apache.cassandra.gms.Gossiper;
import org.apache.cassandra.gms.VersionedValue;
import org.apache.cassandra.hints.HintsService;
import org.apache.cassandra.index.SecondaryIndexManager;
import org.apache.cassandra.io.sstable.format.SSTableReader;
import org.apache.cassandra.io.util.DataInputBuffer;
import org.apache.cassandra.io.util.DataOutputBuffer;
import org.apache.cassandra.locator.InetAddressAndPort;
import org.apache.cassandra.net.IMessageSink;
import org.apache.cassandra.net.MessageDeliveryTask;
import org.apache.cassandra.net.MessageIn;
import org.apache.cassandra.net.MessageOut;
import org.apache.cassandra.net.MessagingService;
import org.apache.cassandra.schema.LegacySchemaMigrator;
import org.apache.cassandra.service.ClientState;
import org.apache.cassandra.service.PendingRangeCalculatorService;
import org.apache.cassandra.service.QueryState;
import org.apache.cassandra.service.StorageService;
import org.apache.cassandra.transport.messages.ResultMessage;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.Throwables;
import org.apache.cassandra.utils.concurrent.Ref;
import org.apache.cassandra.utils.memory.BufferPool;
import org.apache.cassandra.db.BatchlogManager;
import org.apache.cassandra.db.HintedHandOffManager;
import org.apache.cassandra.distributed.api.IInstance;
import org.apache.cassandra.io.sstable.IndexSummaryManager;
import org.apache.cassandra.streaming.StreamCoordinator;
import org.apache.cassandra.utils.NanoTimeToCurrentTimeMillis;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.apache.cassandra.distributed.api.Feature.GOSSIP;
import static org.apache.cassandra.distributed.api.Feature.NETWORK;

public class Instance extends IsolatedExecutor implements IInvokableInstance {
  public final IInstanceConfig config;

  Instance(IInstanceConfig config, ClassLoader classLoader) {
    super("node" + config.num(), classLoader);
    this.config = config;
    InstanceIDDefiner.setInstanceId(config.num());
    FBUtilities.setBroadcastInetAddress(config.broadcastAddressAndPort().address);

<<<<<<< commits-rmx_100/apache/cassandra/54aeb507593dd4e3d5b8db34bc9fa6164ba504bc/Instance-1b385fb.java
    acceptsOnInstance((IInstanceConfig override) -> {
      Config.setOverrideLoadConfig(() -> loadConfig(override));
      DatabaseDescriptor.setDaemonInitialized();
    }).accept(config)
=======
    Config.setOverrideLoadConfig(() -> loadConfig(config))
>>>>>>> commits-rmx_100/apache/cassandra/8dcaa12baa97ce870f23ff9045f968f2fa28b2cc/Instance-29426cb.java
    ;
  }

  public IInstanceConfig config() {
    return config;
  }

  public ICoordinator coordinator() {
    return new Coordinator(this);
  }

  public IListen listen() {
    return new Listen(this);
  }

  @Override public InetAddressAndPort broadcastAddressAndPort() {
    return config.broadcastAddressAndPort();
  }

  public Object[][] executeInternal(String query, Object... args) {
    return sync(() -> {
      ParsedStatement.Prepared prepared = QueryProcessor.prepareInternal(query);
      ResultMessage result = prepared.statement.executeInternal(QueryProcessor.internalQueryState(), QueryProcessor.makeInternalOptions(prepared, args));
      if (result instanceof ResultMessage.Rows) {
        return RowUtil.toObjects((ResultMessage.Rows) result);
      } else {
        return null;
      }
    }).call();
  }

  @Override public UUID schemaVersion() {
    return Schema.instance.getVersion();
  }

  public void startup() {
    throw new UnsupportedOperationException();
  }

  public boolean isShutdown() {
    throw new UnsupportedOperationException();
  }

  @Override public void schemaChangeInternal(String query) {
    sync(() -> {
      try {
        ClientState state = ClientState.forInternalCalls();
        state.setKeyspace(SystemKeyspace.NAME);
        QueryState queryState = new QueryState(state);
        CQLStatement statement = QueryProcessor.parseStatement(query, queryState).statement;
        statement.validate(state);
        QueryOptions options = QueryOptions.forInternalCalls(Collections.emptyList());
        statement.executeInternal(queryState, options);
      } catch (Exception e) {
        throw new RuntimeException("Error setting schema for test (query was: " + query + ")", e);
      }
    }).run();
  }

  private void registerMockMessaging(ICluster cluster) {
    BiConsumer<InetAddressAndPort, IMessage> deliverToInstance = (to, message) -> cluster.get(to).receiveMessage(message);
    BiConsumer<InetAddressAndPort, IMessage> deliverToInstanceIfNotFiltered = (to, message) -> {
      if (cluster.filters().permit(this, cluster.get(to), message.verb())) {
        deliverToInstance.accept(to, message);
      }
    };
    Map<InetAddress, InetAddressAndPort> addressAndPortMap = new HashMap<>();
    cluster.stream().forEach((instance) -> {
      InetAddressAndPort addressAndPort = instance.broadcastAddressAndPort();
      if (!addressAndPort.equals(instance.config().broadcastAddressAndPort())) {
        throw new IllegalStateException("addressAndPort mismatch: " + addressAndPort + " vs " + instance.config().broadcastAddressAndPort());
      }
      InetAddressAndPort prev = addressAndPortMap.put(addressAndPort.address, addressAndPort);
      if (null != prev) {
        throw new IllegalStateException("This version of Cassandra does not support multiple nodes with the same InetAddress: " + addressAndPort + " vs " + prev);
      }
    });
    MessagingService.instance().addMessageSink(new MessageDeliverySink(deliverToInstanceIfNotFiltered, addressAndPortMap::get));
  }

  private class MessageDeliverySink implements IMessageSink {
    private final BiConsumer<InetAddressAndPort, IMessage> deliver;

    private final Function<InetAddress, InetAddressAndPort> lookupAddressAndPort;

    MessageDeliverySink(BiConsumer<InetAddressAndPort, IMessage> deliver, Function<InetAddress, InetAddressAndPort> lookupAddressAndPort) {
      this.deliver = deliver;
      this.lookupAddressAndPort = lookupAddressAndPort;
    }

    public boolean allowOutgoingMessage(MessageOut messageOut, int id, InetAddress to) {
      try (DataOutputBuffer out = new DataOutputBuffer(1024)) {
        InetAddressAndPort from = broadcastAddressAndPort();
        assert from.equals(lookupAddressAndPort.apply(messageOut.from));
        InetAddressAndPort toFull = lookupAddressAndPort.apply(to);
        int version = MessagingService.instance().getVersion(to);
        out.writeInt(MessagingService.PROTOCOL_MAGIC);
        out.writeInt(id);
        long timestamp = System.currentTimeMillis();
        out.writeInt((int) timestamp);
        messageOut.serialize(out, version);
        deliver.accept(toFull, new Message(messageOut.verb.ordinal(), out.toByteArray(), id, version, from));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return false;
    }

    public boolean allowIncomingMessage(MessageIn message, int id) {
      return true;
    }
  }

  public void receiveMessage(IMessage imessage) {
    sync(() -> {
      try (
<<<<<<< commits-rmx_100/apache/cassandra/54aeb507593dd4e3d5b8db34bc9fa6164ba504bc/Instance-1b385fb.java
      DataInputBuffer in = new DataInputBuffer(message.bytes())
=======
>>>>>>> Unknown file: This is a bug in JDime.
      ; DataInputStream input = new DataInputStream(new ByteArrayInputStream(imessage.bytes()))) {
        int version = imessage.version();
        MessagingService.validateMagic(input.readInt());
        int id;
        if (version < MessagingService.VERSION_20) {
          id = Integer.parseInt(input.readUTF());
        } else {
          id = input.readInt();
        }
        assert imessage.id() == id;
        long timestamp = System.currentTimeMillis();
        boolean isCrossNodeTimestamp = false;
        int partial = input.readInt();
        if (DatabaseDescriptor.hasCrossNodeTimeout()) {
          long crossNodeTimestamp = (timestamp & 0xFFFFFFFF00000000L) | (((partial & 0xFFFFFFFFL) << 2) >> 2);
          isCrossNodeTimestamp = (timestamp != crossNodeTimestamp);
          timestamp = crossNodeTimestamp;
        }
        MessageIn message = MessageIn.read(input, version, id);
        if (message == null) {
          return;
        }
        if (version <= MessagingService.current_version) {
          MessagingService.instance().receive(message, id, timestamp, isCrossNodeTimestamp);
        }
      } catch (Throwable t) {
        throw new RuntimeException("Exception occurred on node " + broadcastAddressAndPort(), t);
      }
    }).run();
  }

  public int getMessagingVersion() {
    return callsOnInstance(() -> MessagingService.current_version).call();
  }

  public void setMessagingVersion(InetAddressAndPort endpoint, int version) {
    runOnInstance(() -> MessagingService.instance().setVersion(endpoint.address, version));
  }

  @Override public void startup(ICluster cluster, Set<Feature> with) {
    sync(() -> {
      try {
        mkdirs();
        DatabaseDescriptor.createAllDirectories();
        SystemKeyspace.persistLocalMetadata();
        LegacySchemaMigrator.migrate();
        try {
          Schema.instance.loadFromDisk();
        } catch (Exception e) {
          throw e;
        }
        Keyspace.setInitialized();
        try {
          CommitLog.instance.recover();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        if (config.has(NETWORK)) {
          registerFilter(cluster);
          MessagingService.instance().listen();
        } else {
          registerMockMessaging(cluster);
        }
        if (with.contains(Feature.GOSSIP) || with.contains(Feature.NETWORK)) {
          StorageService.instance.prepareToJoin();
          StorageService.instance.joinTokenRing(1000);
        } else {
          initializeRing(cluster);
          registerMockMessaging(cluster);
        }
        SystemKeyspace.finishStartup();
        if (!FBUtilities.getBroadcastAddress().equals(broadcastAddressAndPort().address)) {
          throw new IllegalStateException();
        }
        if (DatabaseDescriptor.getStoragePort() != broadcastAddressAndPort().port) {
          throw new IllegalStateException();
        }
      } catch (Throwable t) {
        if (t instanceof RuntimeException) {
          throw (RuntimeException) t;
        }
        throw new RuntimeException(t);
      }
    }).run();
  }

  private void mkdirs() {
    new File(config.getString("saved_caches_directory")).mkdirs();
    new File(config.getString("hints_directory")).mkdirs();
    new File(config.getString("commitlog_directory")).mkdirs();
    for (String dir : (String[]) config.get("data_file_directories")) {
      new File(dir).mkdirs();
    }
  }

  private static Config loadConfig(IInstanceConfig overrides) {
    Config config = new Config();
    overrides.propagate(config);
    return config;
  }

  private void initializeRing(ICluster cluster) {
    String partitionerName = config.getString("partitioner");
    List<String> initialTokens = new ArrayList<>();
    List<InetAddressAndPort> hosts = new ArrayList<>();
    List<UUID> hostIds = new ArrayList<>();
    for (int i = 1; i <= cluster.size(); ++i) {
      IInstanceConfig config = cluster.get(i).config();
      initialTokens.add(config.getString("initial_token"));
      hosts.add(config.broadcastAddressAndPort());
      hostIds.add(config.hostId());
    }
    try {
      IPartitioner partitioner = FBUtilities.newPartitioner(partitionerName);
      StorageService storageService = StorageService.instance;
      List<Token> tokens = new ArrayList<>();
      for (String token : initialTokens) {
        tokens.add(partitioner.getTokenFactory().fromString(token));
      }
      for (int i = 0; i < tokens.size(); i++) {
        InetAddressAndPort ep = hosts.get(i);
        UUID hostId = hostIds.get(i);
        Token token = tokens.get(i);
        Gossiper.runInGossipStageBlocking(() -> {
          Gossiper.instance.initializeNodeUnsafe(ep.address, hostId, 1);
          Gossiper.instance.injectApplicationState(ep.address, ApplicationState.TOKENS, new VersionedValue.VersionedValueFactory(partitioner).tokens(Collections.singleton(token)));
          storageService.onChange(ep.address, ApplicationState.STATUS, new VersionedValue.VersionedValueFactory(partitioner).normal(Collections.singleton(token)));
          Gossiper.instance.realMarkAlive(ep.address, Gossiper.instance.getEndpointStateForEndpoint(ep.address));
        });
        int version = Math.min(MessagingService.current_version, cluster.get(ep).getMessagingVersion());
        MessagingService.instance().setVersion(ep.address, version);
      }
      for (int i = 0; i < tokens.size(); ++i) {
        assert storageService.getTokenMetadata().isMember(hosts.get(i).address);
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  public Future<Void> shutdown() {
    return shutdown(true);
  }

  public Future<Void> shutdown(boolean graceful) {
    if (!graceful) {
      MessagingService.instance().shutdown(false);
    }
    Future<?> future = async((ExecutorService executor) -> {
      Throwable error = null;
      if (config.has(GOSSIP) || config.has(NETWORK)) {
        StorageService.instance.shutdownServer();
        error = parallelRun(error, executor, () -> NanoTimeToCurrentTimeMillis.shutdown(MINUTES.toMillis(1L)));
      }
      error = parallelRun(error, executor, () -> Gossiper.instance.stopShutdownAndWait(1L, MINUTES), CompactionManager.instance::forceShutdown, errorexecutorGossiper.instance::stopCompactionManager.instance::forceShutdownBatchlogManager.instance::shutdownHintsService.instance::shutdownBlockingSecondaryIndexManager::shutdownExecutorsColumnFamilyStore::shutdownFlushExecutorColumnFamilyStore::shutdownPostFlushExecutorColumnFamilyStore::shutdownReclaimExecutorPendingRangeCalculatorService.instance::shutdownExecutorBufferPool::shutdownLocalCleanerStorageService.instance::shutdownBGMonitorRef::shutdownReferenceReaperMemtable.MEMORY_POOL::shutdownScheduledExecutors::shutdownAndWaitSSTableReader::shutdownBlocking);
      error = parallelRun(error, executor, CommitLog.instance::shutdownBlocking, MessagingService.instance()::shutdown);

<<<<<<< commits-rmx_100/apache/cassandra/54aeb507593dd4e3d5b8db34bc9fa6164ba504bc/Instance-1b385fb.java
      error = parallelRun(error, executor, StageManager::shutdownAndWait, SharedExecutorPool.SHARED::shutdownAndWait);
=======
>>>>>>> Unknown file: This is a bug in JDime.

      error = parallelRun(error, executor, errorexecutorCommitLog.instance::shutdownBlocking);
      Throwables.maybeFail(error);
    }).apply(isolatedExecutor);
    return CompletableFuture.runAsync(ThrowingRunnable.toRunnable(future::get), isolatedExecutor).thenRun(super::shutdown);
  }

  private static Throwable parallelRun(Throwable accumulate, ExecutorService runOn, ThrowingRunnable... runnables) {
    List<Future<Throwable>> results = new ArrayList<>();
    for (ThrowingRunnable runnable : runnables) {
      results.add(runOn.submit(() -> {
        try {
          runnable.run();
          return null;
        } catch (Throwable t) {
          return t;
        }
      }));
    }
    for (Future<Throwable> future : results) {
      try {
        Throwable t = future.get();
        if (t != null) {
          throw t;
        }
      } catch (Throwable t) {
        accumulate = Throwables.merge(accumulate, t);
      }
    }
    return accumulate;
  }

  private void registerFilter(ICluster cluster) {
    IInstance instance = this;
    MessagingService.instance().addMessageSink(new IMessageSink() {
      public boolean allowOutgoingMessage(MessageOut message, int id, InetAddress toAddress) {
        IInstance to = cluster.get(InetAddressAndPort.getByAddressOverrideDefaults(toAddress, instance.config().broadcastAddressAndPort().port));
        return cluster.filters().permit(instance, to, message.verb.ordinal());
      }

      public boolean allowIncomingMessage(MessageIn message, int id) {
        return true;
      }
    });
  }
}