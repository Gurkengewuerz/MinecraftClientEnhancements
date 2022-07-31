package de.mc8051.clientenhancements.client.modules;

import de.mc8051.clientenhancements.ClientEnhancements;
import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.KeyController;
import de.mc8051.clientenhancements.client.Module;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class FullBrightness extends Module {

    double DEFAULT = 1.0;

    public FullBrightness() {
        this.key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.enhancements.brightness",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.enhancements.default"
        ));
    }

    @Override
    public void onTick(KeyController controller, MinecraftClient client) {
        if (state || controller.getState(XRay.class))
            update(client, controller.getState(XRay.class) ? 16.0 : ClientEnhancements.config.getConfig().FULLBRIGHTNESS_GAMMA);
        else update(client, DEFAULT);
    }


    private void update(MinecraftClient client, double value) {
        if (client == null) return;
        if (client.options.getGamma().getValue() == value) return;
        client.options.getGamma().setValue(value);
        if (client.player != null) client.player.sendMessage(Text.of(String.valueOf(value)), true);
    }
}
