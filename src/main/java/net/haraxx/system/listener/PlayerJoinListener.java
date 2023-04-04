package net.haraxx.system.listener;

import net.haraxx.system.HaraxxPlugin;
import net.haraxx.system.api.HaraxxPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;
import java.util.UUID;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        UUID playerUuid = player.getUniqueId();

        Bukkit.getScheduler().runTaskAsynchronously(HaraxxPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                Optional<HaraxxPlayer> haraxxPlayerOptional = HaraxxPlugin.getDatabase().loadPlayer(playerUuid);
                if (haraxxPlayerOptional.isPresent()) {
                    HaraxxPlayer.getPlayerMap().put(playerUuid, haraxxPlayerOptional.get());
                } else {
                    HaraxxPlugin.getDatabase().createPlayer(playerUuid);
                }

                Bukkit.getConsoleSender().sendMessage(HaraxxPlayer.getPlayerMap().toString());
            }
        });
    }
}
