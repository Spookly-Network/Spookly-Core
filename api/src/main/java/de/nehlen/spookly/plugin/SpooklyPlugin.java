package de.nehlen.spookly.plugin;

import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

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

    public boolean isPluginPresent(@Nonnull String name) {
        return this.getServer().getPluginManager().getPlugin(name) != null;
    }
}
