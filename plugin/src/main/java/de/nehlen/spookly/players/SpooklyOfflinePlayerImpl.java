package de.nehlen.spookly.players;

import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.player.PlayerPointsChangeEvent;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishment.PunishmentImpl;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spookly.punishments.PunishmentType;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
//TODO add builder and protect constructor
public class SpooklyOfflinePlayerImpl implements SpooklyOfflinePlayer {

    private final UUID uuid;
    private String name;
    private String texture;
    private Integer points;

    private Instant lastLogin;
    private final Instant firstLogin;

    public SpooklyOfflinePlayerImpl() {
        this.uuid = UUID.randomUUID();
        this.firstLogin = Instant.now();
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
        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerName(this, name);
    }

    @Override
    public Integer points() {
        return points;
    }

    @Override
    public void points(Integer points) {
        SpooklyCorePlugin.getInstance().getPlayerSchema().setPlayerPoints(this, points);
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

        SpooklyCorePlugin.getInstance().getPunishmentSchema().addPunishment(punishment);

        Player player = Bukkit.getPlayer(this.uniqueId());
        if (player != null) {
            player.kick();
        }
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

    @Override
    public void addPunishment(Punishment punishment) {
        SpooklyCorePlugin.getInstance().getPunishmentSchema().addPunishment(punishment);
    }
}
