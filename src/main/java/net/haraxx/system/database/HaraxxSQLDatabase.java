package net.haraxx.system.database;

import net.haraxx.system.api.HaraxxPlayer;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class HaraxxSQLDatabase implements HaraxxDatabase {
    /*
    Use specific database implementation instead, e.g. HaraxxSQLiteDatabase for SQLite
     */

    protected Connection connection;

    @Override
    public boolean setupDatabase() {
        return false;
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public Optional<HaraxxPlayer> loadPlayer(UUID uuid) {
        ResultSet matchedUuids;
        try {
            PreparedStatement selectUuid = connection.prepareStatement(
                    "SELECT isTutorial FROM HARAXX_SYSTEM WHERE Uuid=?"
            );
            selectUuid.setString(1, uuid.toString());
            matchedUuids = selectUuid.executeQuery();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(
                    "§cCouldn't execute the database query! This is a critical problem, please check if the credentials are correct and the database is online."
            );
            return Optional.empty();
        }

        try {
            while (matchedUuids.next()) {
                boolean isTutorial = matchedUuids.getBoolean("isTutorial");

                return Optional.of(new HaraxxPlayer(uuid, isTutorial));
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cError while accessing player data of " + Bukkit.getPlayer(uuid).displayName());
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public Optional<HaraxxPlayer> createPlayer(UUID uuid) {

        try {
            PreparedStatement createStatement = connection.prepareStatement(
                    "INSERT INTO HARAXX_SYSTEM (Uuid) VALUES (?)"
            );
            createStatement.setString(1, uuid.toString());
            createStatement.execute();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(
                    "§cCouldn't execute the database query! This is a critical problem, please check if the credentials are correct and the database is online."
            );
            return Optional.empty();
        }
        return Optional.of(new HaraxxPlayer(uuid, true));
    }

    @Override
    public boolean savePlayer(HaraxxPlayer haraxxPlayer) {
        try {
            PreparedStatement insertData = connection.prepareStatement(
                    "UPDATE HARAXX_SYSTEM SET isTutorial = ? WHERE Uuid=?"
            );
            insertData.setBoolean(1, haraxxPlayer.isTutorial());
            insertData.setString(2, haraxxPlayer.getUuid().toString());
            insertData.execute();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(
                    "§cCouldn't execute the database query! This is a critical problem, please check if the credentials are correct and the database is online."
            );
            e.printStackTrace();
        }

        return false;
    }
}
