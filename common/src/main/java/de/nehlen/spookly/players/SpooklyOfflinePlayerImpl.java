package de.nehlen.spookly.players;

import de.nehlen.spookly.database.Schema;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.punishments.PunishReason;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
public class SpooklyOfflinePlayerImpl implements SpooklyOfflinePlayer {

    private final Schema schema;

    private final UUID uuid;
    private String name;
    private String texture;
    private Integer points;

    private Instant lastLogin;
    private final Instant firstLogin;

    public SpooklyOfflinePlayerImpl(Schema schema) {
        this.uuid = UUID.randomUUID();
        this.firstLogin = Instant.now();
        this.schema = schema;
    }

    public SpooklyOfflinePlayerImpl(UUID uuid, String name, String texture, Integer points, Instant lastLogin, Instant firstLogin, Schema schema) {
        this.uuid = uuid;
        this.name = name;
        this.texture = texture;
        this.points = points;
        this.lastLogin = lastLogin;
        this.firstLogin = firstLogin;
        this.schema = schema;
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
        this.name = name;
        schema.set("name", uuid, name);
    }

    @Override
    public Integer points() {
        return this.points;
    }

    @Override
    public void points(Integer points) {
        this.points = points;
        schema.set("points", uuid, points);
    }

    @Override
    public void addPoints(Integer points) {
        points(points + points());
    }

    @Override
    public void ban() {
        //TODO
    }

    @Override
    public void ban(PunishReason reason) {
        //TODO
    }

    @Override
    public String textureUrl() {
        return this.texture;
    }

    @Override
    public void textureUrl(String textureUrl) {
        this.texture = textureUrl;
        schema.set("texture", uuid, textureUrl);
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
        this.lastLogin = lastPlayed;
        schema.set("lastLogin", uuid, lastPlayed);
    }
}
