package com.google.android.exoplayer2;
import java.util.HashSet;


<<<<<<< commits-hd_100/google/ExoPlayer/7a109f741153e2924f7e698dd5554491df10f635-13cf35d4492fa6fded01b1751c3d8e60d72253d1/A.java
/**
 * Information about the ExoPlayer library.
 */
public final class ExoPlayerLibraryInfo {
  /**
   * A tag to use when logging library information.
   */
  public static final String TAG = "ExoPlayer";

  /**
   * The version of the library expressed as a string, for example "1.2.3".
   */
  public static final String VERSION = "2.5.0";

  /**
   * The version of the library expressed as {@code "ExoPlayerLib/" + VERSION}.
   */
  public static final String VERSION_SLASHY = "ExoPlayerLib/2.5.0";

  /**
   * The version of the library expressed as an integer, for example 1002003.
   * <p>
   * Three digits are used for each component of {@link #VERSION}. For example "1.2.3" has the
   * corresponding integer version 1002003 (001-002-003), and "123.45.6" has the corresponding
   * integer version 123045006 (123-045-006).
   */
  public static final int VERSION_INT = 2005000;

  /**
   * Whether the library was compiled with {@link com.google.android.exoplayer2.util.Assertions}
   * checks enabled.
   */
  public static final boolean ASSERTIONS_ENABLED = true;

  /**
   * Whether the library was compiled with {@link com.google.android.exoplayer2.util.TraceUtil}
   * trace enabled.
   */
  public static final boolean TRACE_ENABLED = true;

  private static final HashSet<String> registeredModules = new HashSet<>();

  private static String registeredModulesString = "goog.exo.core";

  private ExoPlayerLibraryInfo() {
  }

  /**
   * Returns a string consisting of registered module names separated by ", ".
   */
  public static synchronized String registeredModules() {
    return registeredModulesString;
  }

  /**
   * Registers a module to be returned in the {@link #registeredModules()} string.
   *
   * @param name The name of the module being registered.
   */
  public static synchronized void registerModule(String name) {
    if (registeredModules.add(name)) {
      registeredModulesString = registeredModulesString + ", " + name;
    }
  }
}
=======
/**
 * Information about the ExoPlayer library.
 */
public interface ExoPlayerLibraryInfo {
  /**
   * The version of the library expressed as a string, for example "1.2.3".
   */
  String VERSION = "2.4.4";

  /**
   * The version of the library expressed as {@code "ExoPlayerLib/" + VERSION}.
   */
  String VERSION_SLASHY = "ExoPlayerLib/2.4.4";

  /**
   * The version of the library expressed as an integer, for example 1002003.
   * <p>
   * Three digits are used for each component of {@link #VERSION}. For example "1.2.3" has the
   * corresponding integer version 1002003 (001-002-003), and "123.45.6" has the corresponding
   * integer version 123045006 (123-045-006).
   */
  int VERSION_INT = 2004004;

  /**
   * Whether the library was compiled with {@link com.google.android.exoplayer2.util.Assertions}
   * checks enabled.
   */
  boolean ASSERTIONS_ENABLED = true;

  /**
   * Whether the library was compiled with {@link com.google.android.exoplayer2.util.TraceUtil}
   * trace enabled.
   */
  boolean TRACE_ENABLED = true;
}
>>>>>>> commits-hd_100/google/ExoPlayer/7a109f741153e2924f7e698dd5554491df10f635-13cf35d4492fa6fded01b1751c3d8e60d72253d1/B.java
