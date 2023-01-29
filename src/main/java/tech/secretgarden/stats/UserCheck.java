package tech.secretgarden.stats;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCheck {

    Database database = new Database();

    public boolean hasData(String uuid, String tableName) {
        try (Connection connection = database.getPool().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM " + tableName + " WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
