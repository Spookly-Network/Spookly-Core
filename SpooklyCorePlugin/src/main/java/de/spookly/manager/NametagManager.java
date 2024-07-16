package de.spookly.manager;

import java.util.List;
import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import de.spookly.Spookly;
import de.spookly.SpooklyCorePlugin;
import de.spookly.player.PlayerRegisterEvent;
import de.spookly.player.SpooklyPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class NametagManager {

    public NametagManager() {
        Spookly.getServer().getEventExecuter().register(PlayerRegisterEvent.class, event -> {
            refreshNameTag(event.getSpooklyPlayer());
        });
    }

    public void refreshNameTag(SpooklyPlayer player) {
        resetScoreboard(player);
        refreshListname(player);
        Spookly.getOnlinePlayers().forEach(spooklyPlayer -> {
            resetScoreboard(spooklyPlayer);
            registerPlayerToTeam(player, spooklyPlayer);
            registerPlayerToTeam(spooklyPlayer, player);
        });
    }

    public void refreshListname(SpooklyPlayer player) {
        player.toPlayer().playerListName(player.nameTag());
    }

    private void resetScoreboard(SpooklyPlayer player) {
        Player spigotPlayer = player.toPlayer();
        ScoreboardManager manager = spigotPlayer.getServer().getScoreboardManager();
        if (spigotPlayer.getScoreboard().equals(manager.getMainScoreboard())) {
            Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> spigotPlayer.setScoreboard(manager.getNewScoreboard()));
        }
    }

    private void registerPlayerToTeam(SpooklyPlayer scoreboardHolder, SpooklyPlayer onlinePlayer) {
        Component prefix = onlinePlayer.prefix();
        List<Team> prefixTeam = scoreboardHolder.toPlayer().getScoreboard().getTeams().stream().filter(t -> t.prefix().compact().contains(prefix)).toList();

        if (prefixTeam.isEmpty()) {
            String generatedName = getNameFromInput(onlinePlayer.tabSortId()) + "_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Team team = scoreboardHolder.toPlayer().getScoreboard().registerNewTeam(generatedName);
            team.color(NamedTextColor.nearestTo(onlinePlayer.nameColor()));
            team.prefix(prefix);
            team.addEntries(onlinePlayer.toPlayer().getName());
        } else {
            Team team = prefixTeam.getFirst();
            team.addEntries(onlinePlayer.toPlayer().getName());
        }
    }

    /**
     * This is a special method to sort nametags in
     * the tablist. It takes a priority and converts
     * it to an alphabetic representation to force a
     * specific sort.
     *
     * @param input the sort priority
     * @return the team name
     */
    private String getNameFromInput(int input) {
        if (input < 0) return "Z";
        char letter = (char) ((input / 5) + 65);
        int repeat = input % 5 + 1;
        StringBuilder builder = new StringBuilder();

        int i = 0;
        do {
            if(i < repeat)
                builder.append(letter);
            else
                builder.append(0);
            i++;
        } while (builder.length() < 5);
        return builder.toString();
    }
}
