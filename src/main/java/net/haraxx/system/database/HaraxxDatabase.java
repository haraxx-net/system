package net.haraxx.system.database;

import net.haraxx.system.api.HaraxxPlayer;

import java.util.Optional;
import java.util.UUID;

public interface HaraxxDatabase {
    boolean setupDatabase();
    void closeConnection();
    Optional<HaraxxPlayer> loadPlayer(UUID uuid);
    Optional<HaraxxPlayer> createPlayer(UUID uuid);
    boolean savePlayer(HaraxxPlayer haraxxPlayer);
}
