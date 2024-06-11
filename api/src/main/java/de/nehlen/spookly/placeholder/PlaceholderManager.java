package de.nehlen.spookly.placeholder;


import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface PlaceholderManager {

    @Deprecated
    void registerPlaceholder(String placeholder, replaceHandler handler);
    void registerPlaceholder(Placeholder placeholder);
    void removePlaceholder(String placeholder);

    /**
     * @param input Component in which placeholders should be replaced
     * @param player Player in the context
     * @return Component with replaced placeholder
     */
    @Deprecated
    Component replacePlaceholder(Component input, Player player);

    Component replacePlaceholder(Component input, PlaceholderContext context);

    @Deprecated
    public interface replaceHandler {
        Component replace(Player player);
    }

    public interface PlaceholderReplaceHandler {
        Component replace(PlaceholderContext context);
    }
}
