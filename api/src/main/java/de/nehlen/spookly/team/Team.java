package de.nehlen.spookly.team;

import de.nehlen.spookly.team.display.TeamDisplay;
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
    boolean removePlayer(Player player);

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
    @Deprecated
    TextColor teamColor();
    @Deprecated
    void teamColor(TextColor teamColor);
    Map<String, Object> memory();
    Inventory teamInventory();
    void teamInventory(Inventory teamInventory);
    Component teamName();
    void teamName(Component teamName);
    @Deprecated
    Component prefix();
    @Deprecated
    void prefix(Component teamName);
    void addToMemory(String key, Object object);
    void removeFromMemory(String key);
    void replaceFromMemory(String key, Object object);
    Integer tabSortId();

    void setTeamDisplay(TeamDisplay display);
    TeamDisplay getTeamDisplay();

    interface Builder {
        Builder maxTeamSize(final int maxTeamSize);
        @Deprecated
        Builder teamColor(final TextColor teamColor);
        @Deprecated
        Builder prefix(final Component prefix);
        Builder display(final TeamDisplay display);
        Builder teamName(final Component teamName);
        Builder addToMemory(String key, Object object);
        Builder tabSortId(final int tabSortId);
        Team build();
    }
}
