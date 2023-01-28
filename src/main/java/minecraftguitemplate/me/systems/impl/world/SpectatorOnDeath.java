package minecraftguitemplate.me.systems.impl.world;

import minecraftguitemplate.me.config.values.Value;
import minecraftguitemplate.me.events.event.player.PlayerDeathEvent;
import minecraftguitemplate.me.systems.Categories;
import minecraftguitemplate.me.systems.impl.Module;
import org.bukkit.GameMode;

public class SpectatorOnDeath extends Module {
    private final Value<String> deathMessage = new Value<>("DeathMessage", "Hey! you are dead!");
    public SpectatorOnDeath() {
        super("SpectatorOnDeath", Categories.WORLD, false);
        addConfig(deathMessage);
    }

    @Override
    public void onDeath(PlayerDeathEvent event) {
        event.getPlayer().setGameMode(GameMode.SPECTATOR);
        event.getPlayer().sendMessage(deathMessage.get());
    }
}
