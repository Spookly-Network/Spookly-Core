package de.nehlen.spookly.team;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing the manager for teams in the Spookly system.
 */
public interface TeamManager {

    /**
     * Checks if a team is managed by the TeamManager.
     *
     * @param team the team to check.
     * @return true if the team is managed, false otherwise.
     */
    boolean contains(Team team);

    /**
     * Registers a player to a random team.
     *
     * @param player the player to register.
     */
    void registerPlayerRandom(Player player);

    /**
     * Registers a player to the team with the fewest members.
     *
     * @param player the player to register.
     * @return the team to which the player was registered.
     */
    Team registerPlayerToLowestTeam(Player player);

    /**
     * Registers a new team with the TeamManager.
     *
     * @param team the team to register.
     */
    void registerTeam(Team team);

    /**
     * Removes a team from the TeamManager.
     *
     * @param team the team to remove.
     */
    void removeTeam(Team team);

    /**
     * Removes all empty teams from the TeamManager.
     */
    void removeEmptyTeams();

    /**
     * Gets a team by its unique identifier.
     *
     * @param uuid the unique identifier of the team.
     * @return the team with the specified UUID, or null if not found.
     */
    Team team(UUID uuid);

    /**
     * Gets the list of registered teams managed by the TeamManager.
     *
     * @return the list of registered teams.
     */
    List<Team> registeredTeams();

    /**
     * Removes a player from all teams managed by the TeamManager.
     *
     * @param player the player to remove.
     * @return true if the player was removed from any team, false otherwise.
     */
    boolean removePlayerFromTeams(Player player);
}
