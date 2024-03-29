package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.modules.Freecam;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.listener.ClientCommonPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
public abstract class PacketOutputMixin implements ClientCommonPacketListener {

    @Inject(at = {@At("HEAD")}, method = {"sendPacket(Lnet/minecraft/network/packet/Packet;)V"}, cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (ClientEnhancementsClient.getKeyController().getState(Freecam.class) && (packet instanceof PlayerMoveC2SPacket))
            ci.cancel();
    }
}
