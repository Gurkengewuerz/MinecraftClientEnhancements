package de.mc8051.clientenhancements.client;

import de.mc8051.clientenhancements.CapePlayerHandler;
import de.mc8051.clientenhancements.client.modules.AntiInvisibility;
import de.mc8051.clientenhancements.client.modules.Fly;
import de.mc8051.clientenhancements.client.modules.FullBrightness;
import de.mc8051.clientenhancements.client.modules.NoFall;
import de.mc8051.clientenhancements.client.modules.XRay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientEnhancementsClient implements ClientModInitializer {

    private static Zoom zoomer;
    private static List<Module> modules;
    private static KeyController keyController;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BARRIER, RenderLayer.getTranslucent());
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);

        CapePlayerHandler.init();

        modules = List.of(
                new AntiInvisibility(),
                new Fly(),
                new NoFall(),
                new XRay(),
                new FullBrightness()
        );

        zoomer = new Zoom();
        keyController = new KeyController(modules);
    }

    public void tick(MinecraftClient client) {
        keyController.tick(client);
    }

    public static KeyController getKeyController() {
        return keyController;
    }

    public static Zoom getZoomer() {
        return zoomer;
    }
}
