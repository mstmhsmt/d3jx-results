package com.google.android.exoplayer2;
import java.util.HashSet;


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
