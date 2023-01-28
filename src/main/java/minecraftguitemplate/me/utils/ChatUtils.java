package minecraftguitemplate.me.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ChatUtils {
    private static final HashMap<Player, Consumer<String>> task = new HashMap<>();
    public static void addTask(Player p, Consumer<String> r) {
        task.put(p, r);
    }

    public static boolean run(Player p, String txt) {
        AtomicBoolean state = new AtomicBoolean(false);
        task.forEach((player, r) -> {
            if (p.equals(player)) {
                r.accept(txt);
                state.set(true);
            }
        });

        task.remove(p);
        return state.get();
    }
}
