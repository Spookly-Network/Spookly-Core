package de.spookly;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import de.spookly.canvas.MenuFunctionListener;
import de.spookly.configuration.Config;
import de.spookly.configuration.ConfigurationWrapper;
import de.spookly.database.MongoDatabaseConfiguration;
import de.spookly.event.EventExecuterImpl;
import de.spookly.instance.SpooklyCore;
import de.spookly.listener.Listener;
import de.spookly.manager.GamePhaseManagerImpl;
import de.spookly.manager.NametagManager;
import de.spookly.manager.PlayerManager;
import de.spookly.manager.TeamManagerImpl;
import de.spookly.placeholder.PlaceholderManager;
import de.spookly.placeholder.PlaceholderManagerImpl;
import de.spookly.plugin.SpooklyPlugin;
import de.spookly.team.TeamManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class SpooklyCorePlugin extends SpooklyPlugin implements org.bukkit.event.Listener {

    @Getter private static SpooklyCorePlugin instance;
    @Getter private boolean debug = false;

    @Getter private ConfigurationWrapper databaseConfiguration;

    @Getter private EventExecuterImpl eventExecuter;

    private SpooklyCore spooklyCore;

    @Getter private PlayerManager playerManager;
    @Getter private NametagManager nametagManager;
    @Getter private TeamManager teamManager;
    @Getter private GamePhaseManagerImpl gamePhaseManager;
    @Getter private PlaceholderManager placeholderManager;

    @Getter private TranslationRegistry translationRegistry;

    @Getter private MongoDatabaseConfiguration mongoDatabaseConfiguration;

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
        this.mongoDatabaseConfiguration = new MongoDatabaseConfiguration(this);
        getLogger().info("Initialized database and configuration files");

        this.mongoDatabaseConfiguration.connect();
        getLogger().info("Connected to MongoDB database");


        // Executer
        this.eventExecuter = new EventExecuterImpl();
        getLogger().info("Initialized excuter");

        //Replaced with mongoDB

        getLogger().info("Initialized DB schemas");

        // Manager
        this.playerManager = new PlayerManager(this);
        this.nametagManager = new NametagManager();
        this.teamManager = new TeamManagerImpl();
        this.gamePhaseManager = new GamePhaseManagerImpl();
        this.placeholderManager = new PlaceholderManagerImpl();
        getLogger().info("Initialized manager");

//        this.playerSchema.createSchema();
//        this.punishmentSchema.createSchema();

        //Listener for event excuter
        registerEvent(new Listener());
        registerEvent(new MenuFunctionListener());
        getLogger().info("Initialized bukkit event listener");

        this.translationRegistry = TranslationRegistry.create(Key.key("spookly:value"));
        registerTranslations();
        getLogger().info("Initialized adventure translation");
        getLogger().info("Done");
    }

    @Override
    protected void disable() {}

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
//        this.getLogger().info("Logging login from " + event.getUniqueId());
//        //Load Punishments pre login so it can be checked on sync login
//        this.getPunishmentSchema().getActivePunishments(event.getUniqueId(), optionalPunishments -> {
//            this.getLogger().info("0");
//            if (optionalPunishments.isEmpty()) {
//                playerManager.punishmentCache.put(event.getUniqueId(), new ArrayList<>());
//                return;
//            }
//            List<Punishment> punishments = new ArrayList<>(optionalPunishments.get());
//            playerManager.punishmentCache.put(event.getUniqueId(), punishments);
//        });
    }

    public void handleLogin(PlayerLoginEvent event) {
        playerManager.initPlayer(event.getPlayer());

//        if(!playerManager.punishmentCache.containsKey(event.getPlayer().getUniqueId()))
//            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("An error ocurred. Please try again later!"));
//
//        UUID uuid = event.getPlayer().getUniqueId();
//        Optional<Punishment> optionalPunishment = playerManager.punishmentCache.get(uuid).stream().filter(p -> p.getType() == PunishmentType.BAN).findFirst();
//        if (!optionalPunishment.isPresent()) {
//            event.allow();
//            return;
//        }
//
//        Punishment punishment = optionalPunishment.get();
//        DateTimeFormatter formatter = DateTimeFormatter
//                .ofPattern("dd.MM.yyyy hh:mm");
//
//        event.disallow(PlayerLoginEvent.Result.KICK_BANNED,
//                Component.empty()
//                        .append(Component.text("You are banned!").color(NamedTextColor.RED))
//                        .append(Component.newline())
//                        .append(Component.text("Reason: ").color(NamedTextColor.GRAY))
//                        .append(Component.text(punishment.getReason()).color(NamedTextColor.RED))
//                        .append(Component.newline())
//                        .append(Component.text("Until: ").color(NamedTextColor.GRAY))
//                        .append(Component.text(formatter.format(punishment.getExpiry())).color(NamedTextColor.RED))
//                        .append(Component.text(" (UTC)"))
//        );
//        event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
    }
}
