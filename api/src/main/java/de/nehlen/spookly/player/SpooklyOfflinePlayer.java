package de.nehlen.spookly.player;

import de.nehlen.spookly.punishments.PunishReason;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

public interface SpooklyOfflinePlayer {

    UUID uniqueId();
    String name();
    void name(String name);

    Integer points();
    void points(Integer points);
    void addPoints(Integer points);

    void ban();
    void ban(PunishReason reason);

    String textureUrl();
    void textureUrl(String textureUrl);

    Instant firstPlayed();
    Instant lastPlayed();
    void lastPlayed(Instant lastPlayed);
}
