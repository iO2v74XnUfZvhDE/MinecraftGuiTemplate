package minecraftguitemplate.me.events.listener;

import minecraftguitemplate.me.systems.impl.Module;
import minecraftguitemplate.me.systems.impl.ModuleManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        for (Module module : ModuleManager.getModuleList()) {
            if (module.isEnabled()) {
                module.onDeath(new minecraftguitemplate.me.events.event.player.PlayerDeathEvent(event.getEntity()));
            }
        }
    }
}
