package minecraftguitemplate.me.gui;

import minecraftguitemplate.me.config.values.Value;
import minecraftguitemplate.me.systems.impl.Module;
import minecraftguitemplate.me.utils.ChatUtils;
import minecraftguitemplate.me.utils.ItemUtils;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigMenu {
    public static void display(Player p, Module m) {
        List<Value<?>> configList = m.getConfigList();
        Menu.Builder<ChestMenu.Builder> pageTemplate = ChestMenu.builder(6).title("Config").redraw(true);
        Mask itemSlots = BinaryMask.builder(pageTemplate.getDimensions())
                .pattern("111111111").build();

        int length = (int) Math.ceil(configList.size() / 24);

        ItemStack[] itemStacks = new ItemStack[length * 9];
        Arrays.fill(itemStacks, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        List<Menu> configmenu = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(itemSlots)
                .nextButton(ItemUtils.getNamedItem(Material.ARROW, "Next page"))
                .nextButtonEmpty(new ItemStack(Material.RED_STAINED_GLASS_PANE)) // Icon when no next page available
                .nextButtonSlot(53)
                .previousButton(ItemUtils.getNamedItem(Material.ARROW, "Previous page"))
                .previousButtonEmpty(new ItemStack(Material.RED_STAINED_GLASS_PANE)) // Icon when no previous page available
                .previousButtonSlot(45)
                .addItems(Arrays.asList(itemStacks))
                .build();

        for (int i = 0; i < configmenu.size(); i++) {
            Menu slots = configmenu.get(i);
            slots.getSlot(53).setClickHandler((player, info) -> {
                Menu clickedMenu = info.getClickedMenu();
                ItemStack item = clickedMenu.getSlot(45).getItem(player);
                if (item.getType() == Material.ARROW) {
                    int i1 = configmenu.indexOf(clickedMenu);
                    configmenu.get(i1 + 1).open(player);
                }
            });
            slots.getSlot(45).setClickHandler((player, info) -> {
                Menu clickedMenu = info.getClickedMenu();
                ItemStack item = clickedMenu.getSlot(45).getItem(player);
                if (item.getType() == Material.ARROW) {
                    int i1 = configmenu.indexOf(clickedMenu);
                    configmenu.get(i1 - 1).open(player);
                }
            });

            for (int j = 0; j < 45; j++) {
                slots.getSlot(j).setItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
            }
            for (int j = 46; j < 53; j++) {
                slots.getSlot(j).setItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
            }

            try {
                for (int j = 10; j < 34; j++) {
                    Value<?> value = configList.get(i * 24 + (j - 10));
                    Slot slot = slots.getSlot(j);
                    slot.setClickHandler(((player, clickInformation) -> {
                        slots.close();
                        if (value.get() instanceof String) {
                            player.sendMessage("??????????????????????????????????????????????????????????????????????cancel??????????????????????????????");
                            ChatUtils.addTask(player, t -> {
                                if (!t.equalsIgnoreCase("?cancel")) {
                                    Value<String> val = (Value<String>) value;
                                    val.set(t);
                                    player.sendMessage("?????????????????????");
                                } else {
                                    player.sendMessage("??????????????????????????????");
                                }
                                slots.open(player);
                            });
                        } else if (value.get() instanceof Integer) {
                            player.sendMessage("???????????????????????????????????????????????????????????????????cancel??????????????????????????????");
                            ChatUtils.addTask(player, t -> {
                                if (!t.equalsIgnoreCase("?cancel")) {
                                    Value<Integer> val = (Value<Integer>) value;

                                    try {
                                        val.set(Integer.valueOf(t));
                                        player.sendMessage("?????????????????????");
                                    } catch (ArithmeticException ignored) {
                                        player.sendMessage("??????????????????????????????");
                                    }
                                } else {
                                    player.sendMessage("??????????????????????????????");
                                }
                                slots.open(player);
                            });
                        } else if (value.get() instanceof Double) {
                            player.sendMessage("???????????????????????????????????????????????????????????????????cancel??????????????????????????????");
                            ChatUtils.addTask(player, t -> {
                                if (!t.equalsIgnoreCase("?cancel")) {
                                    Value<Double> val = (Value<Double>) value;

                                    try {
                                        val.set(Double.valueOf(t));
                                        player.sendMessage("?????????????????????");
                                    } catch (ArithmeticException ignored) {
                                        player.sendMessage("??????????????????????????????");
                                    }
                                } else {
                                    player.sendMessage("??????????????????????????????");
                                }
                                slots.open(player);
                            });
                        } else if (value.get() instanceof Boolean) {
                            player.sendMessage("true?????????false?????????????????????????????????????????????????????????????cancel??????????????????????????????");
                            ChatUtils.addTask(player, t -> {
                                if (!t.equalsIgnoreCase("?cancel")) {
                                    Value<Boolean> val = (Value<Boolean>) value;
                                    val.set(Boolean.valueOf(t));
                                    player.sendMessage("?????????????????????");
                                } else {
                                    player.sendMessage("??????????????????????????????");
                                }
                                slots.open(player);
                            });
                        }
                    }));
                    slot.setItemTemplate(val -> {
                        Material material = Material.DIRT;
                        Object obj = "";
                        if (value.get() instanceof String str) {
                            obj = str;
                            material = Material.BLUE_STAINED_GLASS_PANE;
                        } else if (value.get() instanceof Integer integer) {
                            obj = integer;
                            material = Material.ORANGE_STAINED_GLASS_PANE;
                        } else if (value.get() instanceof Double floatDec) {
                            obj = floatDec;
                            material = Material.BROWN_STAINED_GLASS_PANE;
                        } else if (value.get() instanceof Boolean bool) {
                            obj = bool;
                            material = bool ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
                        }

                        ItemStack itemStack = ItemUtils.getNamedItem(material, value.getName(), obj.toString());
                        return itemStack;
                    });

                    slots.update();
                }
            } catch (IndexOutOfBoundsException ignored) {
            }

            ItemStack itemStack = ItemUtils.getNamedItem(Material.ARROW, "Go back");
            Slot slot = slots.getSlot(0);
            slot.setItem(itemStack);
            slot.setClickHandler(((player, clickInformation) -> {
                MainMenu.display(player);
            }));
        }

        configmenu.get(0).open(p);
    }
}
