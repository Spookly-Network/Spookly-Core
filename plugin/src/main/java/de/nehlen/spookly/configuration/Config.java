package de.nehlen.spookly.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

public class Config implements ConfigurationWrapper {

    private final YamlConfiguration fileConfiguration = new YamlConfiguration();
    private File file;

    public Config(File file) {
        File directory = file.getParentFile();
        if (!file.exists()) {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Could not create " + file.getName() + ":", e);
            }
        }
        this.load(file);
    }

    @Override
    public ConfigurationWrapper load(File file) {
        this.file = file;
        try {
            fileConfiguration.load(file);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not read " + file.getParentFile().getAbsolutePath() + ":", e);
        }
        return this;
    }

    @Override
    public void save() {
        if (file != null) {
            try {
                fileConfiguration.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reload() throws IOException {
        if (file != null) {
            try {
                fileConfiguration.load(file);
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }

    @Override
    public boolean contains(String path) {
        try {
            return fileConfiguration.contains(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void set(final String path, final Object value) {
        if (value instanceof Location) {
            set(path + ".world", ((Location) value).getWorld().getName());
            set(path + ".x", ((Location) value).getX());
            set(path + ".y", ((Location) value).getY());
            set(path + ".z", ((Location) value).getZ());
            set(path + ".yaw", ((Location) value).getYaw());
            set(path + ".pitch", ((Location) value).getPitch());
            return;
        }
        fileConfiguration.set(path, value);
        save();
    }

    @Override
    public <T> T get(String path) {
        try {
            return (T) fileConfiguration.get(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getKeys(String path, boolean deep) {
        try {
            return fileConfiguration.getConfigurationSection(path).getKeys(deep);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString(String path) {
        try {
            return fileConfiguration.getString(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInt(String path) {
        try {
            return fileConfiguration.getInt(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDouble(String path) {
        try {
            return fileConfiguration.getDouble(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getBoolean(String path) {
        try {
            return fileConfiguration.getBoolean(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Location getLocation(String path) {
        final String worldName = getOrSetDefault(path + ".world", "world");
        final double x = getOrSetDefault(path + ".x", 0D);
        final double y = getOrSetDefault(path + ".y", 60D);
        final double z = getOrSetDefault(path + ".z", 0D);
        final double yaw = getOrSetDefault(path + ".yaw", 0D);
        final double pitch = getOrSetDefault(path + ".pitch", 0D);
        return new Location(Bukkit.getWorld(worldName), x, y, z, (float) yaw, (float) pitch);
    }

    @Override
    public Color getColor(String path) {
        String argbString = getOrSetDefault(path, "0,0,0,0");
        String[] values = argbString.split(",");
        return Color.fromARGB(Integer.parseInt(values[3]), Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
    }

    @Override
    public <T> T getOrSetDefault(String path, T defaultValue) {
        if (defaultValue instanceof Location && fileConfiguration.isSet(path + ".world")) {
            return (T) new Location(Bukkit.createWorld(new WorldCreator(fileConfiguration.getString(path + ".world"))), fileConfiguration.getDouble(path + ".x"), fileConfiguration.getDouble(path + ".y"), fileConfiguration.getDouble(path + ".z"), (float) fileConfiguration.getDouble(path + ".yaw"), (float) fileConfiguration.getDouble(path + ".pitch"));
        }
        final Object object = fileConfiguration.get(path);
        if (object == null) {
            if (defaultValue != null) {
                set(path, defaultValue);
            }
            return defaultValue;
        }
        return (T) object;
    }

    @Override
    public boolean isSet(String path) {
        return fileConfiguration.isSet(path);
    }
}
