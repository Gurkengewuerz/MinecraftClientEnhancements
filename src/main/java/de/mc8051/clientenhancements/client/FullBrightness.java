package de.mc8051.clientenhancements.client;

import de.mc8051.clientenhancements.ClientEnhancements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class FullBrightness {

    double DEFAULT = 1.0;

    public void tick(MinecraftClient client) {
        if (ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.BRIGHTNESS) || ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.XRAY))
            update(client, ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.XRAY) ? 16.0 : ClientEnhancements.config.getConfig().FULLBRIGHTNESS_GAMMA);
        else update(client, DEFAULT);
    }

    private void update(MinecraftClient client, double value) {
        if (client == null) return;
        if (client.options.getGamma().getValue() == value) return;
        client.options.getGamma().setValue(value);
        client.options.write();
        if(client.player != null) client.player.sendMessage(Text.of(String.valueOf(value)), true);
    }
}
