package voldemort.cluster.failuredetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import voldemort.annotations.jmx.JmxGetter;
import voldemort.cluster.Node;
import voldemort.store.UnreachableStoreException;

public abstract class AbstractFailureDetector implements FailureDetector {

    protected final FailureDetectorConfig failureDetectorConfig;

    protected final ConcurrentHashMap<FailureDetectorListener, Object> listeners;

    protected final Map<Node, NodeStatus> nodeStatusMap;

    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected AbstractFailureDetector(FailureDetectorConfig failureDetectorConfig) {
        if (failureDetectorConfig == null)
            throw new IllegalArgumentException("FailureDetectorConfig must be non-null");
        this.failureDetectorConfig = failureDetectorConfig;
        listeners = new ConcurrentHashMap<FailureDetectorListener, Object>();
        nodeStatusMap = new ConcurrentHashMap<Node, NodeStatus>();
        for (Node node : failureDetectorConfig.getNodes()) {
            nodeStatusMap.put(node, createNodeStatus(node, failureDetectorConfig.getTime().getMilliseconds()));
        }
    }

    public void addFailureDetectorListener(FailureDetectorListener failureDetectorListener) {
        if (failureDetectorListener == null)
            throw new IllegalArgumentException("FailureDetectorListener must be non-null");
        listeners.put(failureDetectorListener, failureDetectorListener);
    }

    public void removeFailureDetectorListener(FailureDetectorListener failureDetectorListener) {
        if (failureDetectorListener == null)
            throw new IllegalArgumentException("FailureDetectorListener must be non-null");
        listeners.remove(failureDetectorListener);
    }

    public FailureDetectorConfig getConfig() {
        return failureDetectorConfig;
    }

    @JmxGetter(name = "availableNodes", description = "The available nodes")
    public String getAvailableNodes() {
        List<String> list = new ArrayList<String>();
        for (Node node : getConfig().getNodes()) if (isAvailable(node))
            list.add(String.valueOf(node.getId()));
        return StringUtils.join(list, ",");
    }

    @JmxGetter(name = "unavailableNodes", description = "The unavailable nodes")
    public String getUnavailableNodes() {
        List<String> list = new ArrayList<String>();
        for (Node node : getConfig().getNodes()) if (!isAvailable(node))
            list.add(String.valueOf(node.getId()));
        return StringUtils.join(list, ",");
    }

    @JmxGetter(name = "availableNodeCount", description = "The number of available nodes")
    public int getAvailableNodeCount() {
        int available = 0;
        for (Node node : getConfig().getNodes()) if (isAvailable(node))
            available++;
        return available;
    }

    @JmxGetter(name = "nodeCount", description = "The number of total nodes")
    public int getNodeCount() {
        return getConfig().getNodes().size();
    }

    public void waitForAvailability(Node node) throws InterruptedException {
        checkNodeArg(node);
        NodeStatus nodeStatus = getNodeStatus(node);
        synchronized (nodeStatus) {
            if (!isAvailable(node))
                nodeStatus.wait();
        }
    }

    public long getLastChecked(Node node) {
        checkNodeArg(node);
        NodeStatus nodeStatus = getNodeStatus(node);
        synchronized (nodeStatus) {
            return nodeStatus.getLastChecked();
        }
    }

    public void destroy() {
    }

    protected void setAvailable(Node node) {
        NodeStatus nodeStatus = getNodeStatus(node);
        if (logger.isTraceEnabled())
            logger.trace(node + " set as available");
        boolean previouslyAvailable = setAvailable(nodeStatus, true);
        if (!previouslyAvailable) {
            if (logger.isInfoEnabled())
                logger.info(node + " now available");
            synchronized (nodeStatus) {
                nodeStatus.notifyAll();
            }
            for (FailureDetectorListener fdl : listeners.keySet()) {
                try {
                    fdl.nodeAvailable(node);
                } catch (Exception e) {
                    if (logger.isEnabledFor(Level.WARN))
                        logger.warn(e, e);
                }
            }
        }
    }

    protected void setUnavailable(Node node, UnreachableStoreException e) {
        NodeStatus nodeStatus = getNodeStatus(node);
        if (logger.isEnabledFor(Level.WARN)) {
            if (e != null)
                logger.warn(node + " set as unavailable", e);
            else
                logger.warn(node + " set as unavailable");
        }
        boolean previouslyAvailable = setAvailable(nodeStatus, false);
        if (previouslyAvailable) {
            if (logger.isInfoEnabled())
                logger.info(node + " now unavailable");
            for (FailureDetectorListener fdl : listeners.keySet()) {
                try {
                    fdl.nodeUnavailable(node);
                } catch (Exception ex) {
                    if (logger.isEnabledFor(Level.WARN))
                        logger.warn(ex, ex);
                }
            }
        }
    }

    protected NodeStatus getNodeStatus(Node node) {
        NodeStatus nodeStatus = nodeStatusMap.get(node);
        if (nodeStatus == null) {
            logger.warn("creating new node status for node " + node + " for failure detector.");
            nodeStatusMap.put(node, createNodeStatus(node, failureDetectorConfig.getTime().getMilliseconds()));
        }
        return nodeStatus;
    }

    private boolean setAvailable(NodeStatus nodeStatus, boolean isAvailable) {
        synchronized (nodeStatus) {
            boolean previous = nodeStatus.isAvailable();
            nodeStatus.setAvailable(isAvailable);
            nodeStatus.setLastChecked(getConfig().getTime().getMilliseconds());
            return previous;
        }
    }

    protected void checkNodeArg(Node node) {
        if (node == null)
            throw new IllegalArgumentException("node must be non-null");
    }

    protected void checkArgs(Node node, long requestTime) {
        checkNodeArg(node);
        if (requestTime < 0)
            throw new IllegalArgumentException("requestTime - " + requestTime + " - less than 0");
    }

    private NodeStatus createNodeStatus(Node node, long currTime) {
        NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setLastChecked(currTime);
        nodeStatus.setStartMillis(currTime);
        nodeStatus.setAvailable(true);
        return nodeStatus;
    }
}
