package net.haraxx.system;

import net.haraxx.system.api.HaraxxPlayer;
import net.haraxx.system.database.HaraxxDatabase;
import net.haraxx.system.database.sql.HaraxxSQLiteDatabase;
import net.haraxx.system.listener.PlayerJoinListener;
import net.haraxx.system.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class HaraxxPlugin extends JavaPlugin {
    private static HaraxxDatabase DATABASE;
    private static HaraxxPlugin INSTANCE;

    public static HaraxxDatabase getDatabase() {
        return DATABASE;
    }

    public static HaraxxPlugin getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        DATABASE = new HaraxxSQLiteDatabase();
        DATABASE.setupDatabase();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "Haraxx System has started!");

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);

        // To fix reload behaviour: Players are online, but don't have a HaraxxPlayer Object due to missing Join Event
        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Optional<HaraxxPlayer> optionalHaraxxPlayer = DATABASE.loadPlayer(player.getUniqueId());
                    if (optionalHaraxxPlayer.isPresent())
                        HaraxxPlayer.getPlayerMap().put(player.getUniqueId(), optionalHaraxxPlayer.get());
                }
            }
        });
    }

    @Override
    public void onDisable() {
        DATABASE.closeConnection();
    }
}
