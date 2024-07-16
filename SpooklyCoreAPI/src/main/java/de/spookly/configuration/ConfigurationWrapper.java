package de.spookly.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Location;

public interface ConfigurationWrapper {

    /**
     * Loads the configuration data from the specified file.
     *
     * @param file the file to load the configuration data from
     * @return current Configuration instance
     */
    ConfigurationWrapper load(File file);

    /**
     * Saves the configuration data to the specified file.
     *
     */
    void save();

    /**
     * Reloads the configuration data from the file it was last loaded from.
     *
     * @throws IOException if there was an error reading or parsing the configuration file
     */
    void reload() throws IOException;

    /**
     * Checks if the configuration contains a value at the specified path.
     *
     * @param path the path to check for a value
     * @return true if a value exists at the specified path, false otherwise
     */
    boolean contains(String path);

    /**
     * Sets the value at the specified path.
     *
     * @param path the path to set the value at
     * @param value the value to set
     */
    void set(String path, Object value);

    /**
     * Gets the value at the specified path.
     *
     * @param path the path to get the value at
     * @return the value at the specified path, or null if no value exists at the path
     */
    <T> T get(String path);

    /**
     * Gets the string value at the specified path.
     *
     * @param path the path to get the value at
     * @return the string value at the specified path, or null if no value exists at the path
     */
    String getString(String path);

    /**
     * Gets the integer value at the specified path.
     *
     * @param path the path to get the value at
     * @return the integer value at the specified path, or 0 if no value exists at the path or the value is not an integer
     */
    int getInt(String path);

    /**
     * Gets the double value at the specified path.
     *
     * @param path the path to get the value at
     * @return the double value at the specified path, or 0.0 if no value exists at the path or the value is not a double
     */
    double getDouble(String path);

    /**
     * Gets the boolean value at the specified path.
     *
     * @param path the path to get the value at
     * @return the boolean value at the specified path, or false if no value exists at the path or the value is not a boolean
     */
    boolean getBoolean(String path);

    /**
     * Gets the location value at the specified path.
     *
     * @param path the path to get the value at
     * @return the Location value at the specified path, or 0,0,0 if no value exists at the path or the value is not a location
     */
    Location getLocation(String path);

    /**
     * Gets the Color value at the specified path.
     *
     * @param path the path to get the value at
     * @return the Color value at the specified path, or 0,0,0 if no value exists at the path or the value is not a rgb color
     */
    Color getColor(String path);

    /**
     * Gets the value at the specified path, or sets the value to the default value and returns it if no value exists at the path.
     *
     * @param path the path to get the value at
     * @param defaultValue the default value to set and return if no value exists at the path
     * @return the value at the specified path, or the default value if no value exists at the path
     */
    <T> T getOrSetDefault(String path, T defaultValue);

    /**
     * See {@link org.bukkit.configuration.ConfigurationSection#getKeys(boolean)}
     * Gets a set containing all keys in this section.
     *  <p>
     *  If deep is set to true, then this will contain all the keys within any
     *  child {@link org.bukkit.configuration.ConfigurationSection}s (and their children, etc). These
     *  will be in a valid path notation for you to use.
     *  <p>
     *  If deep is set to false, then this will contain only the keys of any
     *  direct children, and not their own children.
     *
     *  @param path the path to get the value at
     *  @param deep Whether or not to get a deep list, as opposed to a shallow
     *      list.
     *  @return Set of keys contained within this ConfigurationSection.
     */
    Set<String> getKeys(String path, boolean deep);

    boolean isSet(String path);
}
