package de.mc8051.clientenhancements.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.util.math.Vec3d;

public class Flying {

    int toggle = 0;
    double FALL_SPEED = -0.04;

    boolean switchedState = false;

    public void tick(MinecraftClient client) {
        if (client.player != null) {
            ClientPlayerEntity player = client.player;
            boolean inFlyMode = player.isCreative() || player.isSpectator();

            if (ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.FLY)) {
                if (!switchedState) switchedState = true;
                player.getAbilities().allowFlying = true;

                if (player.hasVehicle()) return;
                if (inFlyMode) return;

                final Vec3d velocity = player.getVelocity();
                if (toggle == 0) {
                    player.setVelocity(new Vec3d(
                            velocity.x, FALL_SPEED - velocity.y, velocity.z
                    ));
                }

                if (toggle == 0 || velocity.y < FALL_SPEED) toggle = 40;
                toggle--;
            } else if (switchedState) {
                switchedState = false;
                PlayerAbilities abilities = player.getAbilities();

                abilities.flying = inFlyMode && !player.isOnGround();
                abilities.allowFlying = inFlyMode;
            }

        }
    }
}
