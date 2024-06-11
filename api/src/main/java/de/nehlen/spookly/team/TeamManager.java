package de.nehlen.spookly.team;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface TeamManager {
    boolean contains(Team team);
    void registerPlayerRandom(Player player);
    Team registerPlayerToLowestTeam(Player player);
    void registerTeam(Team team);
    void removeTeam(Team team);
    void removeEmptyTeams();
    Team team(UUID uuid);
    List<Team> registeredTeams();
    boolean removePlayerFromTeams(Player player);
}
