package de.nehlen.spookly.team;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.player.SpooklyPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

@Getter @Accessors(fluent = true, chain = false)
public class TeamImpl implements Team{

    private final UUID uuid;
    private final List<Player> registeredPlayers;
    @Setter private Integer maxTeamSize;
    @Setter private TextColor teamColor;
    private Map<String, Object> memory;
    @Setter private Inventory teamInventory = Bukkit.createInventory(null, 27, Component.text(""));
    @Setter private Component teamName;
    @Setter private Component prefix;

    protected final TeamManager teamManager;

    protected TeamImpl(Integer maxTeamSize, TextColor teamColor, Component prefix, Component teamName, Map<String, Object> memory) {
        this.uuid = UUID.randomUUID();

        this.maxTeamSize = maxTeamSize;
        this.teamColor = teamColor;
        this.prefix = prefix;
        this.teamName = teamName;
        this.memory = memory;

        this.registeredPlayers = new ArrayList<>();
        this.teamManager = Spookly.getTeamManager();
    }

    @Override
    public void registerPlayer(Player player) {
        SpooklyPlayer spooklyPlayer = Spookly.getPlayer(player);
        PlayerJoinTeamEvent playerJoinTeamEvent = new PlayerJoinTeamEvent(spooklyPlayer, this);

        if (this.teamManager.contains(this)) {
            if (!(this.registeredPlayers.size() >= this.maxTeamSize)) {

                this.teamManager.registeredTeams().stream().filter(team -> team.contains(player)).forEach(team -> {
                    PlayerQuitTeamEvent playerQuitTeamEvent = new PlayerQuitTeamEvent(spooklyPlayer, team);
                    Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> Bukkit.getPluginManager().callEvent(playerQuitTeamEvent));
                    if (!playerQuitTeamEvent.isCancelled())
                        team.removePlayer(player);
                });

                Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> Bukkit.getPluginManager().callEvent(playerJoinTeamEvent));
                if (!playerJoinTeamEvent.isCancelled()) {
                    this.registeredPlayers.add(player);
                }
            }
        }
    }

    @Override
    public void removePlayer(Player player) {
        SpooklyPlayer spooklyPlayer = Spookly.getPlayer(player);
        PlayerQuitTeamEvent playerQuitTeamEvent = new PlayerQuitTeamEvent(spooklyPlayer, this);

        if (this.teamManager.contains(this))
            if (this.registeredPlayers.contains(player)) {
                Bukkit.getScheduler().runTask(SpooklyCorePlugin.getInstance(), () -> Bukkit.getPluginManager().callEvent(playerQuitTeamEvent));
                if (!playerQuitTeamEvent.isCancelled())
                    this.registeredPlayers.remove(player);
            }
    }

    @Override
    public boolean contains(Player player) {
        return this.registeredPlayers.contains(player);
    }

    @Override
    public boolean isFull() {
        return this.registeredPlayers.size() >= this.maxTeamSize;
    }

    @Override
    public Integer size() {
        return this.registeredPlayers.size();
    }

    @Override
    public void addToMemory(String key, Object object) {
        this.memory.put(key, object);
    }

    @Override
    public void removeFromMemory(String key) {
        this.memory.remove(key);
    }

    @Override
    public void replaceFromMemory(String key, Object object) {
        this.memory.replace(key, object);
    }

    public static TeamBuilder builder() {
        return new TeamBuilder();
    }

    public static class TeamBuilder implements Team.Builder {
        private Integer maxTeamSize;
        private TextColor teamColor;
        private Component prefix;
        private Component teamName;
        private Map<String, Object> memory = new HashMap<>();

        protected TeamBuilder() {}

        public TeamBuilder maxTeamSize(final int maxTeamSize) {
            this.maxTeamSize = maxTeamSize;
            return this;
        }

        public TeamBuilder teamColor(final TextColor teamColor) {
            this.teamColor = teamColor;
            return this;
        }

        public TeamBuilder prefix(final Component prefix) {
            this.prefix = prefix;
            return this;
        }
        public TeamBuilder teamName(final Component teamName) {
            this.teamName = teamName;
            return this;
        }
        public TeamBuilder addToMemory(String key, Object object) {
            this.memory.put(key, object);
            return this;
        }
        public Team build() {
            return new TeamImpl(maxTeamSize, teamColor, prefix, teamName, memory);
        }

    }
}
