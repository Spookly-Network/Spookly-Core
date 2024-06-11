package de.nehlen.spookly.events;

import de.nehlen.spookly.SpooklyCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventExecuterImpl implements EventExecuter {

    private Map<Class<? extends Event>, List<EventSubscriber>> registeredEvents = new HashMap<>();

    @Override
    public <T extends Event> void register(@NotNull Class<T> aClass, @NotNull EventSubscriber<T> listener) {
        if (registeredEvents.containsKey(aClass)) {
            registeredEvents.get(aClass).add(listener);
        } else {
            List<EventSubscriber> subscribers = new ArrayList<>();
            subscribers.add(listener);
            registeredEvents.put(aClass, subscribers);
        }
    }

    @Override
    public <T extends Event> void unregister(@NotNull EventSubscriber<T> listener) {
        List<EventSubscriber> subscribers = registeredEvents.get(listener.getClass().componentType());
        if (subscribers != null) {
            subscribers.remove(listener);
            if (subscribers.isEmpty()) {
                registeredEvents.remove(listener.getClass());
            }
        }
    }

    public void reciveEventAction(Event event) {
        //Executing async is bonkers when event is called synced
        if (!registeredEvents.containsKey(event.getClass())) return;
        registeredEvents
                .get(event.getClass())
                .forEach(entry -> {
                    Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> entry.handle(event));
                    if (SpooklyCorePlugin.getInstance().isDebug())
                        SpooklyCorePlugin.getInstance().getLogger().info("Send Event " + event.getClass().getName() + " to -> " + entry.getClass().getName());
                });
    }
}
