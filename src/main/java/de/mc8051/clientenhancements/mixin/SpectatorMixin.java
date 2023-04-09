package de.mc8051.clientenhancements.mixin;

import com.mojang.authlib.GameProfile;
import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.modules.Freecam;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class SpectatorMixin extends PlayerEntity {

    public SpectatorMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "isSpectator", at = @At("HEAD"), cancellable = true)
    private void overrideIsSpectator(CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance() == null) return;
        final ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        if (ClientEnhancementsClient.getKeyController().getState(Freecam.class) && this.getId() == player.getId())
            cir.setReturnValue(true);
    }
}
