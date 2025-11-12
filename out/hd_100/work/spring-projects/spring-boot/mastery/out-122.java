package org.springframework.boot.actuate.autoconfigure.web.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.junit.jupiter.api.Test;

@ClassPathExclusions("spring-webmvc-*")
class JerseyChildManagementContextConfigurationTests {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner().withUserConfiguration(JerseyChildManagementContextConfiguration.class);

    @Test
    void autoConfigurationIsConditionalOnServletWebApplication() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(JerseySameManagementContextConfiguration.class));
        contextRunner.run((context) -> assertThat(context).doesNotHaveBean(JerseySameManagementContextConfiguration.class));
    }

    @Test
    void autoConfigurationIsConditionalOnClassResourceConfig() {
        this.contextRunner.withClassLoader(new FilteredClassLoader(ResourceConfig.class)).run((context) -> assertThat(context).doesNotHaveBean(JerseySameManagementContextConfiguration.class));
    }

    
<<<<<<< commits-hd_100/spring-projects/spring-boot/262eb686d02edd03afe438e54d8bf20bf6de25ea-895d76e5921d368bd698a012fed465b077b44b8c/A.java
@Test
    void resourceConfigIsCustomizedWithResourceConfigCustomizerBean() {
        this.contextRunner.withUserConfiguration(CustomizerConfiguration.class).run((context) -> {
            assertThat(context).hasSingleBean(ResourceConfig.class);
            ResourceConfig config = context.getBean(ResourceConfig.class);
            ResourceConfigCustomizer customizer = context.getBean(ResourceConfigCustomizer.class);
            verify(customizer).customize(config);
        });
    }
=======

>>>>>>> commits-hd_100/spring-projects/spring-boot/262eb686d02edd03afe438e54d8bf20bf6de25ea-895d76e5921d368bd698a012fed465b077b44b8c/B.java


    @Test
    void jerseyApplicationPathIsAutoConfigured() {
        this.contextRunner.run((context) -> {
            JerseyApplicationPath bean = context.getBean(JerseyApplicationPath.class);
            assertThat(bean.getPath()).isEqualTo("/");
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    void servletRegistrationBeanIsAutoConfigured() {
        this.contextRunner.run((context) -> {
            ServletRegistrationBean<ServletContainer> bean = context.getBean(ServletRegistrationBean.class);
            assertThat(bean.getUrlMappings()).containsExactly("/*");
        });
    }

    @Test
    void resourceConfigCustomizerBeanIsNotRequired() {
        this.contextRunner.run((context) -> assertThat(context).hasSingleBean(ResourceConfig.class));
    }

    
<<<<<<< commits-hd_100/spring-projects/spring-boot/262eb686d02edd03afe438e54d8bf20bf6de25ea-895d76e5921d368bd698a012fed465b077b44b8c/A.java
@Configuration(proxyBeanMethods = false)
    static class CustomizerConfiguration {

        @Bean
        ResourceConfigCustomizer resourceConfigCustomizer() {
            return mock(ResourceConfigCustomizer.class);
        }
    }
=======

>>>>>>> commits-hd_100/spring-projects/spring-boot/262eb686d02edd03afe438e54d8bf20bf6de25ea-895d76e5921d368bd698a012fed465b077b44b8c/B.java

}
