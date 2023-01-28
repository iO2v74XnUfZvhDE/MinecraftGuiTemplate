package minecraftguitemplate.me.systems.impl;

import minecraftguitemplate.me.config.values.Value;
import minecraftguitemplate.me.systems.Categories;

import java.util.ArrayList;
import java.util.List;

abstract public class Module {
    private final String name;
    private final Categories category;
    public Module(String name, Categories category, boolean defaultEnabled) {
        this.name = name;
        this.category = category;
        enabled = defaultEnabled;
    }

    public String getName() {
        return name;
    }

    public Categories getCategory() {
        return category;
    }

    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private final List<Value<?>> configList = new ArrayList<>();

    protected void addConfig(Value<?> value) {
        configList.add(value);
    }

    public List<Value<?>> getConfigList() {
        return configList;
    }
}
