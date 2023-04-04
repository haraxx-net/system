package net.haraxx.system.database.sql;

import net.haraxx.system.HaraxxPlugin;
import net.haraxx.system.database.HaraxxSQLDatabase;
import org.bukkit.Bukkit;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HaraxxSQLiteDatabase extends HaraxxSQLDatabase {
    @Override
    public boolean setupDatabase() {
        // 1. Load SQLite driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage("§cCouldn't find driver for SQLite! Without the driver, SQLite won't work.");
            return false;
        }

        // 2. Connect to local database or create it.
        try {
            String databasePath = HaraxxPlugin.getInstance().getDataFolder().toPath().resolve("db.sqlite").toString();
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cUnable to create or connect to the local SQLite database!");
            return false;
        }

        // 3. Create table (and in the future: add columns older versions might not have)
        try {
            Statement createDefaultTable = connection.createStatement();
            createDefaultTable.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS HARAXX_SYSTEM (Uuid varchar(40) NOT NULL, isTutorial boolean DEFAULT true)"
            );
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cUnable to create table in the database!");
            return false;
        }

        return true;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
