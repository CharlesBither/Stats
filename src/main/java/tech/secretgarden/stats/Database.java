package tech.secretgarden.stats;

import com.zaxxer.hikari.HikariDataSource;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {

    private static final ArrayList<String> list = Stats.dbList;
    public static HikariDataSource pool;

    public static void connect() throws SQLException {

        pool = new HikariDataSource();
        pool.setDriverClassName("com.mysql.jdbc.Driver");
        pool.setJdbcUrl("jdbc:mysql://" + list.get(0) + ":" + list.get(1) + "/" + list.get(2) + "?useSSL=true&autoReconnect=true");
        pool.setUsername(list.get(3));
        pool.setPassword(list.get(4));
    }

    public static boolean isConnected() {
        return pool != null;
    }

    public HikariDataSource getPool() {
        return pool;
    }

    public void disconnect() {
        if (isConnected()) {
            pool.close();
        }
    }
}
