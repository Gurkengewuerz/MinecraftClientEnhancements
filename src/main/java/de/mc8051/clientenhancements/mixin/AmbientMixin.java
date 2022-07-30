package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.KeyBindingController;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(MinecraftClient.class)
public class AmbientMixin {

    @Inject(at = @At(value = "HEAD"), method = "isAmbientOcclusionEnabled()Z", cancellable = true)
    private static void isAmbientOcclusionEnabled(CallbackInfoReturnable<Boolean> ci) {
        if (ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.XRAY)) {
            ci.setReturnValue(false);
            ci.cancel();
        }
    }
}
