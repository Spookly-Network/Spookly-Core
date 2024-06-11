package de.nehlen.spookly.punishments;

import java.time.Instant;
import java.util.UUID;

public interface Punishment {
    UUID getUniqueId();
    UUID getPlayerUniqueId();
    PunishmentType getType();
    String getReason();
    Instant getExpiry();
    UUID getCreator();
    UUID getLastUpdater();
    Instant getCreationTime();
    Instant getLastUpdate();

    void setExpiry(Instant expiry);

    interface Builder {
        Builder setType(PunishmentType type);
        Builder setReason(String reason);
        Builder setExpiry(Instant expiry);
        Punishment build();
    }
}
