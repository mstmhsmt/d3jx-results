package voldemort.routing;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import junit.framework.TestCase;
import voldemort.cluster.Node;
import voldemort.utils.ConstantHashFunction;
import voldemort.utils.FnvHashFunction;
import voldemort.utils.HashFunction;
import cern.jet.random.ChiSquare;
import cern.jet.random.engine.MersenneTwister;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;

public class ConsistentRoutingStrategyTest extends TestCase {

    private final byte[] key = new byte[0];

    private List<Node> getTestNodes() {
        return ImmutableList.of(node(0, 2, 7, 14), node(1, 1, 10, 13), node(2, 3, 5, 17), node(3, 0, 11, 16), node(4, 6, 9, 15), node(5, 4, 8, 12));
    }

    public ConsistentRoutingStrategy getRouter(HashFunction hash, int replicationFactor) {
        return new ConsistentRoutingStrategy(hash, getTestNodes(), replicationFactor);
    }

    public ConsistentRoutingStrategy getRouter(int hashValue, int replicationFactor) {
        List<Node> nodes = getTestNodes();
        return new ConsistentRoutingStrategy(new ConstantHashFunction(hashValue), nodes, replicationFactor);
    }

    public void test1xReplication() {
        assertNodeOrder(getRouter(0, 1).routeRequest(key), 3);
        assertNodeOrder(getRouter(14, 1).routeRequest(key), 0);
        assertNodeOrder(getRouter(4, 1).routeRequest(key), 5);
    }

    public void test3xReplcation() {
        assertNodeOrder(getRouter(0, 3).routeRequest(key), 3, 1, 0);
        assertNodeOrder(getRouter(14, 3).routeRequest(key), 0, 4, 3);
        assertNodeOrder(getRouter(4, 3).routeRequest(key), 5, 2, 4);
        assertNodeOrder(getRouter(16, 3).routeRequest(key), 3, 2, 1);
    }

    public void testGetNodes() {
        getRouter(0, 3).getNodes().containsAll(getTestNodes());
    }

    public void testTagAssignment() {
        List<Node> nodes = getTestNodes();
        ConsistentRoutingStrategy router = getRouter(new FnvHashFunction(), 3);
        for (Node n : nodes) for (Integer tag : n.getPartitionIds()) assertEquals(router.getNodeByPartition(tag), n);
        for (int i = 0; i < nodes.size(); i++) assertEquals("Unexpected tag assignment for tag " + i + ": ", new HashSet<Integer>(nodes.get(i).getPartitionIds()), router.getPartitionsByNode(nodes.get(i)));
    }

    public void testLoadBalancing() {
        testLoadBalancing(2, 10, 1000, 2);
        testLoadBalancing(6, 100, 1000, 3);
        testLoadBalancing(10, 200, 10000, 3);
    }

    public void testLoadBalancing(int numNodes, int tagsPerNode, int numRequests, int replicationFactor) {
        List<Integer> tags = new ArrayList<Integer>();
        List<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < numNodes * tagsPerNode; i++) tags.add(i);
        for (int i = 0; i < numNodes; i++) nodes.add(new Node(i, "host", 8080, 6666, tags.subList(tagsPerNode * i, tagsPerNode * (i + 1))));
        Random random = new Random(2158745224L);
        Collections.shuffle(nodes, random);
        ConsistentRoutingStrategy router = new ConsistentRoutingStrategy(new FnvHashFunction(), nodes, replicationFactor);
        for (Node n : nodes) assertEquals(tagsPerNode, router.getPartitionsByNode(n).size());
        Multiset<Integer> masters = HashMultiset.create();
        Multiset<Integer> counts = HashMultiset.create();
        byte[] key = new byte[16];
        for (int i = 0; i < numRequests; i++) {
            random.nextBytes(key);
            List<Node> routed = router.routeRequest(key);
            assertEquals(replicationFactor, routed.size());
            masters.add(routed.get(0).getId());
            for (Node n : routed) counts.add(n.getId());
        }
        System.out.println("numNodes = " + numNodes + ", tagsPerNode = " + tagsPerNode + ", numRequests = " + numRequests);
        System.out.println("master node distribution:");
        assertWellBalanced(numNodes, masters);
        System.out.println();
        System.out.println("storage node distribution:");
        assertWellBalanced(numNodes, counts);
        System.out.println();
    }

    private void assertWellBalanced(int numNodes, Multiset<Integer> ids) {
        double expected = ids.size() / (double) numNodes;
        double chiSq = 0.0;
        int df = numNodes - 1;
        NumberFormat prct = NumberFormat.getPercentInstance();
        prct.setMaximumFractionDigits(4);
        NumberFormat num = NumberFormat.getInstance();
        num.setMaximumFractionDigits(4);
        num.setMinimumFractionDigits(4);
        System.out.println("node\treqs\tX^2\tskew");
        for (Integer id : ids.elementSet()) {
            System.out.println(id + "\t" + ids.count(id) + "\t" + num.format(chiSq(ids.count(id), expected)) + "\t" + prct.format((ids.count(id) - expected) / expected));
            chiSq += chiSq(ids.count(id), expected);
        }
        System.out.println("X^2 = " + chiSq);
        ChiSquare dist = new ChiSquare(df, new MersenneTwister());
        double pValue = 1.0 - dist.cdf(chiSq);
        System.out.println("p-value = " + pValue);
        assertTrue("Non-uniform load distribution detected.", pValue >= 0.05);
    }

    private double chiSq(double observed, double expected) {
        return (observed - expected) * (observed - expected) / expected;
    }

    private void assertNodeOrder(List<Node> found, int... expected) {
        assertEquals("Router produced unexpected number of nodes.", expected.length, found.size());
        for (int i = 0; i < found.size(); i++) assertEquals(expected[i], found.get(i).getId());
    }

    private Node node(int id, int... tags) {
        List<Integer> list = new ArrayList<Integer>(tags.length);
        for (int tag : tags) list.add(tag);
        return new Node(id, "localhost", 8080, 6666, 6667, list);
    }
}
