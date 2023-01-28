package minecraftguitemplate.me.systems.impl;

import minecraftguitemplate.me.systems.impl.world.SpectatorOnDeath;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<Module> moduleList = new ArrayList<>();
    public static void startup() {
        moduleList.add(new SpectatorOnDeath());
    }

    public static List<Module> getModuleList() {
        return moduleList;
    }
}
