package voldemort.store.rebalancing;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import voldemort.VoldemortException;
import voldemort.client.protocol.RequestFormatType;
import voldemort.cluster.Node;
import voldemort.cluster.failuredetector.FailureDetector;
import voldemort.server.RequestRoutingType;
import voldemort.server.StoreRepository;
import voldemort.store.DelegatingStore;
import voldemort.store.Store;
import voldemort.store.StoreUtils;
import voldemort.store.UnreachableStoreException;
import voldemort.store.metadata.MetadataStore;
import voldemort.store.metadata.MetadataStore.VoldemortState;
import voldemort.utils.ByteArray;
import voldemort.utils.Time;
import voldemort.versioning.ObsoleteVersionException;
import voldemort.versioning.Version;
import voldemort.versioning.Versioned;
import com.google.common.collect.Lists;
import com.google.common.collect.Iterables;
import com.google.common.collect.HashMultimap;
import java.util.HashMap;
import voldemort.server.rebalance.RebalancerState;
import voldemort.store.socket.SocketStoreFactory;
import voldemort.client.rebalance.RebalancePartitionsInfo;
import com.google.common.collect.Multimap;
import com.google.common.collect.Maps;

public class RedirectingStore extends DelegatingStore<ByteArray, byte[]> {

    private final static Logger logger = Logger.getLogger(RedirectingStore.class);

    private final MetadataStore metadata;

    private final StoreRepository storeRepository;

    private FailureDetector failureDetector;

    public RedirectingStore(Store<ByteArray, byte[]> innerStore, MetadataStore metadata, StoreRepository storeRepository, FailureDetector detector, SocketStoreFactory storeFactory) {
        super(innerStore);
        this.metadata = metadata;
        this.storeRepository = storeRepository;
        this.storeFactory = storeFactory;
        this.failureDetector = detector;
    }

    @Override
    public void put(ByteArray key, Versioned<byte[]> value) throws VoldemortException {
        RebalancePartitionsInfo stealInfo = redirectingKey(key);
        if (stealInfo != null)
            proxyGetAndLocalPut(key, stealInfo.getDonorId());
        getInnerStore().put(key, value);
    }

    private RebalancePartitionsInfo redirectingKey(ByteArray key) {
        if (VoldemortState.REBALANCING_MASTER_SERVER.equals(metadata.getServerState()) && !getName().equals(MetadataStore.METADATA_STORE_NAME)) {
            List<Integer> partitionIds = metadata.getRoutingStrategy(getName()).getPartitionList(key.get());
            return getRebalancePartitionsInfo(partitionIds);
        }
        return null;
    }

    @Override
    public List<Versioned<byte[]>> get(ByteArray key) throws VoldemortException {
        RebalancePartitionsInfo stealInfo = redirectingKey(key);
        if (stealInfo != null)
            proxyGetAndLocalPut(key, stealInfo.getDonorId());
        return getInnerStore().get(key);
    }

    @Override
    public List<Version> getVersions(ByteArray key) {
        RebalancePartitionsInfo stealInfo = redirectingKey(key);
        if (stealInfo != null)
            proxyGetAndLocalPut(key, stealInfo.getDonorId());
        return getInnerStore().getVersions(key);
    }

    @Override
    public Map<ByteArray, List<Versioned<byte[]>>> getAll(Iterable<ByteArray> keys) throws VoldemortException {
        int maxLength = Iterables.size(keys);
        List<ByteArray> redirectingKeys = Lists.newArrayListWithExpectedSize(maxLength);
        List<RebalancePartitionsInfo> rebalancePartitionsInfos = Lists.newArrayListWithExpectedSize(maxLength);
        for (ByteArray key : keys) {
            RebalancePartitionsInfo info;
            info = redirectingKey(key);
            if (info != null) {
                redirectingKeys.add(key);
                rebalancePartitionsInfos.add(info);
            }
        }
        if (!redirectingKeys.isEmpty())
            proxyGetAllAndLocalPut(redirectingKeys, rebalancePartitionsInfos);
        return getInnerStore().getAll(keys);
    }

    @Override
    public boolean delete(ByteArray key, Version version) throws VoldemortException {
        StoreUtils.assertValidKey(key);
        return getInnerStore().delete(key, version);
    }

    private List<Versioned<byte[]>> proxyGet(ByteArray key, int donorNodeId) {
        Node donorNode = metadata.getCluster().getNodeById(donorNodeId);
        checkNodeAvailable(donorNode);
        long startNs = System.nanoTime();
        try {
            Store<ByteArray, byte[]> redirectingStore = getRedirectingSocketStore(getName(), donorNodeId);
            List<Versioned<byte[]>> values = redirectingStore.get(key);
            recordSuccess(donorNode, startNs);
            return values;
        } catch (UnreachableStoreException e) {
            recordException(donorNode, startNs, e);
            throw new ProxyUnreachableException("Failed to reach proxy node " + donorNode, e);
        }
    }

    private void checkNodeAvailable(Node donorNode) {
        if (!failureDetector.isAvailable(donorNode))
            throw new ProxyUnreachableException("Failed to reach proxy node " + donorNode + " is marked down by failure detector.");
    }

    private Map<ByteArray, List<Versioned<byte[]>>> proxyGetAll(List<ByteArray> keys, List<RebalancePartitionsInfo> stealInfoList) throws VoldemortException {
        Multimap<Integer, ByteArray> scatterMap = HashMultimap.create(stealInfoList.size(), keys.size());
        int numKeys = 0;
        for (ByteArray key : keys) {
            numKeys++;
            for (RebalancePartitionsInfo stealInfo : stealInfoList) {
                byte[] keyBytes = key.get();
                for (int p : metadata.getRoutingStrategy(getName()).getPartitionList(keyBytes)) {
                    if (stealInfo.getPartitionList().contains(p))
                        scatterMap.put(stealInfo.getDonorId(), key);
                }
            }
        }
        Map<ByteArray, List<Versioned<byte[]>>> gatherMap = Maps.newHashMapWithExpectedSize(numKeys);
        for (int donorNodeId : scatterMap.keySet()) {
            Node donorNode = metadata.getCluster().getNodeById(donorNodeId);
            checkNodeAvailable(donorNode);
            long startNs = System.nanoTime();
            try {
                Map<ByteArray, List<Versioned<byte[]>>> resultsForNode = getRedirectingSocketStore(getName(), donorNodeId).getAll(scatterMap.get(donorNodeId));
                recordSuccess(donorNode, startNs);
                for (Map.Entry<ByteArray, List<Versioned<byte[]>>> entry : resultsForNode.entrySet()) {
                    gatherMap.put(entry.getKey(), entry.getValue());
                }
            } catch (UnreachableStoreException e) {
                recordException(donorNode, startNs, e);
                throw new ProxyUnreachableException("Failed to reach proxy node " + donorNode, e);
            }
        }
        return gatherMap;
    }

    private void proxyGetAndLocalPut(ByteArray key, int donorId) throws VoldemortException {
        List<Versioned<byte[]>> proxyValues = proxyGet(key, donorId);
        for (Versioned<byte[]> proxyValue : proxyValues) {
            try {
                getInnerStore().put(key, proxyValue);
            } catch (ObsoleteVersionException e) {
            }
        }
    }

    private void proxyGetAllAndLocalPut(List<ByteArray> keys, List<RebalancePartitionsInfo> stealInfoList) throws VoldemortException {
        Map<ByteArray, List<Versioned<byte[]>>> proxyKeyValues = proxyGetAll(keys, stealInfoList);
        for (Map.Entry<ByteArray, List<Versioned<byte[]>>> keyValuePair : proxyKeyValues.entrySet()) {
            for (Versioned<byte[]> proxyValue : keyValuePair.getValue()) {
                try {
                    getInnerStore().put(keyValuePair.getKey(), proxyValue);
                } catch (ObsoleteVersionException e) {
                }
            }
        }
    }

    private Store<ByteArray, byte[]> getRedirectingSocketStore(String storeName, int donorNodeId) {
        if (!storeRepository.hasRedirectingSocketStore(storeName, donorNodeId)) {
            synchronized (storeRepository) {
                if (!storeRepository.hasRedirectingSocketStore(storeName, donorNodeId)) {
                    Node donorNode = getNodeIfPresent(donorNodeId);
                    logger.info("Creating redirectingSocketStore for donorNode " + donorNode + " store " + storeName);
                    storeRepository.addRedirectingSocketStore(donorNode.getId(), storeFactory.create(storeName, donorNode.getHost(), donorNode.getSocketPort(), RequestFormatType.PROTOCOL_BUFFERS, RequestRoutingType.IGNORE_CHECKS));
                }
            }
        }
        return storeRepository.getRedirectingSocketStore(storeName, donorNodeId);
    }

    private Node getNodeIfPresent(int donorId) {
        try {
            return metadata.getCluster().getNodeById(donorId);
        } catch (Exception e) {
            throw new VoldemortException("Failed to get donorNode " + donorId + " from current cluster " + metadata.getCluster() + " at node " + metadata.getNodeId(), e);
        }
    }

    private void recordException(Node node, long startNs, UnreachableStoreException e) {
        failureDetector.recordException(node, (System.nanoTime() - startNs) / Time.NS_PER_MS, e);
    }

    private void recordSuccess(Node node, long startNs) {
        failureDetector.recordSuccess(node, (System.nanoTime() - startNs) / Time.NS_PER_MS);
    }

    private final SocketStoreFactory storeFactory;

    private RebalancePartitionsInfo getRebalancePartitionsInfo(List<Integer> partitionIds) {
        RebalancerState rebalancerState = metadata.getRebalancerState();
        return rebalancerState.find(getName(), partitionIds);
    }
}
