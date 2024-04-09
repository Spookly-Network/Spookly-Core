package de.nehlen.spookly.inventory;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.ClickOptions;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.type.ChestMenu;

@Accessors(chain = false, fluent = true)
public abstract class AbstractSinglePageInventory implements SinglePageInventory {

    @Getter
    private Menu menu;
    protected Player player;

    public AbstractSinglePageInventory(Integer rows, String title, Player player) {
        this.menu = ChestMenu.builder(rows).title(title).build();
        this.player = player;
    }

    public void open() {
        menu.open(player());
    }

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

    public Player player() {
        return this.player;
    }
}
