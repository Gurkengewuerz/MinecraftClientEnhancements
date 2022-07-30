package de.mc8051.clientenhancements.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class XRay {

    private boolean lastState = false;
    private boolean changed = false;

    public void tick(MinecraftClient client) {
        if (client.player == null) return;

        boolean currentState = ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.XRAY);
        if(currentState != lastState) {
            lastState = currentState;
            changed = true;
        }

        if (changed) {
            client.worldRenderer.reload();
            changed = false;
        }
    }
}
