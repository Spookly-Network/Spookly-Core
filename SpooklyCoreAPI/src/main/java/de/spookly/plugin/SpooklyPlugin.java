package de.spookly.plugin;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class SpooklyPlugin extends JavaPlugin {

    private PluginManager pluginManager;
    @Getter private BukkitScheduler scheduler;

    public SpooklyPlugin() {
    }

    protected abstract void load();

    protected abstract void enable();

    protected abstract void disable();

    protected abstract void postStartup();

    public final void onLoad() {
        this.pluginManager = getServer().getPluginManager();
        this.scheduler = Bukkit.getScheduler();
        this.load();
    }

    public final void onEnable() {
        this.enable();
        scheduler.runTaskLater(this, this::postStartup, 1L);
    }

    public <T extends Listener> void registerEvent(T listener) {
        pluginManager.registerEvents(listener, this);
    }

    public <T extends CommandExecutor & TabCompleter> void registerCommand(String cmd, T handler) {
        PluginCommand command = this.getCommand(cmd);
        if (command != null) {
            command.setExecutor(handler);
            command.setTabCompleter((TabCompleter)handler);
        }
    }

    public <T extends CommandExecutor> void registerCommandOnly(String cmd, T handler) {
        PluginCommand command = this.getCommand(cmd);
        if (command != null) {
            command.setExecutor(handler);
        }
    }

    public boolean isPluginPresent(@Nonnull String name) {
        return this.pluginManager.getPlugin(name) != null;
    }
}
