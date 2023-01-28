package minecraftguitemplate.me;

import minecraftguitemplate.me.config.ConfigManager;
import minecraftguitemplate.me.events.listener.ListenerManager;
import minecraftguitemplate.me.gui.CategoryMenu;
import minecraftguitemplate.me.gui.MainMenu;
import minecraftguitemplate.me.gui.SubTeleportMenu;
import minecraftguitemplate.me.gui.TeleportMenu;
import minecraftguitemplate.me.systems.impl.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
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
        ModuleManager.startup(); // Plz call this startup before Config-manager startup call
        ConfigManager.loadConfig(); // Init
        ListenerManager.startup();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Confirmed :)");
        ConfigManager.saveConfig();
        saveConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mainmenu")) {
            if (getConfig().getBoolean("requireop") && !sender.isOp()) {
                sender.sendMessage("You didnt have op");
                return false;
            }

            if (sender instanceof Player p) {
                MainMenu.display(p);
                return true;
            }
            sender.sendMessage("Sorry, You must be a player to use this command.");
        } else if (command.getName().equalsIgnoreCase("tpmenu")) {
            if (getConfig().getBoolean("requireop") && !sender.isOp()) {
                sender.sendMessage("You didnt have op");
                return false;
            }

            if (sender instanceof Player p) {
                if (args.length == 0)
                    TeleportMenu.display(p);
                else
                    SubTeleportMenu.display(p, Bukkit.getPlayer(args[0]));
                return true;
            }
            sender.sendMessage("Sorry, You must be a player to use this command.");
        }
        return false;
    }
}
