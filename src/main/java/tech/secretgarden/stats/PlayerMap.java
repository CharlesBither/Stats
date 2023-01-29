package tech.secretgarden.stats;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PlayerMap {

    public static HashMap<Player, HashMap<String, Integer>> playerMap;

    public static void initPlayerMap() {
        playerMap = new HashMap<>();
    }

    static BukkitRunnable updatePlayerMap = new BukkitRunnable() {
        @Override
        public void run() {

            ImmutableList<Player> onlinePlayerList = ImmutableList.copyOf(Bukkit.getOnlinePlayers());
            // get statMap and put into playerMap
            for (Player player : onlinePlayerList) {

                // get all stats for the player
                HashMap<String, Integer> statMap = StatMap.getStatMap(player);
                // put into playerMap
                playerMap.put(player, statMap);
            }
        }
    };
}
