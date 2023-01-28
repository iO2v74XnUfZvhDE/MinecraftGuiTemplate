package minecraftguitemplate.me.systems;

import org.bukkit.Material;

public enum Categories {
    WORLD("World", Material.GRASS_BLOCK),
    UTILITY("Utility", Material.DEBUG_STICK),
    ;

    private final String name;
    private final Material icon;
    Categories(String name, Material icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }
}
