package de.nehlen.spookly.player;

import de.nehlen.spookly.punishments.PunishReason;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public interface SpooklyPlayer extends SpooklyOfflinePlayer {

    Player toPlayer();

    Component prefix();
    void prefix(Component prefix);

    void resetNameTag();
    Component nameTag();

    TextColor nameColor();
    void nameColor(TextColor nameColor);

    void kick();
    void kick(PunishReason reason);

    void updatePlayerFile();
}
