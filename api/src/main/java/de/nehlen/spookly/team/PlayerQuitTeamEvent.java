package de.nehlen.spookly.team;

import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.player.SpooklyPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerQuitTeamEvent extends SpooklyPlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    @Getter
    private Team team;

    public PlayerQuitTeamEvent(SpooklyPlayer player, Team team) {
        super(player);
        this.team = team;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList()
    {
        return handlers;
    }

}
