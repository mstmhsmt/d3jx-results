package com.yammer.dropwizard.config;

import com.google.common.collect.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.reflection.AnnotatedMethod;
import com.sun.jersey.core.reflection.MethodList;
import com.sun.jersey.core.spi.scanning.PackageNamesScanner;
import com.yammer.dropwizard.jersey.DropwizardResourceConfig;
import com.yammer.dropwizard.jetty.JettyManaged;
import com.yammer.dropwizard.jetty.NonblockingServletHolder;
import com.yammer.dropwizard.lifecycle.ExecutorServiceManager;
import com.yammer.dropwizard.lifecycle.Managed;
import com.yammer.dropwizard.tasks.GarbageCollectionTask;
import com.yammer.dropwizard.tasks.Task;
import com.yammer.metrics.core.HealthCheck;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import javax.annotation.Nullable;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.EventListener;
import java.util.concurrent.*;
import static com.google.common.base.Preconditions.checkNotNull;
import java.io.IOException;
import org.eclipse.jetty.util.resource.Resource;
import com.yammer.dropwizard.json.ObjectMapperFactory;
import org.slf4j.LoggerFactory;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.slf4j.Logger;

public class Environment extends AbstractLifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);

    private final DropwizardResourceConfig config;

    private final ImmutableSet.Builder<HealthCheck> healthChecks;

    private final ImmutableMap.Builder<String, ServletHolder> servlets;

    private final ImmutableMultimap.Builder<String, FilterHolder> filters;

    private final ImmutableSet.Builder<EventListener> servletListeners;

    private final ImmutableSet.Builder<Task> tasks;

    private final AggregateLifeCycle lifeCycle;

    private SessionHandler sessionHandler;

    public Environment(String name, Configuration configuration, ObjectMapperFactory objectMapperFactory) {
        this.name = name;
        this.configuration = configuration;
        this.objectMapperFactory = objectMapperFactory;
        this.config = new DropwizardResourceConfig(false) {

            @Override
            public void validate() {
                super.validate();
                logResources();
                logProviders();
                logHealthChecks();
                logManagedObjects();
                logEndpoints();
                logTasks();
            }
        };
        this.healthChecks = ImmutableSet.builder();
        this.servlets = ImmutableMap.builder();
        this.filters = ImmutableMultimap.builder();
        this.servletListeners = ImmutableSet.builder();
        this.tasks = ImmutableSet.builder();
        this.baseResource = Resource.newClassPathResource(".");
        this.protectedTargets = ImmutableSet.builder();
        this.lifeCycle = new AggregateLifeCycle();
        this.jerseyServletContainer = new ServletContainer(config);
        addTask(new GarbageCollectionTask());
    }

    @Override
    protected void doStart() throws Exception {
        lifeCycle.start();
    }

    @Override
    protected void doStop() throws Exception {
        lifeCycle.stop();
    }

    public void addResource(Object resource) {
        config.getSingletons().add(checkNotNull(resource));
    }

    public void scanPackagesForResourcesAndProviders(Class<?>... classes) {
        checkNotNull(classes);
        final String[] names = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            names[i] = classes[i].getPackage().getName();
        }
        config.init(new PackageNamesScanner(names));
    }

    public void addResource(Class<?> klass) {
        config.getClasses().add(checkNotNull(klass));
    }

    public void addProvider(Object provider) {
        config.getSingletons().add(checkNotNull(provider));
    }

    public void addProvider(Class<?> klass) {
        config.getClasses().add(checkNotNull(klass));
    }

    public void addHealthCheck(HealthCheck healthCheck) {
        healthChecks.add(checkNotNull(healthCheck));
    }

    public void manage(Managed managed) {
        lifeCycle.addBean(new JettyManaged(checkNotNull(managed)));
    }

    public void manage(LifeCycle managed) {
        lifeCycle.addBean(checkNotNull(managed));
    }

    public ServletBuilder addServlet(Servlet servlet, String urlPattern) {
        final ServletHolder holder = new NonblockingServletHolder(checkNotNull(servlet));
        final ServletBuilder servletConfig = new ServletBuilder(holder, servlets);
        servletConfig.addUrlPattern(checkNotNull(urlPattern));
        return servletConfig;
    }

    public ServletBuilder addServlet(Class<? extends Servlet> klass, String urlPattern) {
        final ServletHolder holder = new ServletHolder(checkNotNull(klass));
        final ServletBuilder servletConfig = new ServletBuilder(holder, servlets);
        servletConfig.addUrlPattern(checkNotNull(urlPattern));
        return servletConfig;
    }

    public FilterBuilder addFilter(Filter filter, String urlPattern) {
        final FilterHolder holder = new FilterHolder(checkNotNull(filter));
        final FilterBuilder filterConfig = new FilterBuilder(holder, filters);
        filterConfig.addUrlPattern(checkNotNull(urlPattern));
        return filterConfig;
    }

    public FilterBuilder addFilter(Class<? extends Filter> klass, String urlPattern) {
        final FilterHolder holder = new FilterHolder(checkNotNull(klass));
        final FilterBuilder filterConfig = new FilterBuilder(holder, filters);
        filterConfig.addUrlPattern(checkNotNull(urlPattern));
        return filterConfig;
    }

    public void addServletListeners(EventListener... listeners) {
        this.servletListeners.add(listeners);
    }

    public void addTask(Task task) {
        tasks.add(checkNotNull(task));
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public void enableJerseyFeature(String name) {
        config.getFeatures().put(checkNotNull(name), Boolean.TRUE);
    }

    public void disableJerseyFeature(String name) {
        config.getFeatures().put(checkNotNull(name), Boolean.FALSE);
    }

    public void setJerseyProperty(String name, @Nullable Object value) {
        config.getProperties().put(checkNotNull(name), value);
    }

    public ExecutorService managedExecutorService(String nameFormat, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(nameFormat).build();
        final ExecutorService executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(), threadFactory);
        manage(new ExecutorServiceManager(executor, 5, TimeUnit.SECONDS, nameFormat));
        return executor;
    }

    public ScheduledExecutorService managedScheduledExecutorService(String nameFormat, int corePoolSize) {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(nameFormat).build();
        final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
        manage(new ExecutorServiceManager(executor, 5, TimeUnit.SECONDS, nameFormat));
        return executor;
    }

    ImmutableSet<HealthCheck> getHealthChecks() {
        return healthChecks.build();
    }

    ImmutableMap<String, ServletHolder> getServlets() {
        addServlet(jerseyServletContainer, configuration.getHttpConfiguration().getRootPath()).setInitOrder(Integer.MAX_VALUE);
        return servlets.build();
    }

    ImmutableMultimap<String, FilterHolder> getFilters() {
        return filters.build();
    }

    ImmutableSet<Task> getTasks() {
        return tasks.build();
    }

    ImmutableSet<EventListener> getServletListeners() {
        return servletListeners.build();
    }

    private void logManagedObjects() {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (Object bean : lifeCycle.getBeans()) {
            builder.add(bean.toString());
        }
        LOGGER.debug("managed objects = {}", builder.build());
    }

    private void logHealthChecks() {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (HealthCheck healthCheck : healthChecks.build()) {
            final String canonicalName = healthCheck.getClass().getCanonicalName();
            if (canonicalName == null) {
                builder.add(String.format("%s(\"%s\")", HealthCheck.class.getCanonicalName(), healthCheck.getName()));
            } else {
                builder.add(canonicalName);
            }
        }
        LOGGER.debug("health checks = {}", builder.build());
    }

    private void logResources() {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (Class<?> klass : config.getClasses()) {
            if (klass.isAnnotationPresent(Path.class)) {
                builder.add(klass.getCanonicalName());
            }
        }
        for (Object o : config.getSingletons()) {
            if (o.getClass().isAnnotationPresent(Path.class)) {
                builder.add(o.getClass().getCanonicalName());
            }
        }
        LOGGER.debug("resources = {}", builder.build());
    }

    private void logProviders() {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (Class<?> klass : config.getClasses()) {
            if (klass.isAnnotationPresent(Provider.class)) {
                builder.add(klass.getCanonicalName());
            }
        }
        for (Object o : config.getSingletons()) {
            if (o.getClass().isAnnotationPresent(Provider.class)) {
                builder.add(o.getClass().getCanonicalName());
            }
        }
        LOGGER.debug("providers = {}", builder.build());
    }

    private void logEndpoints() {
        final StringBuilder stringBuilder = new StringBuilder(1024).append("\n\n");
        final ImmutableList.Builder<Class<?>> builder = ImmutableList.builder();
        for (Object o : config.getSingletons()) {
            if (o.getClass().isAnnotationPresent(Path.class)) {
                builder.add(o.getClass());
            }
        }
        for (Class<?> klass : config.getClasses()) {
            if (klass.isAnnotationPresent(Path.class)) {
                builder.add(klass);
            }
        }
        for (Class<?> klass : builder.build()) {
            final String path = klass.getAnnotation(Path.class).value();
            final ImmutableList.Builder<String> endpoints = ImmutableList.builder();
            for (AnnotatedMethod method : annotatedMethods(klass)) {
                String rootPath = configuration.getHttpConfiguration().getRootPath();
                if (rootPath.endsWith("/*")) {
                    rootPath = rootPath.substring(0, rootPath.length() - 2);
                }
                final StringBuilder pathBuilder = new StringBuilder().append(rootPath).append(path);
                if (method.isAnnotationPresent(Path.class)) {
                    final String methodPath = method.getAnnotation(Path.class).value();
                    if (!methodPath.startsWith("/") && !path.endsWith("/")) {
                        pathBuilder.append('/');
                    }
                    pathBuilder.append(methodPath);
                }
                for (HttpMethod verb : method.getMetaMethodAnnotations(HttpMethod.class)) {
                    endpoints.add(String.format("    %-7s %s (%s)", verb.value(), pathBuilder.toString(), klass.getCanonicalName()));
                }
            }
            for (String line : Ordering.natural().sortedCopy(endpoints.build())) {
                stringBuilder.append(line).append('\n');
            }
        }
        LOGGER.info(stringBuilder.toString());
    }

    private void logTasks() {
        final StringBuilder stringBuilder = new StringBuilder(1024).append("\n\n");
        for (Task task : tasks.build()) {
            stringBuilder.append(String.format("    %-7s /tasks/%s (%s)\n", "POST", task.getName(), task.getClass().getCanonicalName()));
        }
        LOGGER.info("tasks = {}", stringBuilder.toString());
    }

    private MethodList annotatedMethods(Class<?> resource) {
        return new MethodList(resource, true).hasMetaAnnotation(HttpMethod.class);
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    private final ImmutableSet.Builder<String> protectedTargets;

    private final ObjectMapperFactory objectMapperFactory;

    public void setJerseyServletContainer(ServletContainer jerseyServletContainer) {
        this.jerseyServletContainer = checkNotNull(jerseyServletContainer);
    }

    private final Configuration configuration;

    private final String name;

    ImmutableSet<String> getProtectedTargets() {
        return protectedTargets.build();
    }

    private ServletContainer jerseyServletContainer;

    private Resource baseResource;

    public void setBaseResource(Resource baseResource) throws IOException {
        this.baseResource = baseResource;
    }

    Resource getBaseResource() {
        return baseResource;
    }

    public void addProtectedTarget(String target) {
        protectedTargets.add(checkNotNull(target));
    }

    public String getName() {
        return name;
    }

    public ServletContainer getJerseyServletContainer() {
        return jerseyServletContainer;
    }

    public ResourceConfig getJerseyResourceConfig() {
        return config;
    }

    public ObjectMapperFactory getObjectMapperFactory() {
        return objectMapperFactory;
    }
}
