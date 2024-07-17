package de.spookly.inventory;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.ClickOptions;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.type.ChestMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract class representing a single-page inventory in the Spookly system.
 */
@Accessors(chain = false, fluent = true)
public abstract class AbstractSinglePageInventory implements SinglePageInventory {

    @Getter
    private Menu menu;
    private Player player;

    /**
     * Constructs a new single-page inventory with the specified number of rows, title, and player.
     *
     * @param rows   the number of rows in the inventory.
     * @param title  the title of the inventory.
     * @param player the player associated with the inventory.
     */
    public AbstractSinglePageInventory(Integer rows, Component title, Player player) {
        this.menu = ChestMenu.builder(rows).title(title).build();
        this.player = player;
    }

    /**
     * Opens the inventory for the player.
     */
    public void open() {
        menu.open(player());
    }

    /**
     * Sets an item in the inventory at the specified slot index with a handle result, click handler, and additional arguments.
     *
     * @param itemStack    the item stack to set.
     * @param slotIndex    the slot index where the item should be placed.
     * @param handleResult the result to handle.
     * @param handler      the click handler for the slot.
     * @param arguments    additional arguments for the handler.
     */
    public void set(ItemStack itemStack, Integer slotIndex, HandleResult handleResult, Slot.ClickHandler handler, String... arguments) {
        ClickOptions.Builder clickOptions = ClickOptions.builder();
        Slot slot = menu.getSlot(slotIndex);

        if (handleResult.equals(HandleResult.ALLOW_GRABBING)) {
            clickOptions = clickOptions.allow(ClickType.LEFT, ClickType.RIGHT)
                    .allow(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME);
        }

        slot.setSettings(SlotSettings.builder()
                .clickHandler(handler)
                .clickOptions(clickOptions.build())
                .arguments(arguments)
                .item(itemStack).build());
    }

    /**
     * Sets an item in the inventory at the specified slot index with a handle result and click handler.
     *
     * @param itemStack    the item stack to set.
     * @param slotIndex    the slot index where the item should be placed.
     * @param handleResult the result to handle.
     * @param handler      the click handler for the slot.
     */
    public void set(ItemStack itemStack, Integer slotIndex, HandleResult handleResult, Slot.ClickHandler handler) {
        ClickOptions.Builder clickOptions = ClickOptions.builder();
        Slot slot = menu.getSlot(slotIndex);

        if (handleResult.equals(HandleResult.ALLOW_GRABBING)) {
            clickOptions = clickOptions.allow(ClickType.LEFT, ClickType.RIGHT)
                    .allow(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME);
        }

        slot.setSettings(SlotSettings.builder()
                .clickHandler(handler)
                .clickOptions(clickOptions.build())
                .item(itemStack).build());
    }

    /**
     * Sets an item in the inventory at the specified slot index with a handle result.
     *
     * @param itemStack    the item stack to set.
     * @param slotIndex    the slot index where the item should be placed.
     * @param handleResult the result to handle.
     */
    public void set(ItemStack itemStack, Integer slotIndex, HandleResult handleResult) {
        ClickOptions.Builder clickOptions = ClickOptions.builder();
        Slot slot = menu.getSlot(slotIndex);

        if (handleResult.equals(HandleResult.ALLOW_GRABBING)) {
            clickOptions = clickOptions.allow(ClickType.LEFT, ClickType.RIGHT)
                    .allow(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME);
        }

        slot.setSettings(SlotSettings.builder()
                .clickOptions(clickOptions.build())
                .item(itemStack).build());
    }

    /**
     * Gets the player associated with this inventory.
     *
     * @return the player.
     */
    public Player player() {
        return this.player;
    }
}
