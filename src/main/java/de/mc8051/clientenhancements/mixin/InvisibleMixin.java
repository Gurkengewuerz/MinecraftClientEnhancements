package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import de.mc8051.clientenhancements.client.KeyBindingController;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class InvisibleMixin {

    @Inject(at = @At("RETURN"), method = "isInvisible()Z")
    private boolean overrideInvis(CallbackInfoReturnable<Boolean> ci) {
        if (ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.INVISIBLE)) return false;
        return ci.getReturnValueZ();
    }


    @Inject(at = @At("RETURN"), method = "isGlowing()Z")
    private boolean overrideInvisGlow(CallbackInfoReturnable<Boolean> ci) {
        if (ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.INVISIBLE)) {
            EntityInvoker target = ((EntityInvoker) (Object) this);
            if (target.getFlagInvoker(5)) return true;
        }
        return ci.getReturnValueZ();
    }
}

