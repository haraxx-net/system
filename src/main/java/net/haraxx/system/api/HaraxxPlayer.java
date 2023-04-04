package net.haraxx.system.api;

import java.util.HashMap;
import java.util.UUID;

public class HaraxxPlayer {
    private static final HashMap<UUID, HaraxxPlayer> playerMap = new HashMap<>();
    private final UUID uuid;
    private boolean isTutorial;
    public HaraxxPlayer(UUID uuid, boolean isTutorial) {
        this.uuid = uuid;
        this.isTutorial = isTutorial;
    }

    public static HashMap<UUID, HaraxxPlayer> getPlayerMap() {
        return playerMap;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isTutorial() {
        return isTutorial;
    }

    public void setTutorial(boolean tutorial) {
        isTutorial = tutorial;
    }
}
