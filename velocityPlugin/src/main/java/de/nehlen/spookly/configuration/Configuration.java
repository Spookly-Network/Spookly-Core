package de.nehlen.spookly.configuration;

import de.nehlen.spookly.database.Connection;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

public class Configuration implements ConfigurationWrapper {

    private Connection configurationLoader = new YamlConfigurationLoader(YamlConfigurationLoader.builder().);
    private File file;

    public Configuration(File file) {
        File directory = file.getParentFile();
        if (!file.exists()) {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (Exception e) {
//                Bukkit.getLogger().log(Level.SEVERE, "Could not create " + file.getName() + ":", e);
            }
        }
        this.load(file);
    }

    @Override
    public ConfigurationWrapper load(File file) {
        this.file = file;
        this.configurationLoader = YamlConfigurationLoader.builder().file(file).build();
        return this;
    }

    @Override
    public void save() {
        if (file != null) {
            try {
                configurationLoader.
                getConfig().save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reload() throws IOException {

    }

    @Override
    public boolean contains(String path) {
        return false;
    }

    @Override
    public void set(String path, Object value) {

    }

    @Override
    public <T> T get(String path) {
        return null;
    }

    @Override
    public String getString(String path) {
        return "";
    }

    @Override
    public int getInt(String path) {
        return 0;
    }

    @Override
    public double getDouble(String path) {
        return 0;
    }

    @Override
    public boolean getBoolean(String path) {
        return false;
    }

    @Override
    public org.bukkit.Location getLocation(String path) {
        return null;
    }

    @Override
    public org.bukkit.Color getColor(String path) {
        return null;
    }

    @Override
    public org.bukkit.configuration.file.YamlConfiguration getConfig() {
        return null;
    }

    @Override
    public <T> T getOrSetDefault(String path, T defaultValue) {
        return null;
    }

    @Override
    public Set<String> getKeys(String path, boolean deep) {
        return Set.of();
    }

    @Override
    public boolean isSet(String path) {
        return false;
    }
}
