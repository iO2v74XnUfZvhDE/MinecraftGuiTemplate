package minecraftguitemplate.me.config;

import minecraftguitemplate.me.GuiTemplate;
import minecraftguitemplate.me.config.values.Value;
import minecraftguitemplate.me.systems.impl.Module;
import minecraftguitemplate.me.systems.impl.ModuleManager;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    public static void saveConfig() {
        FileConfiguration config = GuiTemplate.getInstance().getConfig();
        for (Module module : ModuleManager.getModuleList()) {
            for (Value<?> value : module.getConfigList()) {
                String path = "state." + module.getName() + "." + value.getName();
                if (value.get() instanceof String string) {
                    Value<String> val = (Value<String>) value;
                    if (config.contains(path)) {
                        val.set(config.getString(path));
                    } else {
                        config.set(path, string);
                    }
                } else if (value.get() instanceof Integer integer) {
                    Value<Integer> val = (Value<Integer>) value;
                    if (config.contains(path)) {
                        val.set(config.getInt(path));
                    } else {
                        config.set(path, integer);
                    }
                } else if (value.get() instanceof Double floatDecimal) {
                    Value<Double> val = (Value<Double>) value;
                    if (config.contains(path)) {
                        val.set(config.getDouble(path));
                    } else {
                        config.set(path, floatDecimal);
                    }
                } else if (value.get() instanceof Boolean bool) {
                    Value<Boolean> val = (Value<Boolean>) value;
                    if (config.contains(path)) {
                        val.set(config.getBoolean(path));
                    } else {
                        config.set(path, bool);
                    }
                }
            }
        }
    }
}
