package minecraftguitemplate.me.gui;

import minecraftguitemplate.me.systems.Categories;
import minecraftguitemplate.me.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.paginate.PaginatedMenuBuilder;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.Arrays;
import java.util.List;

public class CategoryMenu {
    private static final List<Menu> category;
    static {
        Menu.Builder<ChestMenu.Builder> pageTemplate = ChestMenu.builder(6).title("Category").redraw(true);
        Mask itemSlots = BinaryMask.builder(pageTemplate.getDimensions())
                .pattern("111111111").build();

        int length = (int) Math.ceil(Categories.values().length / 8);

        ItemStack[] itemStacks = new ItemStack[length * 9];
        Arrays.fill(itemStacks, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        category = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(itemSlots)
                .nextButton(ItemUtils.getNamedItem(Material.ARROW, "Next page"))
                .nextButtonEmpty(new ItemStack(Material.RED_STAINED_GLASS_PANE)) // Icon when no next page available
                .nextButtonSlot(53)
                .previousButton(ItemUtils.getNamedItem(Material.ARROW, "Previous page"))
                .previousButtonEmpty(new ItemStack(Material.RED_STAINED_GLASS_PANE)) // Icon when no previous page available
                .previousButtonSlot(45)
                .addItems(Arrays.asList(itemStacks))
                .build();


        for (int i = 0; i < category.size(); i++) {
            Menu slots = category.get(i);
            slots.getSlot(53).setClickHandler((player, info) -> {
                Menu clickedMenu = info.getClickedMenu();
                ItemStack item = clickedMenu.getSlot(45).getItem(player);
                if (item.getType() == Material.ARROW) {
                    int i1 = category.indexOf(clickedMenu);
                    category.get(i1 + 1).open(player);
                }
            });
            slots.getSlot(45).setClickHandler((player, info) -> {
                Menu clickedMenu = info.getClickedMenu();
                ItemStack item = clickedMenu.getSlot(45).getItem(player);
                if (item.getType() == Material.ARROW) {
                    int i1 = category.indexOf(clickedMenu);
                    category.get(i1 - 1).open(player);
                }
            });

            for (int j = 0; j < 45; j++) {
                slots.getSlot(j).setItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
            }
            for (int j = 46; j < 53; j++) {
                slots.getSlot(j).setItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
            }

            int[] categoryList = new int[]{10, 12, 14, 16, 28, 30, 32, 34};
            Categories[] values = Categories.values();

            try {
                for (int j = 0; j < categoryList.length; j++) {
                    Categories value = values[i * categoryList.length + j];
                    ItemStack itemStack = ItemUtils.getNamedItem(value.getIcon(), value.getName());
                    Slot slot = slots.getSlot(categoryList[j]);
                    slot.setItem(itemStack);
                    slot.setClickHandler(((player, clickInformation) -> {
                        ModuleMenu.display(player, value);
                    }));
                }
            } catch (IndexOutOfBoundsException ignored) {}

            ItemStack itemStack = ItemUtils.getNamedItem(Material.ARROW, "Go back");
            Slot slot = slots.getSlot(0);
            slot.setItem(itemStack);
            slot.setClickHandler(((player, clickInformation) -> {
                MainMenu.display(player);
            }));
        }
    }
    public static void display(Player player) {
        category.get(0).open(player);
    }
}
