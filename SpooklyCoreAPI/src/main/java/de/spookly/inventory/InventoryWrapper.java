package de.spookly.inventory;

import org.bukkit.entity.Player;

/**
 * Interface representing a wrapper for an inventory in the Spookly system.
 */
public interface InventoryWrapper {

    /**
     * Opens the inventory for the player.
     */
    void open();

    /**
     * Gets the player associated with this inventory.
     *
     * @return the player associated with this inventory.
     */
    Player player();
}
