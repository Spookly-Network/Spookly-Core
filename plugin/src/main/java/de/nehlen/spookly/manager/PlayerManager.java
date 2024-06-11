package de.nehlen.spookly.manager;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.player.PlayerRegisterEvent;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spookly.punishments.PunishmentType;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlayerManager {

    private final SpooklyCorePlugin plugin;
    @Getter
    public Map<UUID, SpooklyPlayer> registeredOnlinePlayers = new HashMap<>();
    public Map<UUID, List<Punishment>> punishmentCache = new HashMap<>();

    public PlayerManager(SpooklyCorePlugin plugin) {
        this.plugin = plugin;

        Spookly.getServer().getEventExecuter().register(PlayerJoinEvent.class, event -> {
            initPlayer(event.getPlayer());
        });

        Spookly.getServer().getEventExecuter().register(AsyncPlayerPreLoginEvent.class, event -> {
            //
        });

        Spookly.getServer().getEventExecuter().register(PlayerQuitEvent.class, event -> {
            removeOnlinePlayer(event.getPlayer());
        });
    }

    public void initPlayer(Player player) {
        plugin.getPlayerSchema().getPlayerAsync(player, spooklyPlayer -> {
            if (spooklyPlayer == null) {
                plugin.getPlayerSchema().createUser(player, aBoolean -> {
                    if (!aBoolean)
                        SpooklyCorePlugin.getInstance().getLogger().warning("Failed to create entry for " + player.getName());
                    this.initPlayer(player);
                });
                return;
            } else {
                spooklyPlayer.updatePlayerFile();
            }
            registerOnlinePlayer(player, spooklyPlayer);
            if (punishmentCache.containsKey(player.getUniqueId())) {
                List<Punishment> punishments = punishmentCache.get(player.getUniqueId());
                spooklyPlayer.activePunishments(punishments);
                punishmentCache.remove(player.getUniqueId());
            }
        });
    }

    public void registerOnlinePlayer(@NotNull final Player player, @NotNull final SpooklyPlayer spooklyPlayer) {
        PlayerRegisterEvent playerRegisterEvent = new PlayerRegisterEvent(spooklyPlayer);
        registeredOnlinePlayers.put(player.getUniqueId(), spooklyPlayer);
        Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> {
            Bukkit.getPluginManager().callEvent(playerRegisterEvent);
        });
        SpooklyCorePlugin.getInstance().getLogger().info(player.getName() + " has been registered.");
    }

    public void removeOnlinePlayer(@NotNull final Player player) {
        registeredOnlinePlayers.remove(player.getUniqueId());
    }

}
