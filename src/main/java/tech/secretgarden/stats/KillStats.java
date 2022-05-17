package tech.secretgarden.stats;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
                String uuid = player.getUniqueId().toString();
                String gamertag = player.getName();
                int playerKills = player.getStatistic(Statistic.PLAYER_KILLS);
                int dragon = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDER_DRAGON);
                int wither = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER);
                int witherSkeleton = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER_SKELETON);
                int witch = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITCH);
                int villager = player.getStatistic(Statistic.KILL_ENTITY, EntityType.VILLAGER);
                int zombieVillager = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE_VILLAGER);
                int wanderingTrader = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WANDERING_TRADER);
                int ghast = player.getStatistic(Statistic.KILL_ENTITY, EntityType.GHAST);

                list.add(playerKills);
                list.add(dragon);
                list.add(wither);
                list.add(witherSkeleton);
                list.add(witch);
                list.add(villager);
                list.add(zombieVillager);
                list.add(wanderingTrader);
                list.add(ghast);

                if (userCheck.hasData(uuid, "kills")) {
                    try (Connection connection = database.getPool().getConnection();
                         PreparedStatement statement = connection.prepareStatement(
                                 "UPDATE kills SET " +
                                         "player = ?, " +
                                         "dragon = ?, " +
                                         "wither = ?, " +
                                         "wither_skeleton = ?, " +
                                         "witch = ?, " +
                                         "villager = ?, " +
                                         "zombie_villager = ?, " +
                                         "wandering_trader = ?, " +
                                         "ghast = ? " +
                                         "WHERE uuid = ?")) {

                        for(int i = 0; i < list.size(); i++) {
                            statement.setInt(i + 1, list.get(i));
                        }
                        statement.setString(list.size() + 1, uuid);
                        statement.executeUpdate();
                        Bukkit.getLogger().info("updated kills stats");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try (Connection connection = database.getPool().getConnection();
                         PreparedStatement statement = connection.prepareStatement(
                                 "INSERT INTO kills " +
                                         "(gamertag, uuid, player, dragon, wither, wither_skeleton, witch, villager, " +
                                         "zombie_villager, wandering_trader, ghast) VALUES " +
                                         "(?,?,?,?,?,?,?,?,?,?,?)")) {

                        statement.setString(1, gamertag);
                        statement.setString(2, uuid);

                        for(int i = 0; i < list.size(); i++) {
                            statement.setInt(i + 3, list.get(i));
                            System.out.println((i + 3) + " " + list.get(i));
                        }
                        statement.executeUpdate();
                        Bukkit.getLogger().info("created kills stats");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
}
