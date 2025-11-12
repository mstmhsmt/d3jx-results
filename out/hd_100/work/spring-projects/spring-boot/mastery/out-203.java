package org.springframework.boot.context.embedded;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.web.client.RestTemplate;

@EmbeddedServletContainerTest(packaging = "jar", launchers = { BootRunApplicationLauncher.class, IdeApplicationLauncher.class })
public class EmbeddedServletContainerJarDevelopmentIntegrationTests {

    
<<<<<<< commits-hd_100/spring-projects/spring-boot/bbec7b0f37d0834c94b78bb588ec6970645b081d-6b61c999862db1e0b12fe08685a529efea04f45f/A.java
@TestTemplate
    public void metaInfResourceFromDependencyIsAvailableViaHttp(RestTemplate rest) {
        ResponseEntity<String> entity = rest.getForEntity("/nested-meta-inf-resource.txt", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }@TestTemplate
    public void metaInfResourceFromDependencyIsAvailableViaServletContext(RestTemplate rest) {
        ResponseEntity<String> entity = rest.getForEntity("/servletContext?/nested-meta-inf-resource.txt", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
=======
@Parameters(name = "{0}")
    public static Object[] parameters() {
        return AbstractEmbeddedServletContainerIntegrationTests.parameters("jar", Arrays.asList(BootRunApplicationLauncher.class, IdeApplicationLauncher.class));
    }public EmbeddedServletContainerJarDevelopmentIntegrationTests(String name, AbstractApplicationLauncher launcher) {
        super(name, launcher);
    }@Test
    public void metaInfResourceFromDependencyIsAvailableViaHttp() {
        ResponseEntity<String> entity = this.rest.getForEntity("/nested-meta-inf-resource.txt", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }@Test
    public void metaInfResourceFromDependencyIsAvailableViaServletContext() {
        ResponseEntity<String> entity = this.rest.getForEntity("/servletContext?/nested-meta-inf-resource.txt", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }@Test
    public void metaInfResourceFromDependencyWithNameThatContainsReservedCharactersIsAvailableViaHttp() {
        ResponseEntity<String> entity = this.rest.getForEntity("/nested-reserved-%21%23%24%25%26%28%29%2A%2B%2C%3A%3D%3F%40%5B%5D-meta-inf-resource.txt", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo("encoded-name");
    }
>>>>>>> commits-hd_100/spring-projects/spring-boot/bbec7b0f37d0834c94b78bb588ec6970645b081d-6b61c999862db1e0b12fe08685a529efea04f45f/B.java

}
