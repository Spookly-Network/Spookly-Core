package de.nehlen.spookly.placeholder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter @AllArgsConstructor
public class PlaceholderContext {
    private Player player;
    private PlaceholderType type;

    public enum PlaceholderType {
        CHAT, SCOREBOARD, TAB, BOSSBAR, ALL
    }
}
