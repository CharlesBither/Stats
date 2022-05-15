package tech.secretgarden.stats;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public final class Stats extends JavaPlugin {

    Database database = new Database();
    GeneralStats generalStats = new GeneralStats();
    KillStats killStats = new KillStats();
    MineStats mineStats = new MineStats();

    public static ArrayList<String> dbList = new ArrayList<>();
    public ArrayList<String> getDbList() {
        dbList.add(getConfig().getString("HOST"));
        dbList.add(getConfig().getString("PORT"));
        dbList.add(getConfig().getString("DATABASE"));
        dbList.add(getConfig().getString("USERNAME"));
        dbList.add(getConfig().getString("PASSWORD"));
        return dbList;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (getConfig().getString("HOST") != null && getConfig().getString("DROPLET_HOST") != null) {
            try {
                getDbList();
                Database.connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        getLogger().info("Stats plugin has loaded");
        generalStats.updateOnlinePlayers.runTaskTimer(this, 20, 20 * 60 * 5);
        killStats.updateOnlinePlayers.runTaskTimer(this, 20 * 60, 20 * 60 * 5);
        mineStats.updateOnlinePlayers.runTaskTimer(this, 20 * 60 * 2, 20 * 60 * 5);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Stats plugin has been disabled");
        database.disconnect();
    }
}
