package de.nehlen.spookly.player;

import de.nehlen.spookly.punishments.PunishReason;
import de.nehlen.spookly.punishments.Punishment;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.List;

public interface SpooklyPlayer extends SpooklyOfflinePlayer {

    Player toPlayer();

    Component prefix();
    void prefix(Component prefix);
    void prefix(Component prefix, Integer sortId);
    Integer tabSortId();

    void activePunishments(List<Punishment> punishments);
    List<Punishment> activePunishments();

    void resetNameTag();
    Component nameTag();

    TextColor nameColor();
    void nameColor(TextColor nameColor);

    void kick();
    void kick(PunishReason reason);

    void updatePlayerFile();
    void refreshNameTag();
}
