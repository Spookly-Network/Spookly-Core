package de.spookly.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import lombok.Getter;
import lombok.experimental.Accessors;

import de.spookly.SpooklyCorePlugin;
import de.spookly.team.Team;
import de.spookly.team.TeamCreateEvent;
import de.spookly.team.TeamDestroyEvent;
import de.spookly.team.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
@Accessors(fluent = true, chain = false)
public class TeamManagerImpl implements TeamManager {

    protected List<Team> registeredTeams = new ArrayList<Team>();

    @Override
    public boolean contains(Team team) {
        return registeredTeams.contains(team);
    }

    @Override
    public void registerPlayerRandom(Player player) {
        Random r = new Random();
        int i = r.nextInt(registeredTeams().size());
        for (Team team : registeredTeams()) {
            if (team.contains(player))
                team.removePlayer(player);
        }
        if (!registeredTeams().get(i).isFull()) {
            registeredTeams().get(i).registerPlayer(player);
        } else {
            registerPlayerRandom(player);
        }
    }

    @Override
    public Team registerPlayerToLowestTeam(Player player) {
        Team lowest = this.registeredTeams().getFirst();
        if (this.registeredTeams().size() > 1) {
            for (int i = 1; i != this.registeredTeams().size(); i++) {
                if (this.registeredTeams().get(i).registeredPlayers().size() < lowest
                        .registeredPlayers().size()) {
                    lowest = this.registeredTeams().get(i);
                }
            }
            lowest.registerPlayer(player);
            return lowest;
        }
        return null;
    }

    @Override
    public void registerTeam(Team team) {
        TeamCreateEvent teamCreateEvent = new TeamCreateEvent(team);
        if (!this.registeredTeams.contains(team)) {

            Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> Bukkit.getPluginManager().callEvent(teamCreateEvent));
            if (!teamCreateEvent.isCancelled()) {
                this.registeredTeams.add(team);
            }
        }
    }

    @Override
    public void removeTeam(Team team) {
        TeamDestroyEvent teamDestroyEventEvent = new TeamDestroyEvent(team);
        Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> Bukkit.getPluginManager().callEvent(teamDestroyEventEvent));
        if (!teamDestroyEventEvent.isCancelled())
            this.registeredTeams.remove(team);
    }

    @Override
    public void removeEmptyTeams() {
        List<Team> toRemove = new ArrayList<>();
        this.registeredTeams().forEach(team -> {
            if (team.registeredPlayers().isEmpty()) {
                Bukkit.getConsoleSender().sendMessage("" + team.registeredPlayers().size());
                TeamDestroyEvent teamDestroyEventEvent = new TeamDestroyEvent(team);
                Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> Bukkit.getPluginManager().callEvent(teamDestroyEventEvent));
                if(!teamDestroyEventEvent.isCancelled()) {
                    toRemove.add(team);
                }
            }
        });
        this.registeredTeams.removeAll(toRemove);
    }

    @Override
    public boolean removePlayerFromTeams(Player player) {
        this.registeredTeams().stream().filter(team -> team.contains(player)).forEach(team -> {
            team.removePlayer(player);

        });
        return true;
    }

    @Override
    public Team team(UUID uuid) {
        return registeredTeams.stream().filter(team -> team.uuid().equals(uuid)).toList().get(0);
    }
}
