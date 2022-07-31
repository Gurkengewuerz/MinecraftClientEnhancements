package de.mc8051.clientenhancements.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public abstract class Module {

    protected KeyBinding key;
    protected boolean state;

    public void onTick(KeyController controller, MinecraftClient client) {

    }

    public void onToggle(KeyController controller, MinecraftClient client) {

    }

    public void setState(boolean state) {
        this.state = state;
    }

    public KeyBinding getKey() {
        return key;
    }

    public boolean isState() {
        return state;
    }
}
