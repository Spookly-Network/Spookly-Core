package de.spookly.events;

import org.jetbrains.annotations.NotNull;

import org.bukkit.event.Event;

/**
 * Interface representing an event executor in the Spookly system.
 */
public interface EventExecuter {

    /**
     * Registers an event subscriber for a specified event class.
     *
     * @param <T>      the type of event.
     * @param aClass   the event class to register.
     * @param listener the event subscriber to register.
     */
    public <T extends Event> void register(Class<T> aClass, @NotNull EventSubscriber<T> listener);

    /**
     * Unregisters an event subscriber.
     *
     * @param <T>      the type of event.
     * @param listener the event subscriber to unregister.
     */
    public <T extends Event> void unregister(@NotNull EventSubscriber<T> listener);
}
