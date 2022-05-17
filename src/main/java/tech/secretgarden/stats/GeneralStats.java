package tech.secretgarden.stats;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneralStats {

    Database database = new Database();
    UserCheck userCheck = new UserCheck();

    BukkitRunnable updateOnlinePlayers = new BukkitRunnable() {
        @Override
        public void run() {
            ImmutableList<Player> onlinePlayerList = ImmutableList.copyOf(Bukkit.getOnlinePlayers());
            for (Player player : onlinePlayerList) {
                List<Integer> list = new ArrayList<>();
                String gamertag = player.getName();
                String uuid = player.getUniqueId().toString();
                int breed = player.getStatistic(Statistic.ANIMALS_BRED);
                //int craft = player.getStatistic(Statistic.CRAFT_ITEM);
                long time = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
                int hours = (int) (((time / 20) / 60) / 60);
                int deathCount = player.getStatistic(Statistic.DEATHS);
                int damageDealt = player.getStatistic(Statistic.DAMAGE_DEALT);
                int damageTaken = player.getStatistic(Statistic.DAMAGE_TAKEN);
                int fish = player.getStatistic(Statistic.FISH_CAUGHT);
                int fall = player.getStatistic(Statistic.FALL_ONE_CM) / 100;
                int fly = player.getStatistic(Statistic.FLY_ONE_CM) / 100;
                int walk = player.getStatistic(Statistic.WALK_ONE_CM) / 100;
                int enchants = player.getStatistic(Statistic.ITEM_ENCHANTED);
                int jump = player.getStatistic(Statistic.JUMP);

                int raid = player.getStatistic(Statistic.RAID_WIN);
                int trades = player.getStatistic(Statistic.TRADED_WITH_VILLAGER);
                long deathTime = player.getStatistic(Statistic.TIME_SINCE_DEATH);
                int death = (int) (((deathTime / 20) / 60) / 60);
                long restTime = player.getStatistic(Statistic.TIME_SINCE_REST);
                int rest = (int) (((restTime / 20) / 60) / 60);

                list.add(breed);
                list.add(hours);
                list.add(deathCount);
                list.add(damageDealt);
                list.add(damageTaken);
                list.add(fish);
                list.add(fall);
                list.add(fly);
                list.add(walk);
                list.add(enchants);
                list.add(jump);
                list.add(raid);
                list.add(trades);
                list.add(death);
                list.add(rest);


                if (userCheck.hasData(uuid, "general")) {
                    try (Connection connection = database.getPool().getConnection();
                         PreparedStatement statement = connection.prepareStatement(
                                 "UPDATE general SET " +
                                         "breed = ?, " +
                                         "hours = ?, " +
                                         "death_count = ?, " +
                                         "damage_dealt = ?, " +
                                         "damage_taken = ?, " +
                                         "fish = ?, " +
                                         "fall = ?, " +
                                         "fly = ?, " +
                                         "walk = ?, " +
                                         "enchants = ?, " +
                                         "jump = ?, " +
                                         "raid = ?, " +
                                         "trades = ?, " +
                                         "death = ?, " +
                                         "rest = ?" +
                                 " WHERE uuid = ?")) {

                        for(int i = 0; i < list.size(); i++) {
                            statement.setInt(i + 1, list.get(i));
                        }
                        statement.setString(list.size() + 1, uuid);
                        statement.executeUpdate();
                        Bukkit.getLogger().info("updated general stats");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try (Connection connection = database.getPool().getConnection();
                         PreparedStatement statement = connection.prepareStatement(
                                 "INSERT INTO general " +
                                         "(gamertag, uuid, breed, hours, death_count, damage_dealt, damage_taken, fish, fall, " +
                                         "fly, walk, enchants, jump, raid, trades, death, rest, user_key) VALUES " +
                                         "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {

                        statement.setString(1, gamertag);
                        statement.setString(2, uuid);

                        for(int i = 0; i < list.size(); i++) {
                            statement.setInt(i + 3, list.get(i));
                            System.out.println((i + 3) + " " + list.get(i));
                        }

                        statement.executeUpdate();
                        Bukkit.getLogger().info("created general stats");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
    };
}
