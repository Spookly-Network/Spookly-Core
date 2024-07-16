package de.spookly.player;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class SpooklyPlayerEvent extends Event {
    protected SpooklyPlayer player;

    public SpooklyPlayerEvent(@NotNull final SpooklyPlayer who) {
        player = who;
    }

    public SpooklyPlayerEvent(@NotNull final SpooklyPlayer who, boolean async) {
        super(async);
        player = who;
    }

    /**
     * Returns the spookly-player involved in this event
     *
     * @return SpooklyPlayer who is involved in this event
     */
    @NotNull
    public final SpooklyPlayer getSpooklyPlayer() {
        return player;
    }
}
