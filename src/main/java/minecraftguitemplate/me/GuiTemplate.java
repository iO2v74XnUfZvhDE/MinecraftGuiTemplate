package minecraftguitemplate.me;

import minecraftguitemplate.me.config.ConfigManager;
import minecraftguitemplate.me.systems.impl.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GuiTemplate extends JavaPlugin {
    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getLogger().info("By @iO2v74XnUfZvhDE");

        saveDefaultConfig();

        ModuleManager.startup(); // Plz call this startup before Config-manager startup call
        ConfigManager.saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Confirmed :)");
        saveConfig();
    }
}
