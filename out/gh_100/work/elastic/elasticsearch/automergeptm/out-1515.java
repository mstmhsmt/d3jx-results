package org.elasticsearch.discovery.gce;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import org.elasticsearch.cloud.gce.GceInstancesServiceImpl;
import org.elasticsearch.cloud.gce.GceMetadataServiceImpl;
import org.elasticsearch.common.SuppressForbidden;
import org.elasticsearch.common.io.FileSystemUtils;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.plugin.discovery.gce.GceDiscoveryPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.assertNoTimeout;
import org.elasticsearch.common.settings.Setting;
import java.util.Arrays;

@ESIntegTestCase.SuppressLocalMode @ESIntegTestCase.ClusterScope(supportsDedicatedMasters = false, numDataNodes = 2, numClientNodes = 0) @SuppressForbidden(reason = "use http server") public class GceDiscoverTests extends ESIntegTestCase {
  public static class TestPlugin extends Plugin {

<<<<<<< commits-gh_100/elastic/elasticsearch/ef6e43e18d04b05a398c9d45a12807a4e9c5a406/GceDiscoverTests-1bc72f6.java
    public void onModule(SettingsModule module) {
      module.registerSetting(GceMetadataServiceImpl.GCE_HOST);
      module.registerSetting(GceInstancesServiceImpl.GCE_ROOT_URL);
      module.registerSetting(GceInstancesServiceImpl.GCE_VALIDATE_CERTIFICATES);
    }
=======
>>>>>>> Unknown file: This is a bug in JDime.


    @Override public List<Setting<?>> getSettings() {
      return Arrays.asList(GceComputeServiceImpl.GCE_HOST, GceComputeServiceImpl.GCE_ROOT_URL, GceComputeServiceImpl.GCE_VALIDATE_CERTIFICATES);
    }
  }

  private static HttpsServer httpsServer;

  private static HttpServer httpServer;

  private static Path logDir;

  @Override protected Collection<Class<? extends Plugin>> nodePlugins() {
    return pluginList(GceDiscoveryPlugin.class, TestPlugin.class);
  }

  @Override protected Settings nodeSettings(int nodeOrdinal) {
    Path resolve = logDir.resolve(Integer.toString(nodeOrdinal));
    try {
      Files.createDirectory(resolve);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return Settings.builder().put(super.nodeSettings(nodeOrdinal)).put("discovery.type", "gce").put("path.logs", resolve).put("transport.tcp.port", 0).put("node.portsfile", "true").put("cloud.gce.project_id", "testproject").put("cloud.gce.zone", "primaryzone").put("discovery.initial_state_timeout", "1s").put("cloud.gce.host", "http://" + httpServer.getAddress().getHostName() + ":" + httpServer.getAddress().getPort()).put("cloud.gce.root_url", "https://" + httpsServer.getAddress().getHostName() + ":" + httpsServer.getAddress().getPort()).put("cloud.gce.validate_certificates", false).build();
  }

  @BeforeClass public static void startHttpd() throws Exception {
    logDir = createTempDir();
    SSLContext sslContext = getSSLContext();
    httpsServer = HttpsServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress().getHostAddress(), 0), 0);
    httpServer = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress().getHostAddress(), 0), 0);
    httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext));
    httpServer.createContext("/computeMetadata/v1/instance/service-accounts/default/token", (s) -> {
      String response = GceMockUtils.readGoogleInternalJsonResponse("http://metadata.google.internal/computeMetadata/v1/instance/service-accounts/default/token");
      byte[] responseAsBytes = response.getBytes(StandardCharsets.UTF_8);
      s.sendResponseHeaders(200, responseAsBytes.length);
      OutputStream responseBody = s.getResponseBody();
      responseBody.write(responseAsBytes);
      responseBody.close();
    });
    httpsServer.createContext("/compute/v1/projects/testproject/zones/primaryzone/instances", (s) -> {
      Headers headers = s.getResponseHeaders();
      headers.add("Content-Type", "application/json; charset=UTF-8");
      ESLogger logger = Loggers.getLogger(GceDiscoverTests.class);
      try {
        Path[] files = FileSystemUtils.files(logDir);
        StringBuilder builder = new StringBuilder("{\"id\": \"dummy\",\"items\":[");
        int foundFiles = 0;
        for (int i = 0; i < files.length; i++) {
          Path resolve = files[i].resolve("transport.ports");
          if (Files.exists(resolve)) {
            if (foundFiles++ > 0) {
              builder.append(",");
            }
            List<String> addressses = Files.readAllLines(resolve);
            Collections.shuffle(addressses, random());
            logger.debug("addresses for node: [{}] published addresses [{}]", files[i].getFileName(), addressses);
            builder.append("{\"description\": \"ES Node ").append(files[i].getFileName()).append("\",\"networkInterfaces\": [ {");
            builder.append("\"networkIP\": \"").append(addressses.get(0)).append("\"}],");
            builder.append("\"status\" : \"RUNNING\"}");
          }
        }
        builder.append("]}");
        String responseString = builder.toString();
        final byte[] responseAsBytes = responseString.getBytes(StandardCharsets.UTF_8);
        s.sendResponseHeaders(200, responseAsBytes.length);
        OutputStream responseBody = s.getResponseBody();
        responseBody.write(responseAsBytes);
        responseBody.close();
      } catch (Exception e) {
        byte[] responseAsBytes = ("{ \"error\" : {\"message\" : \"" + e.toString() + "\" } }").getBytes(StandardCharsets.UTF_8);
        s.sendResponseHeaders(500, responseAsBytes.length);
        OutputStream responseBody = s.getResponseBody();
        responseBody.write(responseAsBytes);
        responseBody.close();
      }
    });
    httpsServer.start();
    httpServer.start();
  }

  private static SSLContext getSSLContext() throws Exception {
    char[] passphrase = "keypass".toCharArray();
    KeyStore ks = KeyStore.getInstance("JKS");
    try (InputStream stream = GceDiscoverTests.class.getResourceAsStream("/test-node.jks")) {
      assertNotNull("can\'t find keystore file", stream);
      ks.load(stream, passphrase);
    }
    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    kmf.init(ks, passphrase);
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(ks);
    SSLContext ssl = SSLContext.getInstance("TLS");
    ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
    return ssl;
  }

  @AfterClass public static void stopHttpd() throws IOException {
    for (int i = 0; i < internalCluster().size(); i++) {
      internalCluster().stopRandomDataNode();
    }
    httpsServer.stop(0);
    httpServer.stop(0);
    httpsServer = null;
    httpServer = null;
    logDir = null;
  }

  public void testJoin() throws ExecutionException, InterruptedException {
    assertNoTimeout(client().admin().cluster().prepareHealth().setWaitForNodes(Integer.toString(2)).get());
    internalCluster().startDataOnlyNodeAsync().get();
    assertNoTimeout(client().admin().cluster().prepareHealth().setWaitForNodes(Integer.toString(3)).get());
  }
}