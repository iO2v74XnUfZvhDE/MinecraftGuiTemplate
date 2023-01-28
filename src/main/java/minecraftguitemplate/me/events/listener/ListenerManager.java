package minecraftguitemplate.me.events.listener;

import minecraftguitemplate.me.GuiTemplate;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.ipvp.canvas.MenuFunctionListener;

public class ListenerManager {
    public static void startup() {
        register(
                new MenuFunctionListener(),
                new ChatListener(),
                new DeathListener()
        );
    }

    public static void register(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, GuiTemplate.getInstance());
        }
    }
}
