package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.ClientEnhancements;
import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.modules.AntiInvisibility;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("deprecated")
@Mixin(BarrierBlock.class)
public class BarrierMixin extends Block {

    public BarrierMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "getRenderType", cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> info) {
        // Note: This requires to overwrite the minecraft barrier texture. See resources folder
        if (ClientEnhancementsClient.getKeyController().getState(AntiInvisibility.class) && ClientEnhancements.config.getConfig().ANTI_INVISIBILITY_BARRIER)
            info.setReturnValue(BlockRenderType.MODEL);
    }

}
