package de.spookly.event;

import org.bukkit.event.Event;

/**
 * Interface representing an event subscriber in the Spookly system.
 *
 * @param <T> the type of event this subscriber handles.
 */
public interface EventSubscriber<T extends Event> {

    /**
    * Handles the specified event.
    *
    * @param event the event to handle.
    */
    void handle(T event);
}
