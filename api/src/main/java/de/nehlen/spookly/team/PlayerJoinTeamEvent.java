package de.nehlen.spookly.team;

import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.player.SpooklyPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
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
