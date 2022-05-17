package tech.secretgarden.stats;

import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.ArrayList;

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

        if (getConfig().getString("HOST") != null) {
            try {
                getDbList();
                Database.connect();
                getLogger().info("Connected to droplet");
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
