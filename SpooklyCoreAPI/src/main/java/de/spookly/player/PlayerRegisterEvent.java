package de.spookly.player;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerRegisterEvent extends SpooklyPlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerRegisterEvent(@NotNull final SpooklyPlayer spooklyPlayer) {
        super(spooklyPlayer);
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
