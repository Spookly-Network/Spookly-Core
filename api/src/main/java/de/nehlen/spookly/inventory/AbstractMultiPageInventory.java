package de.nehlen.spookly.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.paginate.PaginatedMenuBuilder;
import org.ipvp.canvas.slot.ClickOptions;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.type.ChestMenu;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractMultiPageInventory implements MultiPageInventory {
    private PaginatedMenuBuilder switcherInventoryPages;
    protected Player player;

    public AbstractMultiPageInventory(Integer rows, Component title, Player player) {
        Menu.Builder pageTemplate = ChestMenu.builder(rows).title(title).redraw(true);
        BinaryMask.Builder itemSlots = BinaryMask.builder(pageTemplate.getDimensions());

        for (int i = 1; i <= rows; i++) {
            itemSlots = itemSlots.row(i).pattern("111111111");
        };

        this.player = player;
        switcherInventoryPages = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(itemSlots.build());
    }

    public AbstractMultiPageInventory(Integer rows, Component title, MaskTemplate maskTemplate, Player player) {
        Menu.Builder pageTemplate = ChestMenu.builder(rows).title(title).redraw(true);

        this.player = player;
        switcherInventoryPages = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(maskTemplate.template(pageTemplate.getDimensions()));
    }

    public void open() {
        List<Menu> pages = switcherInventoryPages.build();
        pages.get(0).open(player());
    }

    public void add(ItemStack itemStack, HandleResult handleResult, Slot.ClickHandler handler, String... arguments) {
        ClickOptions.Builder clickOptions = ClickOptions.builder();
        if (handleResult.equals(HandleResult.ALLOW_GRABBING)) {
            clickOptions = clickOptions.allow(ClickType.LEFT, ClickType.RIGHT)
                    .allow(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME);
        }
        switcherInventoryPages = switcherInventoryPages.addItem(SlotSettings.builder()
                .clickHandler(handler)
                .clickOptions(clickOptions.build())
                .arguments(arguments)
                .item(itemStack).build());
    }

    public void add(ItemStack itemStack, HandleResult handleResult, Slot.ClickHandler handler) {
        ClickOptions.Builder clickOptions = ClickOptions.builder();
        if (handleResult.equals(HandleResult.ALLOW_GRABBING)) {
            clickOptions = clickOptions.allow(ClickType.LEFT, ClickType.RIGHT)
                    .allow(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME);
        }
        switcherInventoryPages = switcherInventoryPages.addItem(SlotSettings.builder()
                .clickHandler(handler)
                .clickOptions(clickOptions.build())
                .item(itemStack).build());
    }

    public void add(ItemStack itemStack, HandleResult handleResult) {
        ClickOptions.Builder clickOptions = ClickOptions.builder();
        if (handleResult.equals(HandleResult.ALLOW_GRABBING)) {
            clickOptions = clickOptions.allow(ClickType.LEFT, ClickType.RIGHT)
                    .allow(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME);
        }
        switcherInventoryPages = switcherInventoryPages.addItem(SlotSettings.builder()
                .clickOptions(clickOptions.build())
                .item(itemStack).build());
    }

    public void newMenuModifier(Consumer<Menu> consumer) {
        switcherInventoryPages = switcherInventoryPages.newMenuModifier(consumer);
    }

    public void nextButton(ItemStack itemStack) {
        switcherInventoryPages = switcherInventoryPages.nextButton(itemStack);
    }

    public void nextButtonEmpty(ItemStack itemStack) {
        switcherInventoryPages = switcherInventoryPages.nextButtonEmpty(itemStack);
    }

    public void nextButtonSlot(Integer slotIndex) {
        switcherInventoryPages = switcherInventoryPages.nextButtonSlot(slotIndex);
    }


    public void previousButton(ItemStack itemStack) {
        switcherInventoryPages = switcherInventoryPages.previousButton(itemStack);
    }

    public void previousButtonEmpty(ItemStack itemStack) {
        switcherInventoryPages = switcherInventoryPages.previousButtonEmpty(itemStack);
    }

    public void previousButtonSlot(Integer slotIndex) {
        switcherInventoryPages = switcherInventoryPages.previousButtonSlot(slotIndex);
    }

    public Player player() {
        return this.player;
    }

    public interface MaskTemplate {
        Mask template(Menu.Dimension dimension);
    }
}