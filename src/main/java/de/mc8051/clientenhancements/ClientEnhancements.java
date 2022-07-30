package de.mc8051.clientenhancements;

import de.mc8051.clientenhancements.config.SimpleConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ClientEnhancements implements ModInitializer {

    public static SimpleConfig config;
    @Override
    public void onInitialize() {
        config = new SimpleConfig("client-enhancements");

        HudRenderCallback.EVENT.register(new HudInfoRenderer());
    }

}
