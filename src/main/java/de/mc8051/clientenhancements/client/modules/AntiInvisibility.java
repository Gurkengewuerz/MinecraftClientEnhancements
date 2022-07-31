package de.mc8051.clientenhancements.client.modules;

import de.mc8051.clientenhancements.ClientEnhancements;
import de.mc8051.clientenhancements.client.KeyController;
import de.mc8051.clientenhancements.client.Module;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AntiInvisibility extends Module {

    public AntiInvisibility() {
        this.key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.enhancements.invisible",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.enhancements.default"
        ));
    }

    @Override
    public void onToggle(KeyController controller, MinecraftClient client) {
        if (ClientEnhancements.config.getConfig().ANTI_INVISIBILITY_BARRIER)
            client.worldRenderer.reload();
    }
}
