package minecraftguitemplate.me;

import minecraftguitemplate.me.config.ConfigManager;
import minecraftguitemplate.me.gui.CategoryMenu;
import minecraftguitemplate.me.systems.Categories;
import minecraftguitemplate.me.systems.impl.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;
import org.jetbrains.annotations.NotNull;

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
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        ModuleManager.startup(); // Plz call this startup before Config-manager startup call
        ConfigManager.saveConfig(); // Init
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Confirmed :)");
        saveConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("showgui")) {
            if (getConfig().getBoolean("requireop") && !sender.isOp()) {
                sender.sendMessage("You didnt have op");
                return false;
            }

            if (sender instanceof Player) {
                CategoryMenu.display((Player) sender);
                return true;
            }
            sender.sendMessage("Sorry, You must be a player to use this command.");
        }
        return false;
    }
}
