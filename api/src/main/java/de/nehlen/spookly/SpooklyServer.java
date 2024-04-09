package de.nehlen.spookly;

import de.nehlen.spookly.events.EventExecuter;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.team.Team;
import de.nehlen.spookly.team.TeamManager;
import net.kyori.adventure.translation.TranslationRegistry;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public interface SpooklyServer {

    SpooklyOfflinePlayer getOfflinePlayer(@Nonnull String name);
    SpooklyOfflinePlayer getOfflinePlayer(@Nonnull UUID uuid);
    SpooklyPlayer getPlayer(@Nonnull Player player);
    SpooklyPlayer getPlayer(@Nonnull UUID uuid);
    List<SpooklyPlayer> getOnlinePlayers();

    EventExecuter getEventExecuter();
    String getSchemaPrefix();

    GamePhaseManager getGamePhaseManager();
    TeamManager getTeamManager();
    Team.Builder buildTeam();

}
