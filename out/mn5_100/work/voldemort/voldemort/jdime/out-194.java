package voldemort.server.jmx;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.apache.log4j.Logger;
import voldemort.annotations.jmx.JmxManaged;
import voldemort.cluster.Cluster;
import voldemort.common.service.AbstractService;
import voldemort.common.service.ServiceType;
import voldemort.common.service.VoldemortService;
import voldemort.server.StoreRepository;
import voldemort.server.VoldemortServer;
import voldemort.store.Store;
import voldemort.utils.ByteArray;
import voldemort.utils.JmxUtils;

/**
 * A service that manages JMX registration
 * 
 * 
 */
@JmxManaged(description = "The JMX service") public class JmxService extends AbstractService {
  private final Logger logger = Logger.getLogger(JmxService.class);

  private final MBeanServer mbeanServer;

  private final VoldemortServer server;

  private final Cluster cluster;

  private final List<VoldemortService> services;

  private final Set<ObjectName> registeredBeans;

  private final StoreRepository storeRepository;

  public JmxService(VoldemortServer server, Cluster cluster, StoreRepository storeRepository, Collection<VoldemortService> services) {
    super(ServiceType.JMX);
    this.server = server;
    this.cluster = cluster;
    this.services = new ArrayList<VoldemortService>(services);
    this.mbeanServer = ManagementFactory.getPlatformMBeanServer();
    this.registeredBeans = new HashSet<ObjectName>();
    this.storeRepository = storeRepository;
  }

  @Override protected void startInner() {
    registerBean(server, JmxUtils.createObjectName(VoldemortServer.class));
    registerBean(cluster, JmxUtils.createObjectName(Cluster.class));
    for (VoldemortService service : services) {
      registerBean(service, JmxUtils.createObjectName(service.getClass()));
    }
    for (Store<ByteArray, byte[], byte[]> store : this.storeRepository.getAllStorageEngines()) {
      if (server.getVoldemortConfig().isEnableJmxClusterName()) {
        registerBean(store, JmxUtils.createObjectName(this.cluster.getName() + "." + JmxUtils.getPackageName(store.getClass()), store.getName()));
      } else {
        registerBean(store, JmxUtils.createObjectName(JmxUtils.getPackageName(store.getClass()), store.getName()));
      }
    }
  }

  @Override protected void stopInner() {
    for (ObjectName name : registeredBeans) {
      JmxUtils.unregisterMbean(mbeanServer, name);
    }
    registeredBeans.clear();
  }

  private void registerBean(Object o, ObjectName name) {
    synchronized (registeredBeans) {
      try {
        if (mbeanServer.isRegistered(name)) {
          logger.warn("Overwriting mbean " + name);
          JmxUtils.unregisterMbean(mbeanServer, name);
        }
        JmxUtils.registerMbean(mbeanServer, JmxUtils.createModelMBean(o), name);
        this.registeredBeans.add(name);
      } catch (Exception e) {
        logger.error("Error registering bean with name \'" + name + "\':", e);
      }
    }
  }
}