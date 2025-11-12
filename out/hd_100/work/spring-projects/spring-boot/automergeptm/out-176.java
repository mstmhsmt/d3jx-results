package org.springframework.boot.web.server;
import org.springframework.util.unit.DataSize;

/**
 * Simple server-independent abstraction for compression configuration.
 *
 * @author Ivan Sopov
 * @author Andy Wilkinson
 * @since 2.0.0
 */
public class Compression {
  /**
	 * Whether response compression is enabled.
	 */
  private boolean enabled = false;

  /**
	 * Comma-separated list of MIME types that should be compressed.
	 */
  private String[] mimeTypes = new String[] { "text/html", "text/xml", "text/plain", "text/css", "text/javascript", "application/javascript", "application/json", "application/xml" };

  /**
	 * Comma-separated list of user agents for which responses should not be compressed.
	 */
  private String[] excludedUserAgents = null;

  /**
	 * Minimum "Content-Length" value that is required for compression to be performed.
	 */
  private DataSize minResponseSize = DataSize.ofKilobytes(2);

  public boolean getEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String[] getMimeTypes() {
    return this.mimeTypes;
  }

  public void setMimeTypes(String[] mimeTypes) {
    this.mimeTypes = mimeTypes;
  }

  public DataSize getMinResponseSize() {
    return this.minResponseSize;
  }

  public void setMinResponseSize(DataSize minSize) {
    this.minResponseSize = minSize;
  }

  public String[] getExcludedUserAgents() {
    return this.excludedUserAgents;
  }

  public void setExcludedUserAgents(String[] excludedUserAgents) {
    this.excludedUserAgents = excludedUserAgents;
  }
}