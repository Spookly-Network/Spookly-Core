package de.nehlen.spookly.players;

import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.database.DatabaseComponentCodec;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishments.PunishReason;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spooklycloudnetutils.manager.GroupManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.*;

@Accessors(fluent = true, chain = false)
public class SpooklyPlayerImpl extends SpooklyOfflinePlayerImpl implements SpooklyPlayer {

    private final Player player;
    @Getter private Component prefix;
    private Integer tabSortId;
    @Getter private TextColor nameColor;
    @Setter @Getter private List<Punishment> activePunishments;

    protected SpooklyPlayerImpl(Player player, String texture, Integer points, Instant lastPlayed, Instant firstPlayed) {
        super(player.getUniqueId(), player.getName(), texture, points, lastPlayed, firstPlayed, new ArrayList<>(), new HashMap<>());
        this.player = player;
        this.activePunishments = new ArrayList<>();
        this.resetNameTag();
    }

    @Override
    public Player toPlayer() {
        return player;
    }

    @Override
    public void resetNameTag() {
        this.nameColor(TextColor.fromHexString(GroupManager.getGroupColor(this.player)));
        Component defaultPrefix = Component.empty().color(nameColor())
                .append(Component.translatable(GroupManager.getGroupPrefix(this.player)).font(Key.key("rangs")).color(NamedTextColor.WHITE))
                .append(Component.text(" "));
        this.prefix(defaultPrefix, GroupManager.getGroupSortId(player) | 0);
    }

    @Override
    public void prefix(Component prefix) {
        this.prefix = prefix;
        this.refreshNameTag();
    }

    @Override
    public void prefix(Component prefix, Integer sortId) {
        SpooklyCorePlugin.getInstance().getLogger().info("Setting sortId to " + sortId + " for " + player.getName());
        this.tabSortId = sortId;
        this.prefix(prefix);
    }

    @Override
    public Integer tabSortId() {
        return tabSortId;
    }

    @Override
    public void refreshNameTag() {
        SpooklyCorePlugin.getInstance().getNametagManager().refreshNameTag(this);
    }

    @Override
    public void nameColor(final TextColor color) {
        this.nameColor = color;
        this.toPlayer().displayName(Component.text(name()).color(color));
    }

    @Override
    public Component nameTag() {
        Component prefix = Component.empty()
                .append(this.prefix());
        return prefix.append(player.displayName());
    }

    @Override
    public UUID uniqueId() {
        return player.getUniqueId();
    }

    @Override
    public String name() {
        return player.getName();
    }


    @Override
    public void kick() {
        //TODO implement punishments
        this.player.kick();
        SpooklyCorePlugin.getInstance().getLogger().warning("Punishments are not supportet yet.");
    }

    @Override
    public void kick(PunishReason reason) {
        //TODO implement punishments
        this.player.kick(Component.translatable(reason.getTranslationKey()));
        SpooklyCorePlugin.getInstance().getLogger().warning("Punishments are not supportet yet.");
    }

    @Override
    public void updatePlayerFile() {
        String onlineTexture = player.getPlayerProfile().getProperties().stream()
                .filter(item -> item.getName().equals("textures"))
                .toList().get(0).getValue();

        if (!this.textureUrl().equals(onlineTexture))
            this.textureUrl(onlineTexture);
        if (!this.name().equals(player.getName()))
            this.name(player.getName());
    }

    @Override
    public String textureUrl() {
        return player.getPlayerProfile().getProperties().stream()
                .filter(item -> item.getName().equals("textures"))
                .toList().get(0).getValue();
    }

    @Override
    public void addPunishment(Punishment punishment) {
        this.activePunishments.add(punishment);
        super.addPunishment(punishment);
    }

    public static SpooklyPlayerBuilder builder(Player player) {
        return new SpooklyPlayerBuilder(player);
    }

    public static class SpooklyPlayerBuilder {

        private final Player player;
        private String texture;
        private Integer points;
        private Instant lastPlayed;
        private Instant firstPlayed;

        protected SpooklyPlayerBuilder(Player player) {
            this.player = player;
        }

        public SpooklyPlayerBuilder texture(String texture) {
            this.texture = texture;
            return this;
        }

        public SpooklyPlayerBuilder points(Integer points) {
            this.points = points;
            return this;
        }

        public SpooklyPlayerBuilder lastPlayed(Instant lastPlayed) {
            this.lastPlayed = lastPlayed;
            return this;
        }

        public SpooklyPlayerBuilder firstPlayed(Instant firstPlayed) {
            this.firstPlayed = firstPlayed;
            return this;
        }

        public SpooklyPlayer build() {
            return new SpooklyPlayerImpl(player, texture, points, lastPlayed, firstPlayed);
        }
    }
}
