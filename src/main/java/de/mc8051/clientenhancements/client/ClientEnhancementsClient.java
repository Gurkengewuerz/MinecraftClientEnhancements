package de.mc8051.clientenhancements.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientEnhancementsClient implements ClientModInitializer {

    private static ClientEnhancementsClient instance;

    private Flying flying;
    private FullBrightness fullBrightness;
    private static KeyBindingController keyBindingController;

    private static KeyBinding keyFly;
    private static KeyBinding keyBrightness;
    private static KeyBinding keyInvisible;

    @Override
    public void onInitializeClient() {
        instance = this;

        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
        flying = new Flying();
        fullBrightness = new FullBrightness();
        keyBindingController = new KeyBindingController();

        keyFly = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KeyBindingController.FLY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.enhancements.default"
        ));

        keyBrightness = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KeyBindingController.BRIGHTNESS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.enhancements.default"
        ));

        keyInvisible = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KeyBindingController.INVISIBLE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "category.enhancements.default"
        ));
    }

    public void tick(MinecraftClient client) {
        keyBindingController.tick(client, keyFly, keyBrightness, keyInvisible);
        fullBrightness.tick(client);
        flying.tick(client);
    }

    public static ClientEnhancementsClient getInstance() {
        return instance;
    }

    public static KeyBindingController getKeyBindingController() {
        return keyBindingController;
    }
}
