package de.nehlen.spookly.events;

import com.google.common.reflect.TypeToken;
import de.nehlen.spookly.SpooklyCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EventExecuterImpl implements EventExecuter {

    private Map<Class<? extends Event>, EventSubscriber> registeredEvents = new HashMap<>();

    @Override
    public <T extends Event> void register(Class<T> aClass, @NotNull EventSubscriber<T> listener) {
        registeredEvents.put(aClass, listener);
    }

    @Override
    public <T extends Event> void unregister(@NotNull EventSubscriber<T> listener) {
        registeredEvents.values().remove(listener);
    }

    public void reciveEventAction(Event event) {
        if (Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTaskAsynchronously(SpooklyCorePlugin.getInstance(), () -> {
                registeredEvents.entrySet().stream()
                        .filter(entry -> entry.getKey().isInstance(event))
                        .forEach(entry -> {
                            Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> entry.getValue().handle(event));
                        });
            });
        } else {
            registeredEvents.entrySet().stream()
                    .filter(entry -> entry.getKey().isInstance(event))
                    .forEach(entry -> {
                        Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> entry.getValue().handle(event));
                    });
        }
    }
}
