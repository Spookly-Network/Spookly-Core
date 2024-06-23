package de.nehlen.spookly.placeholder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

/**
 * Class representing a placeholder in the Spookly system.
 */
@Getter
@AllArgsConstructor
public class Placeholder {

    /**
     * The key for the placeholder.
     */
    private String key;

    /**
     * The handler for replacing the placeholder.
     */
    private PlaceholderReplaceHandler handler;

    /**
     * The type of the placeholder.
     */
    private PlaceholderContext.PlaceholderType type;

    /**
     * Interface representing a handler for replacing placeholders.
     */
    public interface PlaceholderReplaceHandler {

        /**
         * Replaces the placeholder with the appropriate component based on the context.
         *
         * @param context the context in which the placeholder is being replaced.
         * @return the component to replace the placeholder.
         */
        Component replace(PlaceholderContext context);
    }
}
