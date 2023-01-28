package minecraftguitemplate.me.systems.impl.world;

import minecraftguitemplate.me.config.values.Value;
import minecraftguitemplate.me.systems.Categories;
import minecraftguitemplate.me.systems.impl.Module;

public class SpectatorOnDeath extends Module {
    private final Value<String> deathMessage = new Value<>("DeathMessage", "Hey! you are dead!");
    public SpectatorOnDeath() {
        super("SpectatorOnDeath", Categories.WORLD, false);
        addConfig(deathMessage);
    }
}
