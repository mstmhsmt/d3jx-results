package com.google.android.exoplayer2;

import java.util.HashSet;

public final class ExoPlayerLibraryInfo {

    public static final String VERSION = 
"2.5.0"
;

    public static final String VERSION_SLASHY = 
"ExoPlayerLib/2.5.0"
;

    



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
