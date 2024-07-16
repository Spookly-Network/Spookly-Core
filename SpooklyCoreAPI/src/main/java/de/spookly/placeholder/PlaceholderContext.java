package de.spookly.placeholder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * Class representing the context for placeholders in the Spookly system.
 */
@Getter
@AllArgsConstructor
public class PlaceholderContext {

    private Player player;
    private PlaceholderType type;

    /**
     * Enum representing the types of placeholders.
     */
    public enum PlaceholderType {
        /**
         * Placeholder for chat.
         */
        CHAT,

        /**
         * Placeholder for scoreboard.
         */
        SCOREBOARD,

        /**
         * Placeholder for tab list.
         */
        TAB,

        /**
         * Placeholder for boss bar.
         */
        BOSSBAR,

        /**
         * Placeholder for all types.
         */
        ALL
    }
}
