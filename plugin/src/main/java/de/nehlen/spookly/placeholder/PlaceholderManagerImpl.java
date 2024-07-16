package de.nehlen.spookly.placeholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

public class PlaceholderManagerImpl implements PlaceholderManager {

    @Deprecated private Map<String, replaceHandler> placeholders = new HashMap<>();
    private List<Placeholder> placeholderList = new ArrayList<>();

    @Override
    public void registerPlaceholder(String placeholder, replaceHandler value) {
        placeholders.put(placeholder, value);
    }

    @Override
    public void registerPlaceholder(Placeholder placeholder) {
        placeholderList.add(placeholder);
    }

    @Override
    public void removePlaceholder(String placeholder) {
        placeholders.remove(placeholder);
    }

    @Override
    @Deprecated(forRemoval = true)
    public Component replacePlaceholder(Component input, Player player) {
        for (Map.Entry<String, replaceHandler> entry : placeholders.entrySet()) {
            input = input.replaceText(replace(entry.getKey(), entry.getValue().replace(player)));
        }
        return input;
    }

    @Override //TODO wip
    public Component replacePlaceholder(Component input, PlaceholderContext context) {
        List<Placeholder> active = new ArrayList<>();

        if (context.getType() == PlaceholderContext.PlaceholderType.ALL) {
            active.addAll(placeholderList);
        } else {
            active.addAll(placeholderList.stream().filter(placeholder -> placeholder.getType() == context.getType()).toList());
        }

        for (Placeholder entry : active) {
            input = input.replaceText(replace(entry.getKey(), entry.getHandler().replace(context)));
        }
        return input;
    }

    private TextReplacementConfig replace(String literal, Component component) {
        return TextReplacementConfig.builder()
                .matchLiteral(literal)
                .replacement(component)
                .build();
    }
}