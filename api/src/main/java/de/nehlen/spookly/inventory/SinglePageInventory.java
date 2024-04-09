package de.nehlen.spookly.inventory;

import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.Slot;

public interface SinglePageInventory extends InventoryWrapper {
    void set(ItemStack stack, Integer slotIndex, HandleResult handleResult);
    void set(ItemStack stack, Integer slotIndex, HandleResult handleResult, Slot.ClickHandler handler);
    void set(ItemStack stack, Integer slotIndex, HandleResult handleResult, Slot.ClickHandler handler, String... arguments);
    Menu menu();
}
