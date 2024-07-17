package de.spookly.punishment;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import de.spookly.player.SpooklyOfflinePlayer;

@Getter
public class PunishmentImpl implements Punishment {

    @BsonId @BsonProperty("uuid")
    private final UUID uniqueId;
    @BsonIgnore
    private final UUID playerUniqueId;
    private final PunishmentType type;
    @Setter
    private Instant expiry;
    private final String reason;

    private final UUID creator;
    @BsonProperty("last_updater_uuid") @Setter
    private UUID lastUpdater;
    @BsonProperty("created_at")
    private Instant creationTime;
    @BsonProperty("last_updated_at") @Setter
    private Instant lastUpdate;

    protected PunishmentImpl(SpooklyOfflinePlayer target, SpooklyOfflinePlayer creator, PunishmentType type, Instant expiry, String reason) {
        this.uniqueId = UUID.randomUUID();
        this.playerUniqueId = target.uniqueId();
        this.type = type;
        this.expiry = expiry;
        this.reason = reason;

        this.creator = creator.uniqueId();
        this.lastUpdater = creator.uniqueId();
        this.creationTime = Instant.now();
        this.lastUpdate = Instant.now();
    }

    public PunishmentImpl(UUID uniqueId, UUID playerUniqueId, PunishmentType type, Instant expiry, String reason, UUID creator, UUID lastUpdater, Instant creationTime, Instant lastUpdate) {
        this.uniqueId = uniqueId;
        this.playerUniqueId = playerUniqueId;
        this.type = type;
        this.expiry = expiry;
        this.reason = reason;
        this.creator = creator;
        this.lastUpdater = lastUpdater;
        this.creationTime = creationTime;
        this.lastUpdate = lastUpdate;
    }

    public static Punishment.Builder Builder(SpooklyOfflinePlayer target, SpooklyOfflinePlayer creator) {
        return new Builder(target, creator);
    }

    public static final class Builder implements Punishment.Builder {

        private final SpooklyOfflinePlayer target;
        private final SpooklyOfflinePlayer creator;

        private PunishmentType type;
        private Instant expiry;
        private String reason;

        public Builder(SpooklyOfflinePlayer target, SpooklyOfflinePlayer creator) {
            this.target = target;
            this.creator = creator;
        }

        @Override
        public Builder setType(PunishmentType type) {
            this.type = type;
            return this;
        }

        @Override
        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        @Override
        public Builder setExpiry(Instant expiry) {
            this.expiry = expiry;
            return this;
        }

        @Override
        public Punishment build() {
            return new PunishmentImpl(target,creator,type,expiry,reason);
        }
    }
}
