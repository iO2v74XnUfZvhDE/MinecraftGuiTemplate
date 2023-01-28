package minecraftguitemplate.me.gui;

import minecraftguitemplate.me.utils.ItemUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.type.ChestMenu;

import java.util.Date;

public class SubPlayerMenu {
    public static void display(Player p, Player target) {
        ChestMenu page = ChestMenu.builder(3)
                .title(target.getName())
                .redraw(true)
                .build();

        page.forEach(b -> new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        page.getSlot(0).setItem(ItemUtils.getPlayerHead(target));
        page.getSlot(1).setItemTemplate(player -> ItemUtils.getNamedItem(Material.RED_STAINED_GLASS_PANE, "Gamemode", player.getGameMode().name()));
        page.getSlot(3).setItem(ItemUtils.getNamedItem(Material.ENDER_PEARL, "TP", "シフトしながらクリックで自分にtpさせる"));
        page.getSlot(3).setClickHandler((player, clickInformation) -> {
            if (clickInformation.getClickType() == ClickType.SHIFT_LEFT) {
                target.teleport(player);
            } else {
                player.teleport(target);
            }
        });

        page.getSlot(7).setItem(ItemUtils.getNamedItem(Material.FERN, "Kickする"));
        page.getSlot(7).setClickHandler((player, clickInformation) -> {
            target.kickPlayer("You have been kicked by " + player.getName());
            page.close();
        });
        page.getSlot(8).setItem(ItemUtils.getNamedItem(Material.DEAD_BUSH, "Banする"));
        page.getSlot(8).setClickHandler((player, clickInformation) -> {
            Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), "You have been banned by " + player.getName(),
                    new Date(System.currentTimeMillis()+60*1000), null);
            target.kickPlayer("You have been banned by " + player.getName());
            page.close();
        });


        page.open(p);
    }
}
