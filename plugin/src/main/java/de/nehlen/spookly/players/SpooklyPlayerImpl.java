package de.nehlen.spookly.players;

import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishments.PunishReason;
import de.nehlen.spooklycloudnetutils.manager.GroupManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.UUID;

@Accessors(fluent = true, chain = false)
public class SpooklyPlayerImpl extends SpooklyOfflinePlayerImpl implements SpooklyPlayer {

    private final Player player;
    @Getter @Setter private Component prefix;
    @Getter private TextColor nameColor;

    protected SpooklyPlayerImpl(Player player, String texture, Integer points, Instant lastPlayed, Instant firstPlayed) {
        super(player.getUniqueId(), player.getName(),"", 0, lastPlayed, firstPlayed);
        this.player = player;
    }


    @Override
    public Player toPlayer() {
        return player;
    }

    @Override
    public void resetNameTag() {
        this.nameColor(NamedTextColor.GRAY);
        Component defaultPrefix = Component.empty().color(nameColor())
                .append(Component.translatable(GroupManager.getGroupPrefix(this.player)).font(Key.key("rangs")).color(NamedTextColor.WHITE))
                .append(Component.text(" "));
        this.prefix(defaultPrefix);
    }

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
        if(!this.name().equals(player.getName()))
            this.name(player.getName());
    }

    @Override
    public String textureUrl() {
        return player.getPlayerProfile().getProperties().stream()
                .filter(item -> item.getName().equals("textures"))
                .toList().get(0).getValue();
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
