package voldemort.store.metadata;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import voldemort.ServerTestUtils;
import voldemort.client.rebalance.RebalancePartitionsInfo;
import voldemort.server.rebalance.RebalancerState;
import voldemort.store.metadata.MetadataStore.VoldemortState;
import voldemort.utils.ByteArray;
import voldemort.utils.ByteUtils;
import voldemort.versioning.ObsoleteVersionException;
import voldemort.versioning.VectorClock;
import voldemort.versioning.Versioned;
import voldemort.xml.ClusterMapper;
import voldemort.xml.StoreDefinitionsMapper;

public class MetadataStoreTest extends TestCase {
  private static int TEST_RUNS = 100;

  private MetadataStore metadataStore;

  private List<String> TEST_KEYS = Arrays.asList(MetadataStore.CLUSTER_KEY, MetadataStore.STORES_KEY, MetadataStore.REBALANCING_STEAL_INFO);

  @Override public void setUp() throws Exception {
    super.setUp();
    metadataStore = ServerTestUtils.createMetadataStore(ServerTestUtils.getLocalCluster(1), ServerTestUtils.getStoreDefs(1));
  }

  public ByteArray getValidKey() {
    int i = (int) (Math.random() * TEST_KEYS.size());
    String key = TEST_KEYS.get(i);
    return new ByteArray(ByteUtils.getBytes(key, "UTF-8"));
  }

  public byte[] getValidValue(ByteArray key) {
    String keyString = ByteUtils.getString(key.get(), "UTF-8");
    if (MetadataStore.CLUSTER_KEY.equals(keyString)) {
      return ByteUtils.getBytes(new ClusterMapper().writeCluster(ServerTestUtils.getLocalCluster(1)), "UTF-8");
    } else {
      if (MetadataStore.STORES_KEY.equals(keyString)) {
        return ByteUtils.getBytes(new StoreDefinitionsMapper().writeStoreList(ServerTestUtils.getStoreDefs(1)), "UTF-8");
      } else {
        if (MetadataStore.SERVER_STATE_KEY.equals(keyString)) {
          int i = (int) (Math.random() * VoldemortState.values().length);
          return ByteUtils.getBytes(VoldemortState.values()[i].toString(), "UTF-8");
        } else {
          if (MetadataStore.REBALANCING_STEAL_INFO.equals(keyString)) {
            int size = (int) (Math.random() * 10);
            List<Integer> partition = new ArrayList<Integer>();
            for (int i = 0; i < size; i++) {
              partition.add((int) Math.random() * 10);
            }
            return ByteUtils.getBytes(new RebalancerState(Arrays.asList(new RebalancePartitionsInfo(0, (int) Math.random() * 5, partition, new ArrayList<Integer>(0), Arrays.asList("testStoreName"), (int) Math.random() * 3))).toJsonString(), "UTF-8");
          }
        }
      }
    }
    throw new RuntimeException("Unhandled key:" + keyString + " passed");
  }

  public void testSimpleGetAndPut() {
    for (int i = 0; i <= TEST_RUNS; i++) {
      ByteArray key = getValidKey();
      VectorClock clock = (VectorClock) metadataStore.get(key, null).get(0).getVersion();
      Versioned<byte[]> value = new Versioned<byte[]>(getValidValue(key), clock.incremented(0, 1));
      metadataStore.put(key, value, null);
      checkValues(value, metadataStore.get(key, null), key);
    }
  }

  public void testRepeatedPuts() {
    for (int i = 0; i <= TEST_RUNS; i++) {
      for (int j = 0; j <= 5; j++) {
        ByteArray key = getValidKey();
        VectorClock clock = (VectorClock) metadataStore.get(key, null).get(0).getVersion();
        Versioned<byte[]> value = new Versioned<byte[]>(getValidValue(key), clock.incremented(0, 1));
        metadataStore.put(key, value, null);
        checkValues(value, metadataStore.get(key, null), key);
      }
    }
  }

  public void testObsoletePut() {
    for (int i = 0; i <= TEST_RUNS; i++) {
      ByteArray key = getValidKey();
      VectorClock clock = (VectorClock) metadataStore.get(key, null).get(0).getVersion();
      Versioned<byte[]> value = new Versioned<byte[]>(getValidValue(key), clock.incremented(0, 1));
      try {
        metadataStore.put(key, value, null);
        assertTrue(true);
        metadataStore.put(key, value, null);
        fail();
      } catch (ObsoleteVersionException e) {
      }
    }
  }

  public void testSynchronousPut() {
    for (int i = 0; i <= TEST_RUNS; i++) {
      ByteArray key = getValidKey();
      VectorClock clock = (VectorClock) metadataStore.get(key, null).get(0).getVersion();
      Versioned<byte[]> value1 = new Versioned<byte[]>(getValidValue(key), clock.incremented(1, 1));
      Versioned<byte[]> value2 = new Versioned<byte[]>(getValidValue(key), clock.incremented(2, 1));
      metadataStore.put(key, value1, null);
      metadataStore.put(key, value2, null);
      assertEquals("Only one metadata value should return", 1, metadataStore.get(key, null).size());
      checkValues(value2, metadataStore.get(key, null), key);
    }
  }

  public void testCleanAllStates() {
    incrementVersionAndPut(metadataStore, MetadataStore.CLUSTER_STATE_KEY, MetadataStore.VoldemortState.REBALANCING_CLUSTER);
    incrementVersionAndPut(metadataStore, MetadataStore.SERVER_STATE_KEY, MetadataStore.VoldemortState.REBALANCING_MASTER_SERVER);
    assertEquals("Values should match.", metadataStore.getClusterState(), VoldemortState.REBALANCING_CLUSTER);
    assertEquals("Values should match.", metadataStore.getServerState(), VoldemortState.REBALANCING_MASTER_SERVER);
    metadataStore.cleanAllRebalancingState();
    assertEquals("Values should match.", metadataStore.getClusterState(), VoldemortState.NORMAL_CLUSTER);
    assertEquals("Values should match.", metadataStore.getServerState(), VoldemortState.NORMAL_SERVER);
  }

  private void checkValues(Versioned<byte[]> value, List<Versioned<byte[]>> list, ByteArray key) {
    assertEquals("should return exactly one value ", 1, list.size());
    assertEquals("should return the last saved version", value.getVersion(), list.get(0).getVersion());
    assertEquals("should return the last saved value (key:" + ByteUtils.getString(key.get(), "UTF-8") + ")", new String(value.getValue()), new String(list.get(0).getValue()));
  }

  /**
     * helper function to auto update version and put()
     * 
     * @param key
     * @param value
     */
  private void incrementVersionAndPut(MetadataStore metadataStore, String keyString, Object value) {
    ByteArray key = new ByteArray(ByteUtils.getBytes(keyString, "UTF-8"));
    VectorClock current = (VectorClock) metadataStore.getVersions(key).get(0);
    metadataStore.put(keyString, new Versioned<Object>(value, current.incremented(0, System.currentTimeMillis())));
  }
}