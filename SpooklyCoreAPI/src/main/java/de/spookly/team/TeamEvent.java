package de.spookly.team;

import org.jetbrains.annotations.NotNull;

import org.bukkit.event.Event;

public abstract class TeamEvent extends Event {
    protected Team team;

    public TeamEvent(@NotNull final Team who) {
        team = who;
    }

    public TeamEvent(@NotNull final Team who, boolean async) {
        super(async);
        team = who;
    }

    /**
     * Returns the team involved in this event
     *
     * @return Team who is involved in this event
     */
    @NotNull
    public final Team getTeam() {
        return team;
    }
}
