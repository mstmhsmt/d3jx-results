package org.springframework.boot.actuate.autoconfigure;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.crsh.auth.AuthenticationPlugin;
import org.crsh.plugin.CRaSHPlugin;
import org.crsh.plugin.PluginContext;
import org.crsh.plugin.PluginDiscovery;
import org.crsh.plugin.PluginLifeCycle;
import org.crsh.plugin.PropertyDescriptor;
import org.crsh.plugin.ServiceLoaderDiscovery;
import org.crsh.vfs.FS;
import org.crsh.vfs.spi.AbstractFSDriver;
import org.crsh.vfs.spi.FSDriver;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ShellProperties.CrshShellAuthenticationProperties;
import org.springframework.boot.actuate.autoconfigure.ShellProperties.CrshShellProperties;
import org.springframework.boot.actuate.autoconfigure.ShellProperties.JaasAuthenticationProperties;
import org.springframework.boot.actuate.autoconfigure.ShellProperties.KeyAuthenticationProperties;
import org.springframework.boot.actuate.autoconfigure.ShellProperties.SimpleAuthenticationProperties;
import org.springframework.boot.actuate.autoconfigure.ShellProperties.SpringAuthenticationProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.AntPathMatcher;

@Configuration
@ConditionalOnClass({ PluginLifeCycle.class })
@EnableConfigurationProperties({ ShellProperties.class })
@AutoConfigureAfter({ SecurityAutoConfiguration.class, ManagementSecurityAutoConfiguration.class })
public class CrshAutoConfiguration {

    @Autowired
    private ShellProperties properties;

    @Bean
    @ConditionalOnExpression("'${shell.auth:simple}' == 'jaas'")
    @ConditionalOnMissingBean({ CrshShellAuthenticationProperties.class })
    public CrshShellAuthenticationProperties jaasAuthenticationProperties() {
        return new JaasAuthenticationProperties();
    }

    @Bean
    @ConditionalOnMissingBean({ CrshShellAuthenticationProperties.class })
    @ConditionalOnExpression("'${shell.auth:simple}' == 'key'")
    public CrshShellAuthenticationProperties keyAuthenticationProperties() {
        return new KeyAuthenticationProperties();
    }

    @Bean
    @ConditionalOnMissingBean({ CrshShellAuthenticationProperties.class })
    @ConditionalOnExpression("'${shell.auth:simple}' == 'simple'")
    public CrshShellAuthenticationProperties simpleAuthenticationProperties() {
        return new SimpleAuthenticationProperties();
    }

    @Bean
    @ConditionalOnMissingBean({ PluginLifeCycle.class })
    public PluginLifeCycle shellBootstrap() {
        CrshBootstrapBean bootstrapBean = new CrshBootstrapBean();
        bootstrapBean.setConfig(this.properties.asCrshShellConfig());
        return bootstrapBean;
    }

    @Configuration
    @ConditionalOnBean({ AuthenticationManager.class })
    @AutoConfigureAfter(CrshAutoConfiguration.class)
    public static class AuthenticationManagerAdapterAutoConfiguration {

        @Autowired(required = false)
        private ManagementServerProperties management;

        @Bean
        public CRaSHPlugin<?> shellAuthenticationManager() {
            return new AuthenticationManagerAdapter();
        }

        @Bean
        @ConditionalOnMissingBean({ CrshShellAuthenticationProperties.class })
        @ConditionalOnExpression("'${shell.auth:spring}' == 'spring'")
        public CrshShellAuthenticationProperties springAuthenticationProperties() {
            SpringAuthenticationProperties authenticationProperties = new SpringAuthenticationProperties();
            if (this.management != null) {
                authenticationProperties.setRoles(new String[] { this.management.getSecurity().getRole() });
            }
            return authenticationProperties;
        }
    }

    public static class CrshBootstrapBean extends PluginLifeCycle {

        @Autowired
        private ListableBeanFactory beanFactory;

        @Autowired
        private Environment environment;

        @Autowired
        private ShellProperties properties;

        @Autowired
        private ResourcePatternResolver resourceLoader;

        @PreDestroy
        public void destroy() {
            stop();
        }

        @PostConstruct
        public void init() {
            FS commandFileSystem = createFileSystem(this.properties.getCommandPathPatterns(), this.properties.getDisabledCommands());
            FS configurationFileSystem = createFileSystem(this.properties.getConfigPathPatterns(), new String[0]);
            PluginDiscovery discovery = new BeanFactoryFilteringPluginDiscovery(this.resourceLoader.getClassLoader(), this.beanFactory, this.properties.getDisabledPlugins());
            PluginContext context = new PluginContext(discovery, createPluginContextAttributes(), commandFileSystem, configurationFileSystem, this.resourceLoader.getClassLoader());
            context.refresh();
            start(context);
        }

        protected FS createFileSystem(String[] pathPatterns, String[] filterPatterns) {
            Assert.notNull(pathPatterns, "PathPatterns must not be null");
            Assert.notNull(filterPatterns, "FilterPatterns must not be null");
            FS fileSystem = new FS();
            for (String pathPattern : pathPatterns) {
                fileSystem.mount(new SimpleFileSystemDriver(new DirectoryHandle(pathPattern, this.resourceLoader, filterPatterns)));
            }
            return fileSystem;
        }

        protected Map<String, Object> createPluginContextAttributes() {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String bootVersion = CrshAutoConfiguration.class.getPackage().getImplementationVersion();
            if (bootVersion != null) {
                attributes.put("spring.boot.version", bootVersion);
            }
            attributes.put("spring.version", SpringVersion.getVersion());
            if (this.beanFactory != null) {
                attributes.put("spring.beanfactory", this.beanFactory);
            }
            if (this.environment != null) {
                attributes.put("spring.environment", this.environment);
            }
            return attributes;
        }
    }

    @SuppressWarnings("rawtypes")
    private static class AuthenticationManagerAdapter extends CRaSHPlugin<AuthenticationPlugin> implements AuthenticationPlugin<String> {

        private static final PropertyDescriptor<String> ROLES = PropertyDescriptor.create("auth.spring.roles", "ADMIN", "Comma separated list of roles required to access the shell");

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired(required = false)
        private AccessDecisionManager accessDecisionManager;

        private String[] roles = new String[] { "ADMIN" };

        @Override
        public boolean authenticate(String username, String password) throws Exception {
            Authentication token = new UsernamePasswordAuthenticationToken(username, password);
            try {
                token = this.authenticationManager.authenticate(token);
            } catch (AuthenticationException ex) {
                return false;
            }
            if (this.accessDecisionManager != null && token.isAuthenticated() && this.roles != null) {
                try {
                    this.accessDecisionManager.decide(token, this, SecurityConfig.createList(this.roles));
                } catch (AccessDeniedException ex) {
                    return false;
                }
            }
            return token.isAuthenticated();
        }

        @Override
        public Class<String> getCredentialType() {
            return String.class;
        }

        @Override
        public AuthenticationPlugin<String> getImplementation() {
            return this;
        }

        @Override
        public String getName() {
            return "spring";
        }

        @Override
        public void init() {
            String rolesPropertyValue = getContext().getProperty(ROLES);
            if (rolesPropertyValue != null) {
                this.roles = StringUtils.commaDelimitedListToStringArray(rolesPropertyValue);
            }
        }

        @Override
        protected Iterable<PropertyDescriptor<?>> createConfigurationCapabilities() {
            return Arrays.<PropertyDescriptor<?>>asList(ROLES);
        }
    }

    private static class BeanFactoryFilteringPluginDiscovery extends ServiceLoaderDiscovery {

        private final ListableBeanFactory beanFactory;

        private final String[] disabledPlugins;

        public BeanFactoryFilteringPluginDiscovery(ClassLoader classLoader, ListableBeanFactory beanFactory, String[] disabledPlugins) throws NullPointerException {
            super(classLoader);
            this.beanFactory = beanFactory;
            this.disabledPlugins = disabledPlugins;
        }

        @Override
        @SuppressWarnings("rawtypes")
        public Iterable<CRaSHPlugin<?>> getPlugins() {
            List<CRaSHPlugin<?>> plugins = new ArrayList<CRaSHPlugin<?>>();
            for (CRaSHPlugin<?> p : super.getPlugins()) {
                if (isEnabled(p)) {
                    plugins.add(p);
                }
            }
            Collection<CRaSHPlugin> pluginBeans = this.beanFactory.getBeansOfType(CRaSHPlugin.class).values();
            for (CRaSHPlugin<?> pluginBean : pluginBeans) {
                if (isEnabled(pluginBean)) {
                    plugins.add(pluginBean);
                }
            }
            return plugins;
        }

        protected boolean isEnabled(CRaSHPlugin<?> plugin) {
            Assert.notNull(plugin, "Plugin must not be null");
            if (ObjectUtils.isEmpty(this.disabledPlugins)) {
                return true;
            }
            Set<Class<?>> pluginClasses = ClassUtils.getAllInterfacesAsSet(plugin);
            pluginClasses.add(plugin.getClass());
            for (Class<?> pluginClass : pluginClasses) {
                if (isEnabled(pluginClass)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isEnabled(Class<?> pluginClass) {
            for (String disabledPlugin : this.disabledPlugins) {
                if (ClassUtils.getShortName(pluginClass).equalsIgnoreCase(disabledPlugin) || ClassUtils.getQualifiedName(pluginClass).equalsIgnoreCase(disabledPlugin)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class SimpleFileSystemDriver extends AbstractFSDriver<ResourceHandle> {

        private final ResourceHandle root;

        public SimpleFileSystemDriver(ResourceHandle handle) {
            this.root = handle;
        }

        @Override
        public Iterable<ResourceHandle> children(ResourceHandle handle) throws IOException {
            if (handle instanceof DirectoryHandle) {
                return ((DirectoryHandle) handle).members();
            }
            return Collections.emptySet();
        }

        @Override
        public long getLastModified(ResourceHandle handle) throws IOException {
            if (handle instanceof FileHandle) {
                return ((FileHandle) handle).getLastModified();
            }
            return -1;
        }

        @Override
        public boolean isDir(ResourceHandle handle) throws IOException {
            return handle instanceof DirectoryHandle;
        }

        @Override
        public String name(ResourceHandle handle) throws IOException {
            return handle.getName();
        }

        @Override
        public Iterator<InputStream> open(ResourceHandle handle) throws IOException {
            if (handle instanceof FileHandle) {
                return Collections.singletonList(((FileHandle) handle).openStream()).iterator();
            }
            return Collections.<InputStream>emptyList().iterator();
        }

        @Override
        public ResourceHandle root() throws IOException {
            return this.root;
        }
    }

    private static abstract class ResourceHandle {

        private final String name;

        public ResourceHandle(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    private static class DirectoryHandle extends ResourceHandle {

        private final ResourcePatternResolver resourceLoader;

        private final String[] filterPatterns;

        private final AntPathMatcher matcher = new AntPathMatcher();

        public DirectoryHandle(String name, ResourcePatternResolver resourceLoader, String[] filterPatterns) {
            super(name);
            this.resourceLoader = resourceLoader;
            this.filterPatterns = filterPatterns;
        }

        public List<ResourceHandle> members() throws IOException {
            Resource[] resources = this.resourceLoader.getResources(getName());
            List<ResourceHandle> files = new ArrayList<ResourceHandle>();
            for (Resource resource : resources) {
                if (!resource.getURL().getPath().endsWith("/") && !shouldFilter(resource)) {
                    files.add(new FileHandle(resource.getFilename(), resource));
                }
            }
            return files;
        }

        private boolean shouldFilter(Resource resource) {
            for (String filterPattern : this.filterPatterns) {
                if (this.matcher.match(filterPattern, resource.getFilename())) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class FileHandle extends ResourceHandle {

        private final Resource resource;

        public FileHandle(String name, Resource resource) {
            super(name);
            this.resource = resource;
        }

        public InputStream openStream() throws IOException {
            return this.resource.getInputStream();
        }

        public long getLastModified() {
            try {
                return this.resource.lastModified();
            } catch (IOException ex) {
                return -1;
            }
        }
    }
}
