package de.spookly.inventory;

import java.util.function.Consumer;

import de.spookly.canvas.Menu;
import de.spookly.canvas.mask.Mask;
import de.spookly.canvas.slot.Slot;

import org.bukkit.inventory.ItemStack;

/**
 * Interface representing a multi-page inventory in the Spookly system.
 */
public interface MultiPageInventory extends InventoryWrapper {

    /**
     * Adds an item to the inventory with a handle result.
     *
     * @param itemStack    the item stack to add.
     * @param handleResult the result to handle.
     */
    void add(ItemStack itemStack, HandleResult handleResult);

    /**
     * Adds an item to the inventory with a handle result and click handler.
     *
     * @param itemStack    the item stack to add.
     * @param handleResult the result to handle.
     * @param handler      the click handler for the slot.
     */
    void add(ItemStack itemStack, HandleResult handleResult, Slot.ClickHandler handler);

    /**
     * Adds an item to the inventory with a handle result, click handler, and additional arguments.
     *
     * @param itemStack    the item stack to add.
     * @param handleResult the result to handle.
     * @param handler      the click handler for the slot.
     * @param arguments    additional arguments for the handler.
     */
    void add(ItemStack itemStack, HandleResult handleResult, Slot.ClickHandler handler, String... arguments);

    /**
     * Modifies the menu with a specified consumer.
     *
     * @param consumer the consumer to modify the menu.
     */
    void newMenuModifier(Consumer<Menu> consumer);

    /**
     * Sets the item stack for the next button.
     *
     * @param itemStack the item stack for the next button.
     */
    void nextButton(ItemStack itemStack);

    /**
     * Sets the item stack for the empty next button.
     *
     * @param itemStack the item stack for the empty next button.
     */
    void nextButtonEmpty(ItemStack itemStack);

    /**
     * Sets the slot index for the next button.
     *
     * @param slotIndex the slot index for the next button.
     */
    void nextButtonSlot(Integer slotIndex);

    /**
     * Sets the item stack for the previous button.
     *
     * @param itemStack the item stack for the previous button.
     */
    void previousButton(ItemStack itemStack);

    /**
     * Sets the item stack for the empty previous button.
     *
     * @param itemStack the item stack for the empty previous button.
     */
    void previousButtonEmpty(ItemStack itemStack);

    /**
     * Sets the slot index for the previous button.
     *
     * @param slotIndex the slot index for the previous button.
     */
    void previousButtonSlot(Integer slotIndex);

    /**
     * Interface representing a mask template for the inventory.
     */
    interface MaskTemplate {

        /**
         * Creates a mask template for the specified menu dimension.
         *
         * @param dimension the menu dimension.
         * @return the mask template.
         */
        Mask template(Menu.Dimension dimension);
    }
}
