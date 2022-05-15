package tech.secretgarden.stats;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KillStats {

    Database database = new Database();
    UserCheck userCheck = new UserCheck();

    BukkitRunnable updateOnlinePlayers = new BukkitRunnable() {
        @Override
        public void run() {
            ImmutableList<Player> onlinePlayerList = ImmutableList.copyOf(Bukkit.getOnlinePlayers());
            for (Player player : onlinePlayerList) {
                List<Integer> list = new ArrayList<>();
                int playerKills = player.getStatistic(Statistic.PLAYER_KILLS);
                int dragonKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDER_DRAGON);
                int witherKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER);
                int witherSkeletonKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER_SKELETON);
                int witchKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITCH);
                int villagerKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.VILLAGER);
                int zombieVillagerKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE_VILLAGER);
                int wanderingTraderKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WANDERING_TRADER);
                int ghastKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.GHAST);
            }
        }
    };
}
