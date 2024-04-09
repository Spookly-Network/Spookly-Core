package de.nehlen.spookly;

import de.nehlen.spookly.Phase.GamePhaseManagerImpl;
import de.nehlen.spookly.configuration.Config;
import de.nehlen.spookly.configuration.ConfigurationWrapper;
import de.nehlen.spookly.database.Connection;
import de.nehlen.spookly.database.SpooklyPlayerSchema;
import de.nehlen.spookly.events.EventExecuter;
import de.nehlen.spookly.events.EventExecuterImpl;
import de.nehlen.spookly.instance.SpooklyCore;
import de.nehlen.spookly.listener.Listener;
import de.nehlen.spookly.manager.NametagManager;
import de.nehlen.spookly.manager.PlayerManager;
import de.nehlen.spookly.manager.TeamManagerImpl;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.team.TeamManager;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class SpooklyCorePlugin extends de.nehlen.spookly.plugin.SpooklyPlugin {

    @Getter private static SpooklyCorePlugin instance;

    @Getter private ConfigurationWrapper databaseConfiguration;

    @Getter private EventExecuterImpl eventExecuter;

    @Getter private Connection connection;
    @Getter private SpooklyPlayerSchema playerSchema;

    private SpooklyCore spooklyCore;

    @Getter private PlayerManager playerManager;
    @Getter private NametagManager nametagManager;
    @Getter private TeamManager teamManager;
    @Getter private GamePhaseManager gamePhaseManager;

    @Getter private TranslationRegistry translationRegistry;

    @Override
    protected void load() {
        instance = this;
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
        this.connection = new Connection(this);
        getLogger().info("Initialized DB configuration");

        // Spookly Server instance
        this.spooklyCore = new SpooklyCore();
        getLogger().info("Created spookly server instance");

        this.playerSchema = new SpooklyPlayerSchema(this.connection);
        getLogger().info("Initialized DB schemas");

        // Manager
        this.playerManager = new PlayerManager(this);
        this.nametagManager = new NametagManager();
        this.teamManager = new TeamManagerImpl();
        this.gamePhaseManager = new GamePhaseManagerImpl();
        getLogger().info("Initialized manager");

        this.playerSchema.createSchema();

        //Listener for event excuter
        registerEvent(new Listener());
        getLogger().info("Initialized bukkit event listener");

        this.translationRegistry = TranslationRegistry.create(Key.key("spookly:value"));
        registerTranslations();
        getLogger().info("Initialized adventure translation");
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
}
