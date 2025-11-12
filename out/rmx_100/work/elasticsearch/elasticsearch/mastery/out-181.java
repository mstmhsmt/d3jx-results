package org.elasticsearch.plugin.discovery.gce;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.ClassInfo;
import org.elasticsearch.SpecialPermission;
import org.elasticsearch.cloud.gce.GceModule;
import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.discovery.DiscoveryModule;
import org.elasticsearch.discovery.gce.GceUnicastHostsProvider;
import org.elasticsearch.discovery.zen.ZenDiscovery;
import org.elasticsearch.plugins.Plugin;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.elasticsearch.cloud.gce.GceInstancesService;
import java.util.ArrayList;

public class GceDiscoveryPlugin extends Plugin {

    public static final String GCE = "gce";

    static {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new SpecialPermission());
        }
        AccessController.doPrivileged(new PrivilegedAction<Void>() {

            @Override
            public Void run() {
                ClassInfo.of(HttpHeaders.class, true);
                return null;
            }
        });
    }

    @Override
    public Collection<Module> createGuiceModules() {
        return Collections.singletonList(new GceModule(settings));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection<Class<? extends LifecycleComponent>> getGuiceServiceClasses() {
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

    @Override
    public List<Setting<?>> getSettings() {
        return Arrays.asList(GceInstancesService.PROJECT_SETTING, GceInstancesService.ZONE_SETTING, GceUnicastHostsProvider.TAGS_SETTING, GceInstancesService.REFRESH_SETTING, GceInstancesService.RETRY_SETTING, GceInstancesService.MAX_WAIT_SETTING);
    }

    public GceDiscoveryPlugin(Settings settings) {
        this.settings = settings;
        logger.trace("starting gce discovery plugin...");
    }

    final private Settings settings;

    final protected ESLogger logger = Loggers.getLogger(GceDiscoveryPlugin.class);
}
