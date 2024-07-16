package de.spookly.inventory;

import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.Slot;

/**
 * Interface representing a single-page inventory in the Spookly system.
 */
public interface SinglePageInventory extends InventoryWrapper {

    /**
     * Sets an item in the inventory at the specified slot index with a handle result.
     *
     * @param stack        the item stack to set.
     * @param slotIndex    the slot index where the item should be placed.
     * @param handleResult the result to handle.
     */
    void set(ItemStack stack, Integer slotIndex, HandleResult handleResult);

    /**
     * Sets an item in the inventory at the specified slot index with a handle result and click handler.
     *
     * @param stack        the item stack to set.
     * @param slotIndex    the slot index where the item should be placed.
     * @param handleResult the result to handle.
     * @param handler      the click handler for the slot.
     */
    void set(ItemStack stack, Integer slotIndex, HandleResult handleResult, Slot.ClickHandler handler);

    /**
     * Sets an item in the inventory at the specified slot index with a handle result, click handler, and additional arguments.
     *
     * @param stack        the item stack to set.
     * @param slotIndex    the slot index where the item should be placed.
     * @param handleResult the result to handle.
     * @param handler      the click handler for the slot.
     * @param arguments    additional arguments for the handler.
     */
    void set(ItemStack stack, Integer slotIndex, HandleResult handleResult, Slot.ClickHandler handler, String... arguments);

    /**
     * Gets the menu associated with this inventory.
     *
     * @return the menu.
     */
    Menu menu();
}
