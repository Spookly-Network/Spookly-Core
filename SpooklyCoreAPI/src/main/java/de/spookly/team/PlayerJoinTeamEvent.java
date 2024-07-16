package de.spookly.team;

import lombok.Getter;

import de.spookly.player.SpooklyPlayer;
import de.spookly.player.SpooklyPlayerEvent;

import org.bukkit.event.HandlerList;

public class PlayerJoinTeamEvent extends SpooklyPlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    @Getter private Team team;

    public PlayerJoinTeamEvent(SpooklyPlayer player, Team team) {
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
