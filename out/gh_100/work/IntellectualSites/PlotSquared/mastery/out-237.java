package com.plotsquared.core.util;

import com.plotsquared.core.command.CommandCaller;
import com.plotsquared.core.configuration.Captions;
import com.plotsquared.core.configuration.Settings;
import com.plotsquared.core.player.PlotPlayer;
import java.util.HashMap;
import com.plotsquared.core.permissions.PermissionHolder;
import java.util.Map;
import com.plotsquared.core.player.PlayerMetaDataKeys;
import com.plotsquared.core.player.MetaDataAccess;

public class Permissions {

    public static boolean hasPermission(PlotPlayer<?> player, Captions caption, boolean notify) {
        return hasPermission(player, caption.getTranslated(), notify);
    }

    public static boolean hasPermission(PlotPlayer<?> player, Captions caption) {
        return hasPermission(player, caption.getTranslated());
    }

    public static boolean hasPermission(PlotPlayer<?> player, String permission) {
        if (!Settings.Enabled_Components.PERMISSION_CACHE) {
            return hasPermission((PermissionHolder) player, permission);
        }
        
<<<<<<< commits-gh_100/IntellectualSites/PlotSquared/d8e80daa9349ca3ff11130e8f4c586f0f43816a0/Permissions-7e3b462.java
if (map != null) {
            Boolean result = map.get(permission);
            if (result != null) {
                return result;
            }
        } else {
            map = new HashMap<>();
            player.setMeta("perm", map);
        }boolean result = hasPermission((PermissionHolder) player, permission);map.put(permission, result);return result;
=======
try (final MetaDataAccess<Map<String, Boolean>> mapAccess = player.accessTemporaryMetaData(PlayerMetaDataKeys.TEMPORARY_PERMISSIONS)) {
            Map<String, Boolean> map = mapAccess.get().orElse(null);
            if (map != null) {
                final Boolean result = map.get(permission);
                if (result != null) {
                    return result;
                }
            } else {
                mapAccess.set((map = new HashMap<>()));
            }
            boolean result = hasPermission((CommandCaller) player, permission);
            map.put(permission, result);
            return result;
        }
>>>>>>> commits-gh_100/IntellectualSites/PlotSquared/722361aedb0b363e3830367d8bd0ebccce271183/Permissions-32dc6a7.java

    }

    public static boolean hasPermission(PermissionHolder caller, String permission) {
        if (caller.hasPermission(permission)) {
            return true;
        }
        if (caller.hasPermission(Captions.PERMISSION_ADMIN.getTranslated())) {
            return true;
        }
        permission = permission.toLowerCase().replaceAll("^[^a-z|0-9|\\.|_|-]", "");
        String[] nodes = permission.split("\\.");
        StringBuilder n = new StringBuilder();
        for (int i = 0; i <= (nodes.length - 1); i++) {
            n.append(nodes[i] + ".");
            String combined = n + Captions.PERMISSION_STAR.getTranslated();
            if (!permission.equals(combined)) {
                if (caller.hasPermission(combined)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasPermission(PlotPlayer<?> player, String permission, boolean notify) {
        if (!hasPermission(player, permission)) {
            if (notify) {
                MainUtil.sendMessage(player, Captions.NO_PERMISSION_EVENT, permission);
            }
            return false;
        }
        return true;
    }

    public static int hasPermissionRange(PlotPlayer<?> player, Captions perm, int range) {
        return hasPermissionRange(player, perm.getTranslated(), range);
    }

    public static int hasPermissionRange(PlotPlayer<?> player, String stub, int range) {
        return player.hasPermissionRange(stub, range);
    }
}
