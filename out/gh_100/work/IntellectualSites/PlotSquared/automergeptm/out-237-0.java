package com.plotsquared.core.util;
import com.plotsquared.core.command.CommandCaller;
import com.plotsquared.core.configuration.Captions;
import com.plotsquared.core.configuration.Settings;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.permissions.PermissionHolder;
import java.util.HashMap;
import com.plotsquared.core.player.MetaDataAccess;
import com.plotsquared.core.player.PlayerMetaDataKeys;
import java.util.Map;

/**
 * The Permissions class handles checking user permissions.<br>
 * - This will respect * nodes and plots.admin and can be used to check permission ranges (e.g. plots.plot.5)<br>
 * - Checking the PlotPlayer class directly will not take the above into account<br>
 */
public class Permissions {
  public static boolean hasPermission(PlotPlayer<?> player, Captions caption, boolean notify) {
    return hasPermission(player, caption.getTranslated(), notify);
  }

  /**
     * Check if a player has a permission (Captions class helps keep track of permissions).
     *
     * @param player
     * @param caption
     * @return
     */
  public static boolean hasPermission(PlotPlayer<?> player, Captions caption) {
    return hasPermission(player, caption.getTranslated());
  }

  /**
     * Check if a {@link PlotPlayer} has a permission.
     *
     * @param player
     * @param permission
     * @return
     */
  public static boolean hasPermission(PlotPlayer<?> player, String permission) {
    if (!Settings.Enabled_Components.PERMISSION_CACHE) {
      return hasPermission((PermissionHolder) player, permission);
    }

    boolean result = hasPermission((PermissionHolder) player, permission);

    try (MetaDataAccess<Map<String, Boolean>> mapAccess = player.accessTemporaryMetaData(PlayerMetaDataKeys.TEMPORARY_PERMISSIONS)) {
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
  }

  /**
     * Check if a {@code CommandCaller} has a permission.
     *
     * @param caller
     * @param permission
     * @return
     */
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

  /**
     * Checks if a PlotPlayer has a permission, and optionally send the no permission message if applicable.
     *
     * @param player
     * @param permission
     * @param notify
     * @return
     */
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

  /**
     * Check the the highest permission a PlotPlayer has within a specified range.<br>
     * - Excessively high values will lag<br>
     * - The default range that is checked is {@link Settings.Limit#MAX_PLOTS}<br>
     *
     * @param player Player to check for
     * @param stub   The permission stub to check e.g. for `plots.plot.#` the stub is `plots.plot`
     * @param range  The range to check
     * @return The highest permission they have within that range
     */
  public static int hasPermissionRange(PlotPlayer<?> player, String stub, int range) {
    return player.hasPermissionRange(stub, range);
  }
}
