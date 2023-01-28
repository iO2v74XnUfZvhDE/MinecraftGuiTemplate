package minecraftguitemplate.me.gui;

import minecraftguitemplate.me.utils.ItemUtils;
import minecraftguitemplate.me.utils.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.paginate.PaginatedMenuBuilder;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.*;

public class PlayerMenu {
    public static void display(Player p) {
        List<Player> target = PlayerUtils.getAllPlayer();
        target.remove(p);

        Menu.Builder<ChestMenu.Builder> pageTemplate = ChestMenu.builder(6).title("Player").redraw(true);
        Mask itemSlots = BinaryMask.builder(pageTemplate.getDimensions())
                .pattern("111111111").build();

        int length = (int) Math.ceil(target.size() / 24);

        ItemStack[] itemStacks = new ItemStack[length * 9];
        Arrays.fill(itemStacks, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        List<Menu> module = PaginatedMenuBuilder.builder(pageTemplate)
                .slots(itemSlots)
                .nextButton(ItemUtils.getNamedItem(Material.ARROW, "Next page"))
                .nextButtonEmpty(new ItemStack(Material.RED_STAINED_GLASS_PANE)) // Icon when no next page available
                .nextButtonSlot(53)
                .previousButton(ItemUtils.getNamedItem(Material.ARROW, "Previous page"))
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

            try {
                for (int j = 10; j < 34; j++) {
                    Slot slot = slots.getSlot(j);
                    Player player1 = target.get(i * 24 + (j - 10));
                    slot.setItem(ItemUtils.getPlayerHead(player1));
                    slot.setClickHandler(((player, clickInformation) -> {
                        SubPlayerMenu.display(player, player1);
                    }));
                }
            } catch (IndexOutOfBoundsException ignored) {
            }

            ItemStack itemStack = ItemUtils.getNamedItem(Material.ARROW, "Go back");
            Slot slot = slots.getSlot(0);
            slot.setItem(itemStack);
            slot.setClickHandler(((player, clickInformation) -> MainMenu.display(p)));

            slots.getSlot(3).setItem(ItemUtils.getNamedItem(Material.GLISTERING_MELON_SLICE, "全員サバイバル", "シフトしながらクリックで自分含め全員サバイバル"));
            slots.getSlot(3).setClickHandler((player, clickInformation) -> {
                List<Player> allPlayer = PlayerUtils.getAllPlayer();
                if (clickInformation.getClickType() != ClickType.SHIFT_LEFT) {
                    allPlayer.remove(player);
                }
                allPlayer.forEach(player1 -> {
                    player1.setGameMode(GameMode.SURVIVAL);
                });
            });
            slots.getSlot(4).setItem(ItemUtils.getNamedItem(Material.ENDER_EYE, "全員自分にtp"));
            slots.getSlot(4).setClickHandler((player, clickInformation) -> {
                List<Player> allPlayer = PlayerUtils.getAllPlayer();
                allPlayer.forEach(player1 -> {
                    player1.teleport(player);
                });
            });
            slots.getSlot(5).setItem(ItemUtils.getNamedItem(Material.POTION, "全員スペクテイター", "シフトしながらクリックで自分含め全員サバイバル"));
            slots.getSlot(5).setClickHandler((player, clickInformation) -> {
                List<Player> allPlayer = PlayerUtils.getAllPlayer();
                if (clickInformation.getClickType() != ClickType.SHIFT_LEFT) {
                    allPlayer.remove(player);
                }
                allPlayer.forEach(player1 -> {
                    player1.setGameMode(GameMode.SPECTATOR);
                });
            });

            slots.getSlot(8).setItem(ItemUtils.getNamedItem(Material.EMERALD_BLOCK, "Refresh"));
            slots.getSlot(8).setClickHandler((player, clickInformation) -> {
                clickInformation.getClickedMenu().close();
                display(player);
            });
        }

        module.get(0).open(p);
    }
}
