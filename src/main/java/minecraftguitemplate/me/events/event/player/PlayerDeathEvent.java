package minecraftguitemplate.me.events.event.player;

import minecraftguitemplate.me.events.Event;
import org.bukkit.entity.Player;

public class PlayerDeathEvent extends Event {
    private final Player player;
    public PlayerDeathEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
