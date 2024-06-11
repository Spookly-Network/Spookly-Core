package de.nehlen.spookly;

import de.nehlen.spookly.Phase.GamePhaseManagerImpl;
import de.nehlen.spookly.configuration.Config;
import de.nehlen.spookly.configuration.ConfigurationWrapper;
import de.nehlen.spookly.database.ConnectionImpl;
import de.nehlen.spookly.database.SpooklyPlayerSchema;
import de.nehlen.spookly.events.EventExecuterImpl;
import de.nehlen.spookly.instance.SpooklyCore;
import de.nehlen.spookly.listener.Listener;
import de.nehlen.spookly.manager.NametagManager;
import de.nehlen.spookly.manager.PlayerManager;
import de.nehlen.spookly.manager.TeamManagerImpl;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.placeholder.PlaceholderManager;
import de.nehlen.spookly.placeholder.PlaceholderManagerImpl;
import de.nehlen.spookly.plugin.SpooklyPlugin;
import de.nehlen.spookly.punishment.SpooklyPunishmentSchema;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spookly.punishments.PunishmentType;
import de.nehlen.spookly.team.TeamManager;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SpooklyCorePlugin extends SpooklyPlugin implements org.bukkit.event.Listener {

    @Getter private static SpooklyCorePlugin instance;
    @Getter private boolean debug = false;

    @Getter private ConfigurationWrapper databaseConfiguration;

    @Getter private EventExecuterImpl eventExecuter;

    @Getter private ConnectionImpl connection;
    @Getter private SpooklyPlayerSchema playerSchema;
    @Getter private SpooklyPunishmentSchema punishmentSchema;

    private SpooklyCore spooklyCore;

    @Getter private PlayerManager playerManager;
    @Getter private NametagManager nametagManager;
    @Getter private TeamManager teamManager;
    @Getter private GamePhaseManager gamePhaseManager;
    @Getter private PlaceholderManager placeholderManager;

    @Getter private TranslationRegistry translationRegistry;

    @Override
    protected void load() {
        instance = this;
        // Spookly Server instance
        this.spooklyCore = new SpooklyCore();
        getLogger().info("Created spookly server instance");

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    protected void enable() {
        // Configuration Files
        this.databaseConfiguration = new Config(new File(getDataFolder(), "database.yml"));
        getLogger().info("Initialized configuration files");

        // Executer
        this.eventExecuter = new EventExecuterImpl();
        getLogger().info("Initialized excuter");

        //Database connection & Schemas
        this.connection = new ConnectionImpl(this);
        getLogger().info("Initialized DB configuration");

        this.playerSchema = new SpooklyPlayerSchema(this.connection);
        this.punishmentSchema = new SpooklyPunishmentSchema(this.connection);
        getLogger().info("Initialized DB schemas");

        // Manager
        this.playerManager = new PlayerManager(this);
        this.nametagManager = new NametagManager();
        this.teamManager = new TeamManagerImpl();
        this.gamePhaseManager = new GamePhaseManagerImpl();
        this.placeholderManager = new PlaceholderManagerImpl();
        getLogger().info("Initialized manager");

        this.playerSchema.createSchema();
        this.punishmentSchema.createSchema();

        //Listener for event excuter
        registerEvent(new Listener());
        getLogger().info("Initialized bukkit event listener");

        this.translationRegistry = TranslationRegistry.create(Key.key("spookly:value"));
        registerTranslations();
        getLogger().info("Initialized adventure translation");
        getLogger().info("Done");
    }

    @Override
    protected void disable() {
        this.connection.close();
    }

    @Override
    protected void postStartup() {
    }

    private void registerTranslations() {
        ResourceBundle bundleUS = ResourceBundle.getBundle("spookly.Translations", Locale.US, UTF8ResourceBundleControl.get());
        ResourceBundle bundleDE = ResourceBundle.getBundle("spookly.Translations", Locale.GERMANY, UTF8ResourceBundleControl.get());

        translationRegistry.registerAll(Locale.US, bundleUS, true);
        translationRegistry.registerAll(Locale.GERMAN, bundleDE, true);
        GlobalTranslator.translator().addSource(translationRegistry);
    }

    @EventHandler
    public void handleAsyncLogin(AsyncPlayerPreLoginEvent event) {
        this.getLogger().info("Logging login from " + event.getUniqueId());
        //Load Punishments pre login so it can be checked on sync login
        this.getPunishmentSchema().getActivePunishments(event.getUniqueId(), optionalPunishments -> {
            this.getLogger().info("0");
            if (optionalPunishments.isEmpty()) {
                playerManager.punishmentCache.put(event.getUniqueId(), new ArrayList<>());
                return;
            }
            List<Punishment> punishments = new ArrayList<>(optionalPunishments.get());
            playerManager.punishmentCache.put(event.getUniqueId(), punishments);
        });
    }

    public void handleLogin(PlayerLoginEvent event) {
        //Get punishment cache and refuse login when ban is active
        if(!playerManager.punishmentCache.containsKey(event.getPlayer().getUniqueId()))
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("An error ocurred. Please try again later!"));

        UUID uuid = event.getPlayer().getUniqueId();
        Optional<Punishment> optionalPunishment = playerManager.punishmentCache.get(uuid).stream().filter(p -> p.getType() == PunishmentType.BAN).findFirst();
        if (!optionalPunishment.isPresent()) {
            event.allow();
            return;
        }

        Punishment punishment = optionalPunishment.get();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd.MM.yyyy hh:mm");

        event.disallow(PlayerLoginEvent.Result.KICK_BANNED,
                Component.empty()
                        .append(Component.text("You are banned!").color(NamedTextColor.RED))
                        .append(Component.newline())
                        .append(Component.text("Reason: ").color(NamedTextColor.GRAY))
                        .append(Component.text(punishment.getReason()).color(NamedTextColor.RED))
                        .append(Component.newline())
                        .append(Component.text("Until: ").color(NamedTextColor.GRAY))
                        .append(Component.text(formatter.format(punishment.getExpiry())).color(NamedTextColor.RED))
                        .append(Component.text(" (UTC)"))
        );
        event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
    }
}
