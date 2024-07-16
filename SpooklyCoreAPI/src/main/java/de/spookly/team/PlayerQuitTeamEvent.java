package de.spookly.team;

import lombok.Getter;

import de.spookly.player.SpooklyPlayer;
import de.spookly.player.SpooklyPlayerEvent;

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
