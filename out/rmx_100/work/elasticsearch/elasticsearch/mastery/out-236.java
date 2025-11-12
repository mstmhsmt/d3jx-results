package org.elasticsearch.tribe;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.support.master.TransportMasterNodeReadAction;
import org.elasticsearch.cluster.ClusterChangedEvent;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.ClusterStateListener;
import org.elasticsearch.cluster.ClusterStateTaskConfig;
import org.elasticsearch.cluster.ClusterStateTaskExecutor;
import org.elasticsearch.cluster.block.ClusterBlock;
import org.elasticsearch.cluster.block.ClusterBlockLevel;
import org.elasticsearch.cluster.block.ClusterBlocks;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.cluster.routing.IndexRoutingTable;
import org.elasticsearch.cluster.routing.RoutingTable;
import org.elasticsearch.common.Priority;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.ConcurrentCollections;
import org.elasticsearch.common.util.set.Sets;
import org.elasticsearch.discovery.DiscoveryModule;
import org.elasticsearch.discovery.DiscoverySettings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.gateway.GatewayService;
import org.elasticsearch.node.Node;
import org.elasticsearch.rest.RestStatus;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import static java.util.Collections.unmodifiableMap;
import org.elasticsearch.cluster.service.ClusterService;
import org.elasticsearch.common.network.NetworkModule;
import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Setting.Property;
import org.elasticsearch.transport.TransportSettings;
import java.util.Arrays;

public class TribeService extends AbstractLifecycleComponent<TribeService> {

    public static final ClusterBlock TRIBE_METADATA_BLOCK = new ClusterBlock(10, "tribe node, metadata not allowed", false, false, RestStatus.BAD_REQUEST, EnumSet.of(ClusterBlockLevel.METADATA_READ, ClusterBlockLevel.METADATA_WRITE));

    public static final ClusterBlock TRIBE_WRITE_BLOCK = new ClusterBlock(11, "tribe node, write not allowed", false, false, RestStatus.BAD_REQUEST, EnumSet.of(ClusterBlockLevel.WRITE));

    public static Settings processSettings(Settings settings) {
        if (TRIBE_NAME_SETTING.exists(settings)) {
            Settings.Builder sb = Settings.builder().put(settings);
            for (String s : settings.getAsMap().keySet()) {
                if (s.startsWith("tribe.") && !s.equals(TRIBE_NAME_SETTING.getKey())) {
                    sb.remove(s);
                }
            }
            return sb.build();
        }
        Map<String, Settings> nodesSettings = settings.getGroups("tribe", true);
        if (nodesSettings.isEmpty()) {
            return settings;
        }
        Settings.Builder sb = Settings.builder().put(settings);
        sb.put(Node.NODE_MASTER_SETTING.getKey(), false);
        sb.put(Node.NODE_DATA_SETTING.getKey(), false);
        sb.put(Node.NODE_INGEST_SETTING.getKey(), false);
        sb.put(DiscoveryModule.DISCOVERY_TYPE_SETTING.getKey(), "local");
        sb.put(DiscoverySettings.INITIAL_STATE_TIMEOUT_SETTING.getKey(), 0);
        if (sb.get("cluster.name") == null) {
            sb.put("cluster.name", "tribe_" + Strings.randomBase64UUID());
        }
        sb.put(TransportMasterNodeReadAction.FORCE_LOCAL_SETTING.getKey(), true);
        return sb.build();
    }

    final private ClusterService clusterService;

    final private String[] blockIndicesWrite;

    final private String[] blockIndicesRead;

    final private String[] blockIndicesMetadata;

    static final private String ON_CONFLICT_ANY = "any", ON_CONFLICT_DROP = "drop", ON_CONFLICT_PREFER = "prefer_";

    public static final Setting<String> ON_CONFLICT_SETTING = new Setting<>("tribe.on_conflict", ON_CONFLICT_ANY, (s) -> {
        switch(s) {
            case ON_CONFLICT_ANY:
            case ON_CONFLICT_DROP:
                return s;
            default:
                if (s.startsWith(ON_CONFLICT_PREFER) && s.length() > ON_CONFLICT_PREFER.length()) {
                    return s;
                }
                throw new IllegalArgumentException("Invalid value for [tribe.on_conflict] must be either [any, drop or start with prefer_] but was: [" + s + "]");
        }
    }, Property.NodeScope);

    public static final Set<String> TRIBE_SETTING_KEYS = Sets.newHashSet(TRIBE_NAME_SETTING.getKey(), ON_CONFLICT_SETTING.getKey(), BLOCKS_METADATA_INDICES_SETTING.getKey(), BLOCKS_METADATA_SETTING.getKey(), BLOCKS_READ_INDICES_SETTING.getKey(), BLOCKS_WRITE_INDICES_SETTING.getKey(), BLOCKS_WRITE_SETTING.getKey());

    final private String onConflict;

    final private Set<String> droppedIndices = ConcurrentCollections.newConcurrentSet();

    final private List<Node> nodes = new CopyOnWriteArrayList<>();

    @Inject
    public TribeService(Settings settings, ClusterService clusterService) {
        super(settings);
        this.clusterService = clusterService;
        Map<String, Settings> nodesSettings = new HashMap<>(settings.getGroups("tribe", true));
        nodesSettings.remove("blocks");
        nodesSettings.remove("on_conflict");
        for (Map.Entry<String, Settings> entry : nodesSettings.entrySet()) {
            Settings clientSettings = buildClientSettings(entry.getKey(), settings, entry.getValue());
            sb.put(Node.NODE_DATA_SETTING.getKey(), false);
            sb.put(Node.NODE_MASTER_SETTING.getKey(), false);
            sb.put(Node.NODE_INGEST_SETTING.getKey(), false);
            nodes.add(new TribeClientNode(clientSettings));
        }
        this.blockIndicesMetadata = BLOCKS_METADATA_INDICES_SETTING.get(settings).toArray(Strings.EMPTY_ARRAY);
        this.blockIndicesRead = BLOCKS_READ_INDICES_SETTING.get(settings).toArray(Strings.EMPTY_ARRAY);
        this.blockIndicesWrite = BLOCKS_WRITE_INDICES_SETTING.get(settings).toArray(Strings.EMPTY_ARRAY);
        if (!nodes.isEmpty()) {
            if (BLOCKS_WRITE_SETTING.get(settings)) {
                clusterService.addInitialStateBlock(TRIBE_WRITE_BLOCK);
            }
            if (BLOCKS_METADATA_SETTING.get(settings)) {
                clusterService.addInitialStateBlock(TRIBE_METADATA_BLOCK);
            }
        }
        this.onConflict = ON_CONFLICT_SETTING.get(settings);
    }

    @Override
    protected void doStart() {
        if (nodes.isEmpty() == false) {
            clusterService.removeInitialStateBlock(DiscoverySettings.NO_MASTER_BLOCK_ID);
            clusterService.removeInitialStateBlock(GatewayService.STATE_NOT_RECOVERED_BLOCK);
        }
    }

    public void startNodes() {
        for (Node node : nodes) {
            try {
                node.injector().getInstance(ClusterService.class).add(new TribeClusterStateListener(node));
                node.start();
            } catch (Throwable e) {
                for (Node otherNode : nodes) {
                    try {
                        otherNode.close();
                    } catch (Throwable t) {
                        logger.warn("failed to close node {} on failed start", t, otherNode);
                    }
                }
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new ElasticsearchException(e);
            }
        }
    }

    @Override
    protected void doStop() {
        doClose();
    }

    @Override
    protected void doClose() {
        for (Node node : nodes) {
            try {
                node.close();
            } catch (Throwable t) {
                logger.warn("failed to close node {}", t, node);
            }
        }
    }

    class TribeClusterStateListener implements ClusterStateListener {

        final private String tribeName;

        final private TribeNodeClusterStateTaskExecutor executor;

        TribeClusterStateListener(Node tribeNode) {
            String tribeName = TRIBE_NAME_SETTING.get(tribeNode.settings());
            this.tribeName = tribeName;
            executor = new TribeNodeClusterStateTaskExecutor(tribeName);
        }

        @Override
        public void clusterChanged(final ClusterChangedEvent event) {
            logger.debug("[{}] received cluster event, [{}]", tribeName, event.source());
            clusterService.submitStateUpdateTask("cluster event from " + tribeName + ", " + event.source(), event, ClusterStateTaskConfig.build(Priority.NORMAL), executor, (source, t) -> logger.warn("failed to process [{}]", t, source));
        }
    }

    class TribeNodeClusterStateTaskExecutor implements ClusterStateTaskExecutor<ClusterChangedEvent> {

        final private String tribeName;

        TribeNodeClusterStateTaskExecutor(String tribeName) {
            this.tribeName = tribeName;
        }

        @Override
        public boolean runOnlyOnMaster() {
            return false;
        }

        @Override
        public BatchResult<ClusterChangedEvent> execute(ClusterState currentState, List<ClusterChangedEvent> tasks) throws Exception {
            ClusterState accumulator = ClusterState.builder(currentState).build();
            BatchResult.Builder<ClusterChangedEvent> builder = BatchResult.builder();
            try {
                accumulator = applyUpdate(accumulator, tasks.get(tasks.size() - 1));
                builder.successes(tasks);
            } catch (Throwable t) {
                builder.failures(tasks, t);
            }
            return builder.build(accumulator);
        }

        private void removeIndex(ClusterBlocks.Builder blocks, MetaData.Builder metaData, RoutingTable.Builder routingTable, IndexMetaData index) {
            metaData.remove(index.getIndex().getName());
            routingTable.remove(index.getIndex().getName());
            blocks.removeIndexBlocks(index.getIndex().getName());
        }

        private void addNewIndex(ClusterState tribeState, ClusterBlocks.Builder blocks, MetaData.Builder metaData, RoutingTable.Builder routingTable, IndexMetaData tribeIndex) {
            Settings tribeSettings = Settings.builder().put(tribeIndex.getSettings()).put(TRIBE_NAME_SETTING.getKey(), tribeName).build();
            metaData.put(IndexMetaData.builder(tribeIndex).settings(tribeSettings));
            routingTable.add(tribeState.routingTable().index(tribeIndex.getIndex()));
            if (Regex.simpleMatch(blockIndicesMetadata, tribeIndex.getIndex().getName())) {
                blocks.addIndexBlock(tribeIndex.getIndex().getName(), IndexMetaData.INDEX_METADATA_BLOCK);
            }
            if (Regex.simpleMatch(blockIndicesRead, tribeIndex.getIndex().getName())) {
                blocks.addIndexBlock(tribeIndex.getIndex().getName(), IndexMetaData.INDEX_READ_BLOCK);
            }
            if (Regex.simpleMatch(blockIndicesWrite, tribeIndex.getIndex().getName())) {
                blocks.addIndexBlock(tribeIndex.getIndex().getName(), IndexMetaData.INDEX_WRITE_BLOCK);
            }
        }

        private ClusterState applyUpdate(ClusterState currentState, ClusterChangedEvent task) {
            boolean clusterStateChanged = false;
            ClusterState tribeState = task.state();
            DiscoveryNodes.Builder nodes = DiscoveryNodes.builder(currentState.nodes());
            for (DiscoveryNode discoNode : currentState.nodes()) {
                String markedTribeName = discoNode.attributes().get(TRIBE_NAME_SETTING.getKey());
                if (markedTribeName != null && markedTribeName.equals(tribeName)) {
                    if (tribeState.nodes().get(discoNode.id()) == null) {
                        clusterStateChanged = true;
                        logger.info("[{}] removing node [{}]", tribeName, discoNode);
                        nodes.remove(discoNode.id());
                    }
                }
            }
            for (DiscoveryNode tribe : tribeState.nodes()) {
                if (currentState.nodes().get(tribe.id()) == null) {
                    Map<String, String> tribeAttr = new HashMap<>();
                    for (ObjectObjectCursor<String, String> attr : tribe.attributes()) {
                        tribeAttr.put(attr.key, attr.value);
                    }
                    tribeAttr.put(TRIBE_NAME_SETTING.getKey(), tribeName);
                    DiscoveryNode discoNode = new DiscoveryNode(tribe.name(), tribe.id(), tribe.getHostName(), tribe.getHostAddress(), tribe.address(), unmodifiableMap(tribeAttr), tribe.version());
                    clusterStateChanged = true;
                    logger.info("[{}] adding node [{}]", tribeName, discoNode);
                    nodes.put(discoNode);
                }
            }
            ClusterBlocks.Builder blocks = ClusterBlocks.builder().blocks(currentState.blocks());
            MetaData.Builder metaData = MetaData.builder(currentState.metaData());
            RoutingTable.Builder routingTable = RoutingTable.builder(currentState.routingTable());
            for (IndexMetaData index : currentState.metaData()) {
                String markedTribeName = TRIBE_NAME_SETTING.get(index.getSettings());
                if (markedTribeName != null && markedTribeName.equals(tribeName)) {
                    IndexMetaData tribeIndex = tribeState.metaData().index(index.getIndex());
                    clusterStateChanged = true;
                    if (tribeIndex == null || tribeIndex.getState() == IndexMetaData.State.CLOSE) {
                        logger.info("[{}] removing index {}", tribeName, index.getIndex());
                        removeIndex(blocks, metaData, routingTable, index);
                    } else {
                        routingTable.add(tribeState.routingTable().index(index.getIndex()));
                        Settings tribeSettings = Settings.builder().put(tribeIndex.getSettings()).put(TRIBE_NAME_SETTING.getKey(), tribeName).build();
                        metaData.put(IndexMetaData.builder(tribeIndex).settings(tribeSettings));
                    }
                }
            }
            for (IndexMetaData tribeIndex : tribeState.metaData()) {
                IndexRoutingTable table = tribeState.routingTable().index(tribeIndex.getIndex());
                if (table == null) {
                    continue;
                }
                final String indexName = tribeIndex.getIndex().getName();
                final IndexMetaData indexMetaData = currentState.metaData().index(indexName);
                if (indexMetaData == null) {
                    if (!droppedIndices.contains(indexName)) {
                        clusterStateChanged = true;
                        logger.info("[{}] adding index {}", tribeName, tribeIndex.getIndex());
                        addNewIndex(tribeState, blocks, metaData, routingTable, tribeIndex);
                    }
                } else {
                    String existingFromTribe = TRIBE_NAME_SETTING.get(indexMetaData.getSettings());
                    if (!tribeName.equals(existingFromTribe)) {
                        if (ON_CONFLICT_ANY.equals(onConflict)) {
                        } else if (ON_CONFLICT_DROP.equals(onConflict)) {
                            clusterStateChanged = true;
                            logger.info("[{}] dropping index {} due to conflict with [{}]", tribeName, tribeIndex.getIndex(), existingFromTribe);
                            removeIndex(blocks, metaData, routingTable, tribeIndex);
                            droppedIndices.add(indexName);
                        } else if (onConflict.startsWith(ON_CONFLICT_PREFER)) {
                            String preferredTribeName = onConflict.substring(ON_CONFLICT_PREFER.length());
                            if (tribeName.equals(preferredTribeName)) {
                                clusterStateChanged = true;
                                logger.info("[{}] adding index {}, preferred over [{}]", tribeName, tribeIndex.getIndex(), existingFromTribe);
                                removeIndex(blocks, metaData, routingTable, tribeIndex);
                                addNewIndex(tribeState, blocks, metaData, routingTable, tribeIndex);
                            }
                        }
                    }
                }
            }
            if (!clusterStateChanged) {
                return currentState;
            } else {
                return ClusterState.builder(currentState).incrementVersion().blocks(blocks).nodes(nodes).metaData(metaData).routingTable(routingTable.build()).build();
            }
        }
    }

    public static final Setting<List<String>> BLOCKS_METADATA_INDICES_SETTING = Setting.listSetting("tribe.blocks.metadata.indices", Collections.emptyList(), Function.identity(), Property.NodeScope);

    public static final Setting<List<String>> BLOCKS_READ_INDICES_SETTING = Setting.listSetting("tribe.blocks.read.indices", Collections.emptyList(), Function.identity(), Property.NodeScope);

    public static final Setting<Boolean> BLOCKS_WRITE_SETTING = Setting.boolSetting("tribe.blocks.write", false, Property.NodeScope);

    public static final Setting<List<String>> BLOCKS_WRITE_INDICES_SETTING = Setting.listSetting("tribe.blocks.write.indices", Collections.emptyList(), Function.identity(), Property.NodeScope);

    public static final Setting<Boolean> BLOCKS_METADATA_SETTING = Setting.boolSetting("tribe.blocks.metadata", false, Property.NodeScope);

    public static final Setting<String> TRIBE_NAME_SETTING = Setting.simpleString("tribe.name", Property.NodeScope);

    static final private List<Setting<?>> PASS_THROUGH_SETTINGS = Arrays.asList(NetworkService.GLOBAL_NETWORK_HOST_SETTING, NetworkService.GLOBAL_NETWORK_BINDHOST_SETTING, NetworkService.GLOBAL_NETWORK_PUBLISHHOST_SETTING, TransportSettings.HOST, TransportSettings.BIND_HOST, TransportSettings.PUBLISH_HOST);

    static Settings buildClientSettings(String tribeName, Settings globalSettings, Settings tribeSettings) {
        for (String tribeKey : tribeSettings.getAsMap().keySet()) {
            if (tribeKey.startsWith("path.")) {
                throw new IllegalArgumentException("Setting [" + tribeKey + "] not allowed in tribe client [" + tribeName + "]");
            }
        }
        Settings.Builder sb = Settings.builder().put(tribeSettings);
        sb.put("node.name", globalSettings.get("node.name") + "/" + tribeName);
        sb.put(Environment.PATH_HOME_SETTING.getKey(), Environment.PATH_HOME_SETTING.get(globalSettings));
        if (Environment.PATH_CONF_SETTING.exists(globalSettings)) {
            sb.put(Environment.PATH_CONF_SETTING.getKey(), Environment.PATH_CONF_SETTING.get(globalSettings));
        }
        if (Environment.PATH_PLUGINS_SETTING.exists(globalSettings)) {
            sb.put(Environment.PATH_PLUGINS_SETTING.getKey(), Environment.PATH_PLUGINS_SETTING.get(globalSettings));
        }
        if (Environment.PATH_LOGS_SETTING.exists(globalSettings)) {
            sb.put(Environment.PATH_LOGS_SETTING.getKey(), Environment.PATH_LOGS_SETTING.get(globalSettings));
        }
        if (Environment.PATH_SCRIPTS_SETTING.exists(globalSettings)) {
            sb.put(Environment.PATH_SCRIPTS_SETTING.getKey(), Environment.PATH_SCRIPTS_SETTING.get(globalSettings));
        }
        for (Setting<?> passthrough : PASS_THROUGH_SETTINGS) {
            if (passthrough.exists(tribeSettings) == false && passthrough.exists(globalSettings)) {
                sb.put(passthrough.getKey(), globalSettings.get(passthrough.getKey()));
            }
        }
        sb.put(TRIBE_NAME_SETTING.getKey(), tribeName);
        if (sb.get(NetworkModule.HTTP_ENABLED.getKey()) == null) {
            sb.put(NetworkModule.HTTP_ENABLED.getKey(), false);
        }
        sb.put(Node.NODE_CLIENT_SETTING.getKey(), true);
        return sb.build();
    }
}
