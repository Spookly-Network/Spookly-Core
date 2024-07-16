package de.spookly.team;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import de.spookly.team.display.TeamDisplay;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Interface representing a team in the Spookly system.
 */
public interface Team {

    /**
     * Adds a player to the team.
     *
     * @param player the player to add.
     */
    void registerPlayer(Player player);

    /**
     * Removes a player from the team.
     *
     * @param player the player to remove.
     * @return true if the player was successfully removed, false otherwise.
     */
    boolean removePlayer(Player player);

    /**
     * Checks if a player is in the team.
     *
     * @param player the player to check.
     * @return true if the player is in the team, false otherwise.
     */
    boolean contains(Player player);

    /**
     * Checks if the team is full.
     *
     * @return true if the team is full, false otherwise.
     */
    boolean isFull();

    /**
     * Gets the current size of the team.
     *
     * @return the size of the team.
     */
    Integer size();

    /**
     * Gets the unique identifier of the team.
     *
     * @return the unique identifier of the team.
     */
    UUID uuid();

    /**
     * Gets the list of registered players in the team.
     *
     * @return the list of registered players.
     */
    List<Player> registeredPlayers();

    /**
     * Gets the maximum size of the team.
     *
     * @return the maximum size of the team.
     */
    Integer maxTeamSize();

    /**
     * Sets the maximum size of the team.
     *
     * @param maxTeamSize the new maximum size of the team.
     */
    void maxTeamSize(Integer maxTeamSize);

    /**
     * Gets the color of the team.
     *
     * @return the color of the team.
     * @deprecated use {@link #getTeamDisplay()} instead
     */
    @Deprecated
    TextColor teamColor();

    /**
     * Sets the color of the team.
     *
     * @param teamColor the new color of the team.
     * @deprecated use {@link #setTeamDisplay(TeamDisplay)} instead
     */
    @Deprecated
    void teamColor(TextColor teamColor);

    /**
     * Gets the team's memory storage.
     *
     * @return the memory storage.
     */
    Map<String, Object> memory();

    /**
     * Gets the team's inventory.
     *
     * @return the team's inventory.
     */
    Inventory teamInventory();

    /**
     * Sets the team's inventory.
     *
     * @param teamInventory the new team inventory.
     */
    void teamInventory(Inventory teamInventory);

    /**
     * Gets the name of the team.
     *
     * @return the name of the team.
     */
    Component teamName();

    /**
     * Sets the name of the team.
     *
     * @param teamName the new team name.
     */
    void teamName(Component teamName);

    /**
     * Gets the prefix of the team.
     *
     * @return the prefix of the team.
     * @deprecated use {@link #getTeamDisplay()} instead
     */
    @Deprecated
    Component prefix();

    /**
     * Sets the prefix of the team.
     *
     * @param prefix the new prefix of the team.
     * @deprecated use {@link #setTeamDisplay(TeamDisplay)} instead
     */
    @Deprecated
    void prefix(Component prefix);

    /**
     * Adds an object to the team's memory storage.
     *
     * @param key    the key for the object.
     * @param object the object to add.
     */
    void addToMemory(String key, Object object);

    /**
     * Removes an object from the team's memory storage.
     *
     * @param key the key for the object to remove.
     */
    void removeFromMemory(String key);

    /**
     * Replaces an object in the team's memory storage.
     *
     * @param key    the key for the object.
     * @param object the new object to replace the old one.
     */
    void replaceFromMemory(String key, Object object);

    /**
     * Gets the tab sorting ID of the team.
     *
     * @return the tab sorting ID.
     */
    Integer tabSortId();

    /**
     * Sets the team's display properties.
     *
     * @param display the new display properties.
     */
    void setTeamDisplay(TeamDisplay display);

    /**
     * Gets the team's display properties.
     *
     * @return the display properties.
     */
    TeamDisplay getTeamDisplay();

    /**
     * Builder interface for creating instances of {@link Team}.
     */
    interface Builder {

        /**
         * Sets the maximum size of the team.
         *
         * @param maxTeamSize the maximum size of the team.
         * @return the builder instance.
         */
        Builder maxTeamSize(final int maxTeamSize);

        /**
         * Sets the color of the team.
         *
         * @param teamColor the color of the team.
         * @return the builder instance.
         * @deprecated use {@link #display(TeamDisplay)} instead
         */
        @Deprecated
        Builder teamColor(final TextColor teamColor);

        /**
         * Sets the prefix of the team.
         *
         * @param prefix the prefix of the team.
         * @return the builder instance.
         * @deprecated use {@link #display(TeamDisplay)} instead
         */
        @Deprecated
        Builder prefix(final Component prefix);

        /**
         * Sets the display properties of the team.
         *
         * @param display the display properties of the team.
         * @return the builder instance.
         */
        Builder display(final TeamDisplay display);

        /**
         * Sets the name of the team.
         *
         * @param teamName the name of the team.
         * @return the builder instance.
         */
        Builder teamName(final Component teamName);

        /**
         * Adds an object to the team's memory storage.
         *
         * @param key    the key for the object.
         * @param object the object to add.
         * @return the builder instance.
         */
        Builder addToMemory(String key, Object object);

        /**
         * Sets the tab sorting ID of the team.
         *
         * @param tabSortId the tab sorting ID.
         * @return the builder instance.
         */
        Builder tabSortId(final int tabSortId);

        /**
         * Builds the team instance.
         *
         * @return the created team.
         */
        Team build();
    }
}
