package de.nehlen.spookly.punishments;

import java.time.Instant;
import java.util.UUID;

/**
 * Interface representing a punishment in the Spookly system.
 */
public interface Punishment {

    /**
     * Gets the unique identifier of the punishment.
     *
     * @return the unique identifier of the punishment.
     */
    UUID getUniqueId();

    /**
     * Gets the unique identifier of the player being punished.
     *
     * @return the unique identifier of the player.
     */
    UUID getPlayerUniqueId();

    /**
     * Gets the type of the punishment.
     *
     * @return the punishment type.
     */
    PunishmentType getType();

    /**
     * Gets the reason for the punishment.
     *
     * @return the reason for the punishment.
     */
    String getReason();

    /**
     * Gets the expiry time of the punishment.
     *
     * @return the expiry time.
     */
    Instant getExpiry();

    /**
     * Gets the unique identifier of the creator of the punishment.
     *
     * @return the unique identifier of the creator.
     */
    UUID getCreator();

    /**
     * Gets the unique identifier of the last person who updated the punishment.
     *
     * @return the unique identifier of the last updater.
     */
    UUID getLastUpdater();

    /**
     * Gets the creation time of the punishment.
     *
     * @return the creation time.
     */
    Instant getCreationTime();

    /**
     * Gets the last update time of the punishment.
     *
     * @return the last update time.
     */
    Instant getLastUpdate();

    /**
     * Sets the expiry time of the punishment.
     *
     * @param expiry the new expiry time.
     */
    void setExpiry(Instant expiry);

    /**
     * Builder interface for creating instances of {@link Punishment}.
     */
    interface Builder {
        /**
         * Sets the type of the punishment.
         *
         * @param type the punishment type.
         * @return the builder instance.
         */
        Builder setType(PunishmentType type);

        /**
         * Sets the reason for the punishment.
         *
         * @param reason the reason for the punishment.
         * @return the builder instance.
         */
        Builder setReason(String reason);

        /**
         * Sets the expiry time of the punishment.
         *
         * @param expiry the expiry time.
         * @return the builder instance.
         */
        Builder setExpiry(Instant expiry);

        /**
         * Builds the punishment instance.
         *
         * @return the created punishment.
         */
        Punishment build();
    }
}