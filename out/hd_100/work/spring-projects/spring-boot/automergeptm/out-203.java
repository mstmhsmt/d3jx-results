package org.springframework.boot.context.embedded;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Spring Boot's embedded servlet container support when developing
 * a jar application.
 *
 * @author Andy Wilkinson
 */
@EmbeddedServletContainerTest(packaging = "jar", launchers = { BootRunApplicationLauncher.class, IdeApplicationLauncher.class }) public class EmbeddedServletContainerJarDevelopmentIntegrationTests {
  @TestTemplate public void metaInfResourceFromDependencyIsAvailableViaHttp(RestTemplate rest) {
    ResponseEntity<String> entity = rest.getForEntity("/nested-meta-inf-resource.txt", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @TestTemplate public void metaInfResourceFromDependencyIsAvailableViaServletContext(RestTemplate rest) {
    ResponseEntity<String> entity = rest.getForEntity("/servletContext?/nested-meta-inf-resource.txt", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test public void metaInfResourceFromDependencyWithNameThatContainsReservedCharactersIsAvailableViaHttp() {
    ResponseEntity<String> entity = this.rest.getForEntity("/nested-reserved-%21%23%24%25%26%28%29%2A%2B%2C%3A%3D%3F%40%5B%5D-meta-inf-resource.txt", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getBody()).isEqualTo("encoded-name");
  }
}