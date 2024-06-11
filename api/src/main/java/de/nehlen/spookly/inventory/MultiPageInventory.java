package de.nehlen.spookly.inventory;

import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;

import java.util.function.Consumer;

public interface MultiPageInventory extends InventoryWrapper {
    
    void add(ItemStack itemStack, HandleResult handleResult);
    void add(ItemStack itemStack, HandleResult handleResult, Slot.ClickHandler handler);
    void add(ItemStack itemStack, HandleResult handleResult, Slot.ClickHandler handler, String... arguments);
    void newMenuModifier(Consumer<Menu> consumer);
    void nextButton(ItemStack itemStack);
    void nextButtonEmpty(ItemStack itemStack);
    void nextButtonSlot(Integer slotIndex);
    void previousButton(ItemStack itemStack);
    void previousButtonEmpty(ItemStack itemStack);
    void previousButtonSlot(Integer slotIndex);

    interface MaskTemplate {
        Mask template(Menu.Dimension dimension);
    }
}
