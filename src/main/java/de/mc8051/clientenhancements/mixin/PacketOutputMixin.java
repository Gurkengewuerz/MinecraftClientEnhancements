package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.modules.Freecam;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class PacketOutputMixin implements ClientPlayPacketListener {

    @Inject(at = {@At("HEAD")}, method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (ClientEnhancementsClient.getKeyController().getState(Freecam.class) && (packet instanceof PlayerMoveC2SPacket))
            ci.cancel();
    }
}
