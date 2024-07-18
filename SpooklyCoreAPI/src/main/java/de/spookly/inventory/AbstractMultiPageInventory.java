package de.spookly.inventory;

import java.util.List;
import java.util.function.Consumer;

import net.kyori.adventure.text.Component;

import de.spookly.canvas.Menu;
import de.spookly.canvas.mask.BinaryMask;
import de.spookly.canvas.mask.Mask;
import de.spookly.canvas.paginate.PaginatedMenuBuilder;
import de.spookly.canvas.paginate.PaginatedMenuTitles;
import de.spookly.canvas.slot.ClickOptions;
import de.spookly.canvas.slot.Slot;
import de.spookly.canvas.slot.SlotSettings;
import de.spookly.canvas.type.ChestMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract class representing a multi-page inventory in the Spookly system.
 */
public abstract class AbstractMultiPageInventory implements MultiPageInventory {

    private PaginatedMenuBuilder switcherInventoryPages;
    private Player player;

    /**
     * Constructs a new multi-page inventory with the specified number of rows, title, and player.
     *
     * @param rows   the number of rows in the inventory.
     * @param title  the title of the inventory.
     * @param player the player associated with the inventory.
     */
    public AbstractMultiPageInventory(Integer rows, Component title, Player player) {
        Menu.Builder pageTemplate = ChestMenu.builder(rows).title(title).redraw(true);
        BinaryMask.Builder itemSlots = BinaryMask.builder(pageTemplate.getDimensions());
        for (int i = 1; i <= rows; i++) {
            itemSlots = itemSlots.row(i).pattern("111111111");
        }

        this.player = player;
        switcherInventoryPages = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(itemSlots.build());
    }

    /**
     * Constructs a new multi-page inventory with the specified number of rows, title, mask template, and player.
     *
     * @param rows        the number of rows in the inventory.
     * @param title       the title of the inventory.
     * @param maskTemplate the mask template for the inventory slots.
     * @param player      the player associated with the inventory.
     */
    public AbstractMultiPageInventory(Integer rows, Component title, MaskTemplate maskTemplate, Player player) {
        Menu.Builder pageTemplate = ChestMenu.builder(rows).title(title).redraw(true);

        this.player = player;
        switcherInventoryPages = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(maskTemplate.template(pageTemplate.getDimensions()));
    }

    /**
     * Opens the first page of the multi-page inventory for the player.
     */
    public void open() {
        List<Menu> pages = switcherInventoryPages.build();
        pages.get(0).open(player());
    }

    /**
     * Adds an item to the inventory with a handle result, click handler, and additional arguments.
     *
     * @param itemStack    the item stack to add.
     * @param handleResult the result to handle.
     * @param handler      the click handler for the slot.
     * @param arguments    additional arguments for the handler.
     */
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

    /**
     * Adds an item to the inventory with a handle result and click handler.
     *
     * @param itemStack    the item stack to add.
     * @param handleResult the result to handle.
     * @param handler      the click handler for the slot.
     */
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

    /**
     * Adds an item to the inventory with a handle result.
     *
     * @param itemStack    the item stack to add.
     * @param handleResult the result to handle.
     */
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

    public void titles(PaginatedMenuTitles titles) {
        switcherInventoryPages = switcherInventoryPages.paginateMenuTitles(titles);
    }

    /**
     * Adds a new menu modifier to the inventory.
     *
     * @param consumer the consumer to modify the menu.
     */
    public void newMenuModifier(Consumer<Menu> consumer) {
        switcherInventoryPages = switcherInventoryPages.newMenuModifier(consumer);
    }

    /**
     * Sets the item stack for the next button.
     *
     * @param itemStack the item stack for the next button.
     */
    public void nextButton(ItemStack itemStack) {
        switcherInventoryPages = switcherInventoryPages.nextButton(itemStack);
    }

    /**
     * Sets the item stack for the empty next button.
     *
     * @param itemStack the item stack for the empty next button.
     */
    public void nextButtonEmpty(ItemStack itemStack) {
        switcherInventoryPages = switcherInventoryPages.nextButtonEmpty(itemStack);
    }

    /**
     * @deprecated in favour of {@link #nextButtonSlots(Integer...)} to integrate new canvas api
     * Sets the slot index for the next button.
     *
     * @param slotIndex the slot index for the next button.
     */
    @Deprecated
    public void nextButtonSlot(Integer slotIndex) {
        nextButtonSlots(slotIndex);
    }

    /**
     * Sets the slot index for the next button.
     *
     * @param slotIndexes the slot index for the next button.
     */
    public void nextButtonSlots(Integer... slotIndexes) {
        switcherInventoryPages = switcherInventoryPages.nextButtonSlots(slotIndexes);
    }

    /**
     * Sets the item stack for the previous button.
     *
     * @param itemStack the item stack for the previous button.
     */
    public void previousButton(ItemStack itemStack) {
        switcherInventoryPages = switcherInventoryPages.previousButton(itemStack);
    }

    /**
     * Sets the item stack for the empty previous button.
     *
     * @param itemStack the item stack for the empty previous button.
     */
    public void previousButtonEmpty(ItemStack itemStack) {
        switcherInventoryPages = switcherInventoryPages.previousButtonEmpty(itemStack);
    }

    /**
     * @deprecated in favour of {@link #previousButtonSlots(Integer...)} to integrate new canvas api
     * Sets the slot index for the previous button.
     *
     * @param slotIndexes the slot indexes for the previous button.
     */
    @Deprecated
    public void previousButtonSlot(Integer slotIndexes) {
        previousButtonSlots(slotIndexes);
    }

    /**
     * Sets the slot index for the previous button.
     *
     * @param slotIndex the slot index for the previous button.
     */
    public void previousButtonSlots(Integer... slotIndex) {
        switcherInventoryPages = switcherInventoryPages.previousButtonSlots(slotIndex);
    }

    /**
     * Gets the player associated with this inventory.
     *
     * @return the player.
     */
    public Player player() {
        return this.player;
    }

    /**
     * Interface representing a mask template for the inventory.
     */
    public interface MaskTemplate {

        /**
         * Creates a mask template for the specified menu dimension.
         *
         * @param dimension the menu dimension.
         * @return the mask template.
         */
        Mask template(Menu.Dimension dimension);
    }
}
