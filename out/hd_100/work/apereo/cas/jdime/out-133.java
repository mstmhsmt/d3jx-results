
<<<<<<< commits-hd_100/apereo/cas/10a131170315f5034f5033c920ed8d3b8522e7b9-03355388770eaf1cf5264c38a88f80b7adfd8493/A.java
package org.jasig.cas.util.http;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.jasig.cas.util.http.HttpClient;
import org.jasig.cas.util.http.SimpleHttpClient;
import org.junit.Test;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Scott Battaglia
 * @since 3.1
 */
public class SimpleHttpClientTests {
  private SimpleHttpClient getHttpClient() throws Exception {
    final SimpleHttpClient httpClient = new SimpleHttpClientFactoryBean().getObject();
    return httpClient;
  }

  @Test public void testOkayUrl() throws Exception {
    assertTrue(this.getHttpClient().isValidEndPoint("http://www.google.com"));
  }

  @Test public void testBadUrl() throws Exception {
    assertFalse(this.getHttpClient().isValidEndPoint("http://www.apereo.org/scottb.html"));
  }

  @Test public void testInvalidHttpsUrl() throws Exception {
    final HttpClient client = this.getHttpClient();
    assertFalse(client.isValidEndPoint("https://static.ak.connect.facebook.com"));
  }

  @Test public void testBypassedInvalidHttpsUrl() throws Exception {
    final SimpleHttpClientFactoryBean clientFactory = new SimpleHttpClientFactoryBean();
    clientFactory.setSslSocketFactory(getFriendlyToAllSSLSocketFactory());
    clientFactory.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    clientFactory.setAcceptableCodes(new int[] { 200, 403 });
    final SimpleHttpClient client = clientFactory.getObject();
    assertTrue(client.isValidEndPoint("https://static.ak.connect.facebook.com"));
  }

  private SSLConnectionSocketFactory getFriendlyToAllSSLSocketFactory() throws Exception {
    final TrustManager trm = new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
      }

      public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
      }
    };
    final SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, new TrustManager[] { trm }, null);
    return new SSLConnectionSocketFactory(sc);
  }
}
=======
package org.jasig.cas.util;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.junit.Test;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Scott Battaglia
 * @since 3.1
 */
public class SimpleHttpClientTests {
  private SimpleHttpClient getHttpClient() {
    final SimpleHttpClient httpClient = new SimpleHttpClient();
    return httpClient;
  }

  @Test public void testOkayUrl() {
    assertTrue(this.getHttpClient().isValidEndPoint("http://www.google.com"));
  }

  @Test public void testBadUrl() {
    assertFalse(this.getHttpClient().isValidEndPoint("https://www.abc1234.org"));
  }

  @Test public void testInvalidHttpsUrl() {
    final HttpClient client = this.getHttpClient();
    assertFalse(client.isValidEndPoint("https://static.ak.connect.facebook.com"));
  }

  @Test public void testBypassedInvalidHttpsUrl() throws Exception {
    final SimpleHttpClient client = new SimpleHttpClient(getFriendlyToAllSSLSocketFactory(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER, new int[] { 200, 403 });
    assertTrue(client.isValidEndPoint("https://static.ak.connect.facebook.com"));
  }

  private SSLConnectionSocketFactory getFriendlyToAllSSLSocketFactory() throws Exception {
    final TrustManager trm = new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
      }

      public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
      }
    };
    final SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, new TrustManager[] { trm }, null);
    return new SSLConnectionSocketFactory(sc);
  }
}
>>>>>>> commits-hd_100/apereo/cas/10a131170315f5034f5033c920ed8d3b8522e7b9-03355388770eaf1cf5264c38a88f80b7adfd8493/B.java
