package minecraftguitemplate.me.events.listener;

import minecraftguitemplate.me.utils.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    private void onChat(PlayerChatEvent event) {
        if (ChatUtils.run(event.getPlayer(), event.getMessage())) {
            event.setCancelled(true);
        }
    }
}
