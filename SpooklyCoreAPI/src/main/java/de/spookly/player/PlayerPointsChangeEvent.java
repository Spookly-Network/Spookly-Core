package de.spookly.player;

import lombok.Getter;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerPointsChangeEvent extends SpooklyPlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    @Getter private final Integer pointsBefore;
    private final Integer pointsAfter;

    public PlayerPointsChangeEvent(SpooklyPlayer player, Integer pointsBefore, Integer pointsAfter) {
        super(player);
        this.pointsBefore = pointsBefore;
        this.pointsAfter = pointsAfter;
    }

    public Integer getPoints() {
        return this.pointsAfter;
    }

    public Integer getPointsChange() {
        return this.pointsAfter - this.pointsBefore;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
