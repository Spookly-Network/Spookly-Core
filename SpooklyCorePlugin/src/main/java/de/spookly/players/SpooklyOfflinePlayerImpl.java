package de.spookly.players;

import static com.mongodb.client.model.Filters.eq;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.spookly.SpooklyCorePlugin;
import de.spookly.database.DatabaseComponentCodec;
import de.spookly.database.subscriber.VoidSubscriber;
import de.spookly.player.PlayerPointsChangeEvent;
import de.spookly.player.SpooklyOfflinePlayer;
import de.spookly.player.SpooklyPlayer;
import de.spookly.punishment.PunishmentImpl;
import de.spookly.punishments.Punishment;
import de.spookly.punishments.PunishmentType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpooklyOfflinePlayerImpl implements SpooklyOfflinePlayer {

    @BsonId
    private final UUID uuid;
    private String name;
    private String texture;
    private Integer points;

    private Instant lastLogin;
    private final Instant firstLogin;

    private List<Punishment> punishments;
    private Map<String, Document> components;

    protected SpooklyOfflinePlayerImpl(UUID uuid, String name, String texture, Integer points, Instant lastLogin, Instant firstLogin, List<Punishment> punishments, Map<String, Document> components) {
        this.uuid = uuid;
        this.name = name;
        this.texture = texture;
        this.points = points;
        this.lastLogin = lastLogin;
        this.firstLogin = firstLogin;
        this.punishments = punishments;
        this.components = components;
    }

    @Override
    public UUID uniqueId() {
        return uuid;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void name(String name) {
        Document replacement = new Document("name", name);
        SpooklyCorePlugin.getInstance().getPlayerManager().getCollection()
                .updateOne(eq("uuid", uniqueId()), replacement)
                .subscribe(new VoidSubscriber());
//        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerName(this, name);
    }

    @Override
    public Integer points() {
        return points;
    }

    @Override
    public void points(Integer points) {
        Document replacement = new Document("points", points);
        SpooklyCorePlugin.getInstance().getPlayerManager().getCollection()
                .updateOne(eq("uuid", uniqueId()), replacement)
                .subscribe(new VoidSubscriber());
//        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerPoints(this, points);
        this.points = points;

        if (this instanceof SpooklyPlayer) {
            PlayerPointsChangeEvent event = new PlayerPointsChangeEvent((SpooklyPlayer) this, this.points, points);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    @Override
    public void addPoints(Integer points) {
        points(points + points());
    }

    /**
     * @param amount
     * @param unit
     * @param reason
     * @param issuer
     * @deprecated use {@link #addPunishment(Punishment)} instead
     */
    @Override
    public void ban(Integer amount, TimeUnit unit, String reason, SpooklyPlayer issuer) {
        Instant until = Instant.now();
        if (Objects.requireNonNull(unit) == TimeUnit.SECONDS) {
            until = until.plusSeconds(amount);
        } else {
            until = until.plusNanos(unit.toNanos(amount));
        }

        Punishment punishment = PunishmentImpl.Builder(this, issuer)
                .setExpiry(until)
                .setType(PunishmentType.BAN)
                .setReason(reason)
                .build();

        addPunishment(punishment);
    }

    @Override
    public String textureUrl() {
        return texture;
    }

    @Override
    public void textureUrl(String textureUrl) {
        Document replacement = new Document("texture", points);
        SpooklyCorePlugin.getInstance().getPlayerManager().getCollection()
                .updateOne(eq("uuid", uniqueId()), replacement)
                .subscribe(new VoidSubscriber());
//        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerTexture(this, textureUrl);
    }

    @Override
    public Instant firstPlayed() {
        return firstLogin;
    }

    @Override
    public Instant lastPlayed() {
        return lastLogin;
    }

    @Override
    public void lastPlayed(Instant lastPlayed) {
        Document replacement = new Document("lastPlayed", lastPlayed);
        SpooklyCorePlugin.getInstance().getPlayerManager().getCollection()
                .updateOne(eq("uuid", uniqueId()), replacement)
                .subscribe(new VoidSubscriber());

//        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerLastLogin(this, lastPlayed);
    }

    @Override
    public void addPunishment(Punishment punishment) {
        this.punishments.add(punishment);
        this.save();
    }

    @Override
    public void removePunishment(Punishment punishment) {
        this.punishments.remove(punishment);
    }

    @Override
    public List<Punishment> getPunishments() {
        return this.punishments;
    }

    @Override
    @Nullable
    public <T> T getDatabaseComponent(@NotNull String key, @NotNull DatabaseComponentCodec<T> codec) {
        Document document = components.get(key);
        if (document == null) {
            return null;
        }
        return codec.decode(document);
    }

    @Override
    public <T> void addDatabaseComponent(@NotNull String key, @NotNull T databaseComponent, @NotNull DatabaseComponentCodec<T> codec) {
        Document document = codec.encode(databaseComponent);
        components.put(key, document);
    }

    @Override
    public <T> void removeDatabaseComponent(@NotNull String key) {
        this.components.remove(key);
    }

    @Override
    public <T> void replaceDatabaseComponent(@NotNull String key, @NotNull T databaseComponent, @NotNull DatabaseComponentCodec<T> codec) {
        this.components.replace(key, codec.encode(databaseComponent));
    }

    @Override
    public Map<String, Document> getRawComponents() {
        return components;
    }

    @Override
    public Boolean hasDatabaseComponent(@NotNull String key) {
        return components.containsKey(key);
    }


    //Begin database stuff
    @Override
    public void save() {
        Document document = playerToDocument();
        SpooklyCorePlugin.getInstance().getPlayerManager().getCollection()
                .replaceOne(eq("uuid", this.uniqueId()), document)
                .subscribe(new VoidSubscriber<UpdateResult>());
    }

    protected Document playerToDocument() {
        Document doc = new Document("uuid", this.uniqueId())
                .append("name", this.name())
                .append("texture", this.textureUrl())
                .append("points", this.points())
                .append("lastPlayed", this.lastPlayed())
                .append("firstPlayed", this.firstPlayed())
                .append("punishments", this.getPunishments().stream().map(punishment ->
                        new Document("uuid", punishment.getUniqueId())
                                .append("type", punishment.getType())
                                .append("expiry", punishment.getExpiry())
                                .append("reason", punishment.getReason())
                                .append("creator", punishment.getCreator())
                                .append("createdAt", punishment.getCreationTime())
                                .append("updatedAt", punishment.getLastUpdate())
                ).toList())
                .append("components", this.getRawComponents());
//        for (var entry : getRawComponents().entrySet()) {
//            doc.append("components." + entry.getKey(), entry.getValue());
//        }
        return doc;
    }

    /*
     * Begin builder
     */
    public static SpooklyOfflinePlayerBuilder builder(UUID uuid) {
        return new SpooklyOfflinePlayerBuilder(uuid);
    }

    public static SpooklyOfflinePlayerBuilder of(Player player) {
        final String playerTexture = player.getPlayerProfile().getProperties().stream()
                .filter(item -> item.getName().equals("textures"))
                .toList().getFirst().getValue();

        return new SpooklyOfflinePlayerBuilder(player.getUniqueId())
                .name(player.getName())
                .texture(playerTexture);
    }

    public static class SpooklyOfflinePlayerBuilder {

        private final UUID uuid;
        private String texture = "";
        private String name = "";
        private Integer points = 0;
        private Instant lastLogin = Instant.now();
        private Instant firstLogin = Instant.now();
        private List<Punishment> punishments = new ArrayList<>();
        private Map<String, Document> components = new HashMap<>();

        protected SpooklyOfflinePlayerBuilder(UUID uuid) {
            this.uuid = uuid;
        }

        public SpooklyOfflinePlayerBuilder texture(String texture) {
            this.texture = texture;
            return this;
        }

        public SpooklyOfflinePlayerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SpooklyOfflinePlayerBuilder points(Integer points) {
            this.points = points;
            return this;
        }

        public SpooklyOfflinePlayerBuilder lastPlayed(Instant lastPlayed) {
            this.lastLogin = lastPlayed;
            return this;
        }

        public SpooklyOfflinePlayerBuilder firstPlayed(Instant firstPlayed) {
            this.firstLogin = firstPlayed;
            return this;
        }

        public SpooklyOfflinePlayerBuilder punishments(List<Punishment> punishments) {
            this.punishments.addAll(punishments);
            return this;
        }

        public SpooklyOfflinePlayerBuilder components(Map<String, Document> components) {
            this.components.putAll(components);
            return this;
        }

        public SpooklyOfflinePlayerBuilder component(String key, Document component) {
            this.components.put(key, component);
            return this;
        }

        public SpooklyOfflinePlayer build() {
            return new SpooklyOfflinePlayerImpl(uuid, name, texture, points, lastLogin, firstLogin, punishments, components);
        }
    }
}
