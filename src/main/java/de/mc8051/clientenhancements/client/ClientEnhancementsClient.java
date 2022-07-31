package de.mc8051.clientenhancements.client;

import de.mc8051.clientenhancements.CapePlayerHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientEnhancementsClient implements ClientModInitializer {

    private static ClientEnhancementsClient instance;

    private Flying flying;
    private FullBrightness fullBrightness;
    private NoFall noFall;
    private static Zoom zoomer;
    private static XRay xray;
    private static Barrier barrier;
    private static KeyBindingController keyBindingController;

    private static KeyBinding keyFly = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KeyBindingController.FLY,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.enhancements.default"
    ));
    private static KeyBinding keyBrightness = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KeyBindingController.BRIGHTNESS,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.enhancements.default"
    ));
    private static KeyBinding keyInvisible = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KeyBindingController.INVISIBLE,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.enhancements.default"
    ));
    private static KeyBinding keyNoFall = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KeyBindingController.NOFALL,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.enhancements.default"
    ));
    public static KeyBinding keyZoom = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KeyBindingController.ZOOM,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.enhancements.default"
    ));
    public static KeyBinding keyXray = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KeyBindingController.XRAY,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            "category.enhancements.default"
    ));

    @Override
    public void onInitializeClient() {
        instance = this;

        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BARRIER, RenderLayer.getTranslucent());

        ClientTickEvents.END_CLIENT_TICK.register(this::tick);

        CapePlayerHandler.init();

        flying = new Flying();
        fullBrightness = new FullBrightness();
        noFall = new NoFall();
        zoomer = new Zoom();
        xray = new XRay();
        barrier = new Barrier();
        keyBindingController = new KeyBindingController();
    }

    public void tick(MinecraftClient client) {
        keyBindingController.tick(client, keyFly, keyBrightness, keyInvisible, keyNoFall, keyXray);
        fullBrightness.tick(client);
        flying.tick(client);
        noFall.tick(client);
        xray.tick(client);
        barrier.tick(client);
    }

    public static ClientEnhancementsClient getInstance() {
        return instance;
    }

    public static KeyBindingController getKeyBindingController() {
        return keyBindingController;
    }

    public static Zoom getZoomer() {
        return zoomer;
    }
}
