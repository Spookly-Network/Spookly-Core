package de.nehlen.spookly.manager;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.database.subscriber.LoadPlayerSubscriber;
import de.nehlen.spookly.database.subscriber.VoidSubscriber;
import de.nehlen.spookly.player.PlayerRegisterEvent;
import de.nehlen.spookly.player.PlayerUnregisterEvent;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.players.SpooklyOfflinePlayerImpl;
import de.nehlen.spookly.players.SpooklyPlayerImpl;
import de.nehlen.spookly.punishments.Punishment;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

public class PlayerManager {

    private final SpooklyCorePlugin plugin;

    @Getter @NotNull
    public Map<UUID, SpooklyPlayer> registeredOnlinePlayers = new HashMap<>();
    public Map<UUID, List<Punishment>> punishmentCache = new HashMap<>();

    @Getter
    MongoCollection<Document> collection;

    public PlayerManager(SpooklyCorePlugin plugin) {
        this.plugin = plugin;
        this.collection = plugin.getMongoDatabaseConfiguration().getCollection("player");

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
        findPlayer(player, spooklyPlayer -> {
            if (spooklyPlayer == null) {
                savePlayerToDatabase(SpooklyOfflinePlayerImpl.of(player).build());
                this.initPlayer(player);
                return;
            }
            spooklyPlayer.updatePlayerFile();
            registerOnlinePlayer(player, spooklyPlayer);
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
        SpooklyPlayer spooklyPlayer = registeredOnlinePlayers.get(player.getUniqueId());
        plugin.getLogger().info(spooklyPlayer.name() + " has been gettet");
        PlayerUnregisterEvent playerUnregisterEvent = new PlayerUnregisterEvent(spooklyPlayer);
        Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> {
            Bukkit.getPluginManager().callEvent(playerUnregisterEvent);
        });
        registeredOnlinePlayers.remove(player.getUniqueId());
    }

    public void findPlayer(@NotNull final Player onlinePlayer, final Consumer<SpooklyPlayer> callback) {
        plugin.getLogger().info("Finding player " + onlinePlayer.getName());
        findOfflinePlayer(onlinePlayer.getUniqueId(), player -> {
            if (player == null) {
                callback.accept(null);
                return;
            }

            plugin.getLogger().info("Player " + onlinePlayer.getName() + " has been found.");
            callback.accept(SpooklyPlayerImpl.builder(onlinePlayer)
                    .texture(player.textureUrl())
                    .points(player.points())
                    .lastPlayed(player.lastPlayed())
                    .firstPlayed(player.firstPlayed())
                    .build());
        });
    }

    public void findOfflinePlayer(@NotNull final UUID uuid, final Consumer<SpooklyOfflinePlayer> callback) {
        findOfflinePlayerAsync(eq("uuid", uuid), callback);
    }

    public void findOfflinePlayer(@NotNull final String name, final Consumer<SpooklyOfflinePlayer> callback) {
        findOfflinePlayerAsync(eq("name", name), callback);

    }

    private void findOfflinePlayerAsync(Bson bson, Consumer<SpooklyOfflinePlayer> callback) {
        collection.find(bson).first().subscribe(new LoadPlayerSubscriber(callback));
    }

    public void savePlayerToDatabase(@NotNull final SpooklyOfflinePlayer player) {
        Document document = playerToDocument(player);
        assert plugin.getMongoDatabaseConfiguration().getMongoDatabase() != null;
        this.collection
                .insertOne(document)
                .subscribe(new VoidSubscriber<InsertOneResult>());
    }

    public void deletePlayerFromDatabase(@NotNull final SpooklyOfflinePlayer player) {
        String playerUniqueId = player.uniqueId().toString();
        this.collection
                .deleteOne(eq("uuid", playerUniqueId))
                .subscribe(new VoidSubscriber<DeleteResult>());
    }

    private Document playerToDocument(SpooklyOfflinePlayer player) {
        Document doc = new Document("uuid", player.uniqueId())
                .append("name", player.name())
                .append("texture", player.textureUrl())
                .append("points", player.points())
                .append("lastPlayed", player.lastPlayed())
                .append("firstPlayed", player.firstPlayed())
                .append("punishments", player.getPunishments().stream().map(punishment ->
                        new Document("uuid", punishment.getUniqueId())
                                .append("type", punishment.getType())
                                .append("expiry", punishment.getExpiry())
                                .append("reason", punishment.getReason())
                                .append("creator", punishment.getCreator())
                                .append("createdAt", punishment.getCreationTime())
                                .append("updatedAt", punishment.getLastUpdate())
                ).toList())
                .append("components", player.getRawComponents());

//        for (var entry : player.getRawComponents().entrySet()) {
//            doc.append("components." + entry.getKey(), entry.getValue());
//            doc.
//        }
        return doc;
    }
}
