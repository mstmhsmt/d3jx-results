package org.springframework.boot.context.embedded;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.web.client.RestTemplate;

@EmbeddedServletContainerTest(packaging = "jar", launchers = { BootRunApplicationLauncher.class, IdeApplicationLauncher.class })
public class EmbeddedServletContainerJarDevelopmentIntegrationTests {

    
@TestTemplate
    public void metaInfResourceFromDependencyIsAvailableViaHttp(RestTemplate rest) {
        ResponseEntity<String> entity = rest.getForEntity("/nested-meta-inf-resource.txt", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }@TestTemplate
    public void metaInfResourceFromDependencyIsAvailableViaServletContext(RestTemplate rest) {
        ResponseEntity<String> entity = rest.getForEntity("/servletContext?/nested-meta-inf-resource.txt", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
