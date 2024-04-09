package de.nehlen.spookly.manager;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.player.PlayerRegisterEvent;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spooklycloudnetutils.manager.GroupManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class NametagManager {

    public NametagManager() {
        Spookly.getServer().getEventExecuter().register(PlayerRegisterEvent.class, event -> {
            SpooklyPlayer player = event.getSpooklyPlayer();
            player.resetNameTag();
            refreshNameTag(player);
        });
    }

    public void refreshNameTag(SpooklyPlayer player) {
        refreshListname(player);
        resetScoreboard(player);
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
            spigotPlayer.setScoreboard(manager.getNewScoreboard());
        }
    }

    private void registerPlayerToTeam(SpooklyPlayer player, SpooklyPlayer onlinePlayer) {
        if (player == onlinePlayer) return;
        Component prefix = onlinePlayer.prefix();
        List<Team> prefixTeam = player.toPlayer().getScoreboard().getTeams().stream().filter(t -> t.prefix().equals(prefix)).collect(Collectors.toList());

        if (prefixTeam.isEmpty()) {
            String generatedName = UUID.randomUUID() + "_" + getNameFromInput(GroupManager.getGroupSortId(onlinePlayer.toPlayer()));
            Team team = player.toPlayer().getScoreboard().registerNewTeam(generatedName);
            team.color(NamedTextColor.nearestTo(player.nameColor()));
            team.prefix(prefix);
            team.addEntries(onlinePlayer.toPlayer().getName());
        } else {
            Team team = prefixTeam.get(0);
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
        for (int i = 0; i < repeat; i++) {
            builder.append(letter);
        }
        return builder.toString();
    }

}
