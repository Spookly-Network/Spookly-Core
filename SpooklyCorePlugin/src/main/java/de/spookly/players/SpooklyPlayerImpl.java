package de.spookly.players;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.spookly.SpooklyCorePlugin;
import de.spookly.placeholder.PlaceholderContext;
import de.spookly.player.SpooklyPlayer;
import de.spookly.punishments.PunishReason;
import de.spookly.punishments.Punishment;
import de.nehlen.spooklycloudnetutils.manager.GroupManager;
import io.papermc.paper.chat.ChatRenderer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

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
        Component defaultPrefix = Component.translatable(GroupManager.getGroupPrefix(this.player))
                .color(NamedTextColor.WHITE);
        Component combinedPrefix = Component.empty()
                .append(defaultPrefix)
                .append(Component.text(" "));

        this.prefix(combinedPrefix, GroupManager.getGroupSortId(player) | 0);
    }

    @Override
    public void prefix(Component prefix) {
        this.prefix = prefix;
        this.refreshNameTag();
    }

    @Override
    public void prefix(Component prefix, Integer sortId) {
        this.tabSortId = sortId;
        this.prefix(prefix);
    }

    @Override
    public ChatRenderer getChatRenderer() {
        return (source, sourceDisplayName, message, viewer) -> {
            PlaceholderContext context = new PlaceholderContext(source, PlaceholderContext.PlaceholderType.CHAT);

            return (Component) Component.text()
                    .append(prefix())
                    .append(sourceDisplayName.color(nameColor()))
                    .append(Component.text(" ›› ").color(NamedTextColor.GRAY)) // Separator
                    .append(SpooklyCorePlugin.getInstance().getPlaceholderManager().replacePlaceholder(message, context))
                    .build();
        };
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
