package minecraftguitemplate.me.gui;

import minecraftguitemplate.me.systems.Categories;
import minecraftguitemplate.me.systems.impl.Module;
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

        int length = (int) Math.ceil(Categories.values().length / 9);

        ItemStack[] itemStacks = new ItemStack[length * 9];
        Arrays.fill(itemStacks, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        category = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(itemSlots)
                .nextButton(new ItemStack(Material.ARROW))
                .nextButtonEmpty(new ItemStack(Material.RED_STAINED_GLASS_PANE)) // Icon when no next page available
                .nextButtonSlot(53)
                .previousButton(new ItemStack(Material.ARROW))
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
                    ItemStack itemStack = new ItemStack(value.getIcon(), 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(value.getName());
                    itemStack.setItemMeta(itemMeta);
                    Slot slot = slots.getSlot(categoryList[j]);
                    slot.setItem(itemStack);
                    slot.setClickHandler(((player, clickInformation) -> {
                        ModuleMenu.display(player, value);
                    }));
                }
            } catch (IndexOutOfBoundsException ignored) {}


        }
    }
    public static void display(Player player) {
        category.get(0).open(player);
    }
}
