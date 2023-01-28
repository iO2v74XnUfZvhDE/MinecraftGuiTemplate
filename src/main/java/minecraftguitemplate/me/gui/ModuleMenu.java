package minecraftguitemplate.me.gui;

import minecraftguitemplate.me.systems.Categories;
import minecraftguitemplate.me.systems.impl.Module;
import minecraftguitemplate.me.systems.impl.ModuleManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.paginate.PaginatedMenuBuilder;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModuleMenu {
    public static void display(Player p, Categories category) {
        List<Module> target = new ArrayList<>();
        for (Module module : ModuleManager.getModuleList()) {
            if (module.getCategory() == category)
                target.add(module);
        }

        Menu.Builder<ChestMenu.Builder> pageTemplate = ChestMenu.builder(6).title("Module").redraw(true);
        Mask itemSlots = BinaryMask.builder(pageTemplate.getDimensions())
                .pattern("111111111").build();

        int length = (int) Math.ceil(target.size() / 24);

        ItemStack[] itemStacks = new ItemStack[length * 9];
        Arrays.fill(itemStacks, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        List<Menu> module = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(itemSlots)
                .nextButton(new ItemStack(Material.ARROW))
                .nextButtonEmpty(new ItemStack(Material.RED_STAINED_GLASS_PANE)) // Icon when no next page available
                .nextButtonSlot(53)
                .previousButton(new ItemStack(Material.ARROW))
                .previousButtonEmpty(new ItemStack(Material.RED_STAINED_GLASS_PANE)) // Icon when no previous page available
                .previousButtonSlot(45)
                .addItems(Arrays.asList(itemStacks))
                .build();


        for (int i = 0; i < module.size(); i++) {
            Menu slots = module.get(i);
            slots.getSlot(53).setClickHandler((player, info) -> {
                Menu clickedMenu = info.getClickedMenu();
                ItemStack item = clickedMenu.getSlot(45).getItem(player);
                if (item.getType() == Material.ARROW) {
                    int i1 = module.indexOf(clickedMenu);
                    module.get(i1 + 1).open(player);
                }
            });
            slots.getSlot(45).setClickHandler((player, info) -> {
                Menu clickedMenu = info.getClickedMenu();
                ItemStack item = clickedMenu.getSlot(45).getItem(player);
                if (item.getType() == Material.ARROW) {
                    int i1 = module.indexOf(clickedMenu);
                    module.get(i1 - 1).open(player);
                }
            });

            for (int j = 0; j < 45; j++) {
                slots.getSlot(j).setItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
            }
            for (int j = 46; j < 53; j++) {
                slots.getSlot(j).setItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
            }

            if (!target.isEmpty()) {
                try {
                    for (int j = 10; j < 34; j++) {
                        Module value = target.get(i * 24 + (j - 10));
                        Slot slot = slots.getSlot(j);
                        slot.setClickHandler(((player, clickInformation) -> {
                            if (clickInformation.getClickType() == ClickType.RIGHT) {
                                if (!value.getConfigList().isEmpty()) {
                                    ConfigMenu.display(player, value);
                                }
                            } else {
                                value.setEnabled(!value.isEnabled());
                                slots.update();
                            }
                        }));
                        slot.setItemTemplate(val -> {
                            ItemStack itemStack = new ItemStack(value.isEnabled() ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, 1);
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName(value.getName());
                            itemMeta.setLore(Collections.singletonList("Right click to configure"));
                            itemStack.setItemMeta(itemMeta);
                            return itemStack;
                        });

                        slots.update();
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            } else {
                for (int j = 10; j < 34; j++) {
                    ItemStack itemStack = new ItemStack(Material.GRASS, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("No modules found.");
                    itemStack.setItemMeta(itemMeta);
                    slots.getSlot(j).setItem(itemStack);
                }
            }

            ItemStack itemStack = new ItemStack(Material.ARROW, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("Go back");
            itemStack.setItemMeta(itemMeta);
            Slot slot = slots.getSlot(0);
            slot.setItem(itemStack);
            slot.setClickHandler(((player, clickInformation) -> {
                CategoryMenu.display(p);
            }));
        }

        module.get(0).open(p);
    }
}
