package com.google.android.exoplayer2;

import java.util.HashSet;

public final class ExoPlayerLibraryInfo {

    public static final String VERSION = 
<<<<<<< commits-hd_100/google/ExoPlayer/7a109f741153e2924f7e698dd5554491df10f635-13cf35d4492fa6fded01b1751c3d8e60d72253d1/A.java
"2.5.0"
=======
"2.4.4"
>>>>>>> commits-hd_100/google/ExoPlayer/7a109f741153e2924f7e698dd5554491df10f635-13cf35d4492fa6fded01b1751c3d8e60d72253d1/B.java
;

    public static final String VERSION_SLASHY = 
<<<<<<< commits-hd_100/google/ExoPlayer/7a109f741153e2924f7e698dd5554491df10f635-13cf35d4492fa6fded01b1751c3d8e60d72253d1/A.java
"ExoPlayerLib/2.5.0"
=======
"ExoPlayerLib/2.4.4"
>>>>>>> commits-hd_100/google/ExoPlayer/7a109f741153e2924f7e698dd5554491df10f635-13cf35d4492fa6fded01b1751c3d8e60d72253d1/B.java
;

    
<<<<<<< commits-hd_100/google/ExoPlayer/7a109f741153e2924f7e698dd5554491df10f635-13cf35d4492fa6fded01b1751c3d8e60d72253d1/A.java

=======
int VERSION_INT = 2004004;
>>>>>>> commits-hd_100/google/ExoPlayer/7a109f741153e2924f7e698dd5554491df10f635-13cf35d4492fa6fded01b1751c3d8e60d72253d1/B.java


    public static final boolean ASSERTIONS_ENABLED = true;

    public static final boolean TRACE_ENABLED = true;

    public static synchronized String registeredModules() {
        return registeredModulesString;
    }

    public static final int VERSION_INT = 2005000;

    public static final String TAG = "ExoPlayer";

    static private String registeredModulesString = "goog.exo.core";

    public static synchronized void registerModule(String name) {
        if (registeredModules.add(name)) {
            registeredModulesString = registeredModulesString + ", " + name;
        }
    }

    static final private HashSet<String> registeredModules = new HashSet<>();

    private ExoPlayerLibraryInfo() {
    }
}
