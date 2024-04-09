package de.nehlen.spookly.events;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import org.bukkit.event.Event;

import java.lang.reflect.Type;

public interface EventSubscriber<T extends Event> {

     void handle(T event);

}
