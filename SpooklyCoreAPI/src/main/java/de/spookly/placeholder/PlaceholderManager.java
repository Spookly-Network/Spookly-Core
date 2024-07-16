package de.spookly.placeholder;

import net.kyori.adventure.text.Component;

import org.bukkit.entity.Player;

/**
 * Interface representing the manager for placeholders in the Spookly system.
 */
public interface PlaceholderManager {

    /**
     * Registers a placeholder with a handler.
     *
     * @param placeholder the placeholder string.
     * @param handler     the handler for replacing the placeholder.
     * @deprecated Use {@link #registerPlaceholder(Placeholder)} instead.
     */
    @Deprecated
    void registerPlaceholder(String placeholder, replaceHandler handler);

    /**
     * Registers a placeholder.
     *
     * @param placeholder the placeholder to register.
     */
    void registerPlaceholder(Placeholder placeholder);

    /**
     * Removes a placeholder.
     *
     * @param placeholder the placeholder string to remove.
     */
    void removePlaceholder(String placeholder);

    /**
     * Replaces placeholders in the input component with values based on the player context.
     *
     * @param input  the input component.
     * @param player the player context.
     * @return the component with replaced placeholders.
     * @deprecated Use {@link #replacePlaceholder(Component, PlaceholderContext)} instead.
     */
    @Deprecated
    Component replacePlaceholder(Component input, Player player);

    /**
     * Replaces placeholders in the input component with values based on the context.
     *
     * @param input   the input component.
     * @param context the placeholder context.
     * @return the component with replaced placeholders.
     */
    Component replacePlaceholder(Component input, PlaceholderContext context);

    /**
     * Interface representing a handler for replacing placeholders based on a player context.
     *
     * @deprecated Use {@link Placeholder.PlaceholderReplaceHandler} instead.
     */
    @Deprecated
    public interface replaceHandler {

        /**
         * Replaces the placeholder with the appropriate component based on the player context.
         *
         * @param player the player context.
         * @return the component to replace the placeholder.
         */
        Component replace(Player player);
    }
}
