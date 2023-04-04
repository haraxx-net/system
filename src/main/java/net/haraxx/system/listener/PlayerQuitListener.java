package net.haraxx.system.listener;

import net.haraxx.system.HaraxxPlugin;
import net.haraxx.system.api.HaraxxPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent quitEvent) {
        Bukkit.getScheduler().runTaskAsynchronously(HaraxxPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                HaraxxPlugin.getDatabase().savePlayer(HaraxxPlayer.getPlayerMap().get(quitEvent.getPlayer().getUniqueId()));
                HaraxxPlayer.getPlayerMap().remove(quitEvent.getPlayer().getUniqueId());
            }
        });
    }
}
