package tech.secretgarden.stats;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MineStats {

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
                int diamond = player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_DIAMOND_ORE);
                int copper = player.getStatistic(Statistic.MINE_BLOCK, Material.COPPER_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_COPPER_ORE);
                int emerald = player.getStatistic(Statistic.MINE_BLOCK, Material.EMERALD_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_EMERALD_ORE);
                int gold = player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_GOLD_ORE);
                int iron = player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_IRON_ORE);

                list.add(diamond);
                list.add(emerald);
                list.add(copper);
                list.add(gold);
                list.add(iron);

                if (userCheck.hasData(uuid, "mine")) {
                    try (Connection connection = database.getPool().getConnection();
                         PreparedStatement statement = connection.prepareStatement(
                                 "UPDATE mine SET " +
                                         "diamond = ?, " +
                                         "emerald = ?, " +
                                         "copper = ?, " +
                                         "gold = ?, " +
                                         "iron = ? " +
                                         "WHERE uuid = ?")) {

                        for(int i = 0; i < list.size(); i++) {
                            statement.setInt(i + 1, list.get(i));
                        }
                        statement.setString(list.size() + 1, uuid);
                        statement.executeUpdate();
                        Bukkit.getLogger().info("updated mining stats");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try (Connection connection = database.getPool().getConnection();
                         PreparedStatement statement = connection.prepareStatement(
                                 "INSERT INTO mine " +
                                         "(gamertag, uuid, diamond, emerald, copper, gold, iron) VALUES " +
                                         "(?,?,?,?,?,?,?)")) {

                        statement.setString(1, gamertag);
                        statement.setString(2, uuid);

                        for(int i = 0; i < list.size(); i++) {
                            statement.setInt(i + 3, list.get(i));
                        }
                        statement.executeUpdate();
                        Bukkit.getLogger().info("created mining stats");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
}
