package de.spookly.player;

import java.util.List;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import de.spookly.punishments.PunishReason;
import de.spookly.punishments.Punishment;

import org.bukkit.entity.Player;

/**
 * Interface representing an online player in the Spookly system, extending SpooklyOfflinePlayer.
 */
public interface SpooklyPlayer extends SpooklyOfflinePlayer {

    /**
     * Converts the SpooklyPlayer to a Bukkit Player object.
     *
     * @return the Player object.
     */
    Player toPlayer();

    /**
     * Gets the prefix of the player.
     *
     * @return the prefix component.
     */
    Component prefix();

    /**
     * Sets the prefix of the player.
     *
     * @param prefix the new prefix component.
     */
    void prefix(Component prefix);

    /**
     * Sets the prefix of the player with a sorting ID.
     *
     * @param prefix the new prefix component.
     * @param sortId the sorting ID for the prefix.
     */
    void prefix(Component prefix, Integer sortId);

    /**
     * Gets the chat renderer for the player.
     *
     * @return the chat renderer.
     */
    ChatRenderer getChatRenderer();

    /**
     * Gets the tab sorting ID of the player.
     *
     * @return the tab sorting ID.
     */
    Integer tabSortId();

    /**
     * Sets the active punishments for the player.
     *
     * @param punishments the list of active punishments.
     */
    void activePunishments(List<Punishment> punishments);

    /**
     * Gets the list of active punishments for the player.
     *
     * @return the list of active punishments.
     */
    List<Punishment> activePunishments();

    /**
     * Resets the name tag of the player to the default.
     */
    void resetNameTag();

    /**
     * Gets the name tag component of the player.
     *
     * @return the name tag component.
     */
    Component nameTag();

    /**
     * Gets the name color of the player.
     *
     * @return the name color.
     */
    TextColor nameColor();

    /**
     * Sets the name color of the player.
     *
     * @param nameColor the new name color.
     */
    void nameColor(TextColor nameColor);

    /**
     * Kicks the player from the server.
     */
    void kick();

    /**
     * Kicks the player from the server with a specified reason.
     *
     * @param reason the reason for the kick.
     */
    void kick(PunishReason reason);

    /**
     * Updates the player file.
     */
    void updatePlayerFile();

    /**
     * Refreshes the name tag of the player.
     */
    void refreshNameTag();
}
