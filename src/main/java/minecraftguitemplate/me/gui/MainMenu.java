package minecraftguitemplate.me.gui;

import minecraftguitemplate.me.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

public class MainMenu {
    public static void display(Player p) {
        ChestMenu page = ChestMenu.builder(6)
                .title("MainMenu")
                .redraw(true)
                .build();

        page.forEach(e -> {
            e.setItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        });

        page.getSlot(0).setItem(ItemUtils.getPlayerHead(p));
        Slot slot10= page.getSlot(10);
        slot10.setItem(ItemUtils.getNamedItem(Material.DEBUG_STICK, "Utility", "便利な機能を提供する様々な機能があります。"));
        Slot slot12 = page.getSlot(12);
        slot12.setItem(ItemUtils.getNamedItem(Material.ENDER_PEARL, "TP MENU",
                "TPするためのメニュー", "/tpmenuコマンドでも使用できます。", "/tpmenu <player>によってそのプレイヤーの個別メニューを開くことが可能です。"));

        slot10.setClickHandler(((player, clickInformation) -> {
            CategoryMenu.display(player);
        }));
        slot12.setClickHandler((player, clickInformation) -> {
            TeleportMenu.display(player);
        });

        page.open(p);
    }
}
