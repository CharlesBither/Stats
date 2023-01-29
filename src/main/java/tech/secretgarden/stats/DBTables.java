package tech.secretgarden.stats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBTables {

    Database database = new Database();
    UserCheck userCheck = new UserCheck();

    BukkitRunnable updateStats = new BukkitRunnable() {
        @Override
        public void run() {
            // iterate through each player in playerMap
            for (Map.Entry<Player, HashMap<String, Integer>> entry : PlayerMap.playerMap.entrySet()) {

                Player player = entry.getKey();
                updateGeneralTable(player);
                updateKillTable(player);
                updateMineTable(player);
            }
        }
    };

    private void updateGeneralTable(Player player) {
        String uuid = player.getUniqueId().toString();
        String gamertag = player.getName();
        HashMap<String, Integer> statMap = PlayerMap.playerMap.get(player);

        int[] generalStats = new int[15];
        generalStats[0] = statMap.get(ColNames.BREED);
        generalStats[1] = statMap.get(ColNames.HOURS);
        generalStats[2] = statMap.get(ColNames.DEATH_COUNT);
        generalStats[3] = statMap.get(ColNames.DAMAGE_DEALT);
        generalStats[4] = statMap.get(ColNames.DAMAGE_TAKEN);
        generalStats[5] = statMap.get(ColNames.FISH);
        generalStats[6] = statMap.get(ColNames.FALL);
        generalStats[7] = statMap.get(ColNames.FLY);
        generalStats[8] = statMap.get(ColNames.WALK);
        generalStats[9] = statMap.get(ColNames.ENCHANTS);
        generalStats[10] = statMap.get(ColNames.JUMP);
        generalStats[11] = statMap.get(ColNames.RAID);
        generalStats[12] = statMap.get(ColNames.TRADES);
        generalStats[13] = statMap.get(ColNames.DEATH);
        generalStats[14] = statMap.get(ColNames.REST);

        // update table stats where uuid = uuid
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

                for (int i = 0; i < generalStats.length; i++) {
                    statement.setInt(i + 1, generalStats[i]);
                }
                statement.setString(16, uuid);
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
                                         "fly, walk, enchants, jump, raid, trades, death, rest) VALUES " +
                                         "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {

                statement.setString(1, gamertag);
                statement.setString(2, uuid);

                for (int i = 0; i < generalStats.length; i++) {
                    statement.setInt(i + 3, generalStats[i]);
                    System.out.println((i + 3) + " " + generalStats[i]);
                }
                statement.executeUpdate();
                Bukkit.getLogger().info("created General stats");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateKillTable(Player player) {
        String uuid = player.getUniqueId().toString();
        String gamertag = player.getName();
        HashMap<String, Integer> statMap = PlayerMap.playerMap.get(player);

        int[] killStats = new int[9];
        killStats[0] = statMap.get(ColNames.PLAYER);
        killStats[1] = statMap.get(ColNames.DRAGON);
        killStats[2] = statMap.get(ColNames.WITHER);
        killStats[3] = statMap.get(ColNames.WITHER_SKELETON);
        killStats[4] = statMap.get(ColNames.WITCH);
        killStats[5] = statMap.get(ColNames.VILLAGER);
        killStats[6] = statMap.get(ColNames.ZOMBIE_VILLAGER);
        killStats[7] = statMap.get(ColNames.WANDERING);
        killStats[8] = statMap.get(ColNames.GHAST);

        // update table stats where uuid = uuid
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

                for (int i = 0; i < killStats.length; i++) {
                            statement.setInt(i + 1, killStats[i]);
                }
                statement.setString(10, uuid);
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

                for (int i = 0; i < killStats.length; i++) {
                    statement.setInt(i + 3, killStats[i]);
                    System.out.println((i + 3) + " " + killStats[i]);
                }
                statement.executeUpdate();
                Bukkit.getLogger().info("created kills stats");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateMineTable(Player player) {
        String uuid = player.getUniqueId().toString();
        String gamertag = player.getName();
        HashMap<String, Integer> statMap = PlayerMap.playerMap.get(player);

        int[] mineStats = new int[5];
        mineStats[0] = statMap.get(ColNames.DIAMOND);
        mineStats[1] = statMap.get(ColNames.EMERALD);
        mineStats[2] = statMap.get(ColNames.COPPER);
        mineStats[3] = statMap.get(ColNames.GOLD);
        mineStats[4] = statMap.get(ColNames.IRON);


        // update table stats where uuid = uuid
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

                for (int i = 0; i < mineStats.length; i++) {
                    statement.setInt(i + 1, mineStats[i]);
                }
                statement.setString(6, uuid);
                statement.executeUpdate();
                Bukkit.getLogger().info("updated mine stats");
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

                for (int i = 0; i < mineStats.length; i++) {
                    statement.setInt(i + 3, mineStats[i]);
                    System.out.println((i + 3) + " " + mineStats[i]);
                }
                statement.executeUpdate();
                Bukkit.getLogger().info("created mine stats");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
