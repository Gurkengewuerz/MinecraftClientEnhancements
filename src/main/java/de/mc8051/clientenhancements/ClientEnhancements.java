package de.mc8051.clientenhancements;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ClientEnhancements implements ModInitializer {
    @Override
    public void onInitialize() {
        HudRenderCallback.EVENT.register(new HudInfoRenderer());
    }
}
