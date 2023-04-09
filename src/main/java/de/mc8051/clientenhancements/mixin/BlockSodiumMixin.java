package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.ClientEnhancements;
import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.modules.XRay;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache")
public class BlockSodiumMixin {
    @Inject(at = @At("HEAD"), method = "shouldDrawSide", cancellable = true, remap = false)
    private void shouldDrawSide(BlockState state, BlockView reader, BlockPos pos, Direction face, CallbackInfoReturnable<Boolean> cir) {
        if (ClientEnhancementsClient.getKeyController().getState(XRay.class)) {
            cir.setReturnValue(ClientEnhancements.config.getConfig().XRAY_MATERIALS.contains(Registries.BLOCK.getId(state.getBlock()).toString().toLowerCase()));
        }
    }
}
