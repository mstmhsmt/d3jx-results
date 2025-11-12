package org.elasticsearch.plugin.discovery.gce;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.ClassInfo;
import org.elasticsearch.SpecialPermission;
import org.elasticsearch.cloud.gce.GceInstancesService;
import org.elasticsearch.cloud.gce.GceModule;
import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.discovery.DiscoveryModule;
import org.elasticsearch.discovery.gce.GceUnicastHostsProvider;
import org.elasticsearch.discovery.zen.ZenDiscovery;
import org.elasticsearch.plugins.Plugin;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.elasticsearch.common.settings.Setting;
import java.util.Arrays;
import java.util.List;

public class GceDiscoveryPlugin extends Plugin {
  public static final String GCE = "gce";

  static {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
      sm.checkPermission(new SpecialPermission());
    }
    AccessController.doPrivileged(new PrivilegedAction<Void>() {
      @Override public Void run() {
        ClassInfo.of(HttpHeaders.class, true);
        return null;
      }
    });
  }

  @Override public Collection<Module> nodeModules() {
    return Collections.singletonList(new GceModule(settings));
  }

  @Override @SuppressWarnings(value = { "rawtypes" }) public Collection<Class<? extends LifecycleComponent>> nodeServices() {
    logger.debug("Register gce compute and metadata services");
    Collection<Class<? extends LifecycleComponent>> services = new ArrayList<>();
    services.add(GceModule.getComputeServiceImpl());
    services.add(GceModule.getMetadataServiceImpl());
    return services;
  }

  public void onModule(DiscoveryModule discoveryModule) {
    logger.debug("Register gce discovery type and gce unicast provider");
    discoveryModule.addDiscoveryType(GCE, ZenDiscovery.class);
    discoveryModule.addUnicastHostProvider(GCE, GceUnicastHostsProvider.class);
  }


<<<<<<< commits-hd_100/elastic/elasticsearch/a9e93a0da4c982e8f40bdddbe25e8f05d8c1af26-2b92c4fd8c127335a8c890519730564d852bc40f/A.java
  public void onModule(SettingsModule settingsModule) {
    logger.debug("registering GCE Settings");
    settingsModule.registerSetting(GceInstancesService.PROJECT_SETTING);
    settingsModule.registerSetting(GceInstancesService.ZONE_SETTING);
    settingsModule.registerSetting(GceUnicastHostsProvider.TAGS_SETTING);
    settingsModule.registerSetting(GceInstancesService.REFRESH_SETTING);
    settingsModule.registerSetting(GceInstancesService.RETRY_SETTING);
    settingsModule.registerSetting(GceInstancesService.MAX_WAIT_SETTING);
  }
=======
>>>>>>> Unknown file: This is a bug in JDime.


  @Override public List<Setting<?>> getSettings() {
    return Arrays.asList(GceComputeService.PROJECT_SETTING, GceComputeService.ZONE_SETTING, GceUnicastHostsProvider.TAGS_SETTING, GceComputeService.REFRESH_SETTING, GceComputeService.RETRY_SETTING, GceComputeService.MAX_WAIT_SETTING);
  }
}