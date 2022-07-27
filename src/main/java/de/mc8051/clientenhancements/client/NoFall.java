package de.mc8051.clientenhancements.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall {


    public void tick(MinecraftClient client) {
        if (client.player != null && ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.NOFALL)) {
            ClientPlayerEntity player = client.player;
            if(player.fallDistance <= (player.isFallFlying() ? 1 : 2))
                return;

            if(player.isFallFlying() && player.isSneaking()
                    && !isFallingFastEnoughToCauseDamage(player))
                return;

            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    }

    private boolean isFallingFastEnoughToCauseDamage(ClientPlayerEntity player)
    {
        return player.getVelocity().y < -0.5;
    }
}
