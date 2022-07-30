package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.ClientEnhancements;
import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.KeyBindingController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class XRayMixin {

    @Inject(at = @At("RETURN"), method = "shouldDrawSide", cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> cir) {
        if (ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.XRAY)) {
            cir.setReturnValue(ClientEnhancements.config.getConfig().XRAY_MATERIALS.contains(Registry.BLOCK.getId(state.getBlock()).toString().toLowerCase()));
        }
    }
}
