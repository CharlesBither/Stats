package tech.secretgarden.stats;

import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.ArrayList;

public final class Stats extends JavaPlugin {

    Database database = new Database();
    DBTables dbTables = new DBTables();

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

        // init a HashMap to hold all players as keys and a hashmap of <statName, data> as the values.
        PlayerMap.initPlayerMap();

        // run tasks every 5 minutes
        PlayerMap.updatePlayerMap.runTaskTimer(this, 20 * 20, 20 * 60 * 5);             // get stats
        dbTables.updateStats.runTaskTimerAsynchronously(this, 20 * 30, 20 * 60 * 5);    // update DB
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Stats plugin has been disabled");
        database.disconnect();
    }
}
