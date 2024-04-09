package de.nehlen.spookly.team;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Team {
    /**
     * Adds a player to the Team
     * @param player the player that gets added
     */
    void registerPlayer(Player player);

    /**
     * Removes a player from the Team
     * @param player the player that gets removed
     */
    void removePlayer(Player player);

    /**
     * Checks if a player is in this team.
     * @param player the player that will be checked
     * @return true if player is in team
     */
    boolean contains(Player player);

    /**
     * Checks if the amount of player is equal to @link
     * @return Checks if a player is in this team.
     */
    boolean isFull();
    Integer size();
    UUID uuid();
    List<Player> registeredPlayers();
    Integer maxTeamSize();
    void maxTeamSize(Integer maxTeamSize);
    TextColor teamColor();
    void teamColor(TextColor teamColor);
    Map<String, Object> memory();
    Inventory teamInventory();
    void teamInventory(Inventory teamInventory);
    Component teamName();
    void teamName(Component teamName);
    Component prefix();
    void prefix(Component teamName);
    void addToMemory(String key, Object object);
    void removeFromMemory(String key);
    void replaceFromMemory(String key, Object object);

    interface Builder {
        Builder maxTeamSize(final int maxTeamSize);
        Builder teamColor(final TextColor teamColor);
        Builder prefix(final Component prefix);
        Builder teamName(final Component teamName);
        Builder addToMemory(String key, Object object);
        Team build();
    }
}
