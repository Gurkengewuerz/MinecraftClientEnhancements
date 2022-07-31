package de.mc8051.clientenhancements.client.modules;

import de.mc8051.clientenhancements.client.KeyController;
import de.mc8051.clientenhancements.client.Module;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class XRay extends Module {

    public XRay() {
        this.key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.enhancements.xray",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.enhancements.default"
        ));
    }

    @Override
    public void onToggle(KeyController controller, MinecraftClient client) {
        client.worldRenderer.reload();
    }
}
