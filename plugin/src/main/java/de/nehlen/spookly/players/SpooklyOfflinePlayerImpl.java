package de.nehlen.spookly.players;

import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.instance.SpooklyCore;
import de.nehlen.spookly.player.PlayerPointsChangeEvent;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishments.PunishReason;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor //TODO add builder and protect constructor
public class SpooklyOfflinePlayerImpl implements SpooklyOfflinePlayer {

    private final UUID uuid;
    private String name;
    private String texture;
    private Integer points;
    private final Instant lastLogin;
    private final Instant firstLogin;

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
        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerName(this, name);
    }

    @Override
    public Integer points() {
        return points;
    }

    @Override
    public void points(Integer points) {
        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerPoints(this, points);
        if (this instanceof SpooklyPlayer) {
            PlayerPointsChangeEvent event = new PlayerPointsChangeEvent((SpooklyPlayer) this, this.points, points);
            Bukkit.getPluginManager().callEvent(event);
        }
        this.points = points;
    }

    @Override
    public void addPoints(Integer points) {
        points(points + points());
    }

    @Override
    public void ban() {
        //TODO implement punishments
        SpooklyCorePlugin.getInstance().getLogger().warning("Punishments are not supportet yet.");
    }

    @Override
    public void ban(PunishReason reason) {
        //TODO implement punishments
        SpooklyCorePlugin.getInstance().getLogger().warning("Punishments are not supportet yet.");
    }

    @Override
    public String textureUrl() {
        return texture;
    }

    @Override
    public void textureUrl(String textureUrl) {
        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerTexture(this, textureUrl);
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
        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerLastLogin(this, lastPlayed);
    }
}
