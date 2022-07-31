package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.modules.XRay;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(MinecraftClient.class)
public class AmbientMixin {

    @Inject(at = @At(value = "HEAD"), method = "isAmbientOcclusionEnabled()Z", cancellable = true)
    private static void isAmbientOcclusionEnabled(CallbackInfoReturnable<Boolean> ci) {
        if (ClientEnhancementsClient.getKeyController().getState(XRay.class)) {
            ci.setReturnValue(false);
            ci.cancel();
        }
    }
}
