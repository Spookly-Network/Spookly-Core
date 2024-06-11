package de.nehlen.spookly.player;

import de.nehlen.spookly.punishments.PunishReason;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spookly.punishments.PunishmentType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface SpooklyOfflinePlayer {

    UUID uniqueId();
    String name();
    void name(String name);

    Integer points();
    void points(Integer points);
    void addPoints(Integer points);

    void ban(Integer amount, TimeUnit unit, String reason, SpooklyPlayer issuer);

    String textureUrl();
    void textureUrl(String textureUrl);

    Instant firstPlayed();
    Instant lastPlayed();
    void lastPlayed(Instant lastPlayed);
    void addPunishment(Punishment punishment);
}
