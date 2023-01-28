package minecraftguitemplate.me.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {
    public static List<Player> getAllPlayer() {
        List<Player> target = new ArrayList<>();
        target.addAll(Bukkit.getOnlinePlayers());
        return target;
    }
}
