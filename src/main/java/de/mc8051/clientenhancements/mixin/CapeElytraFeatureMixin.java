package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.CapePlayerHandler;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ElytraFeatureRenderer.class)
public class CapeElytraFeatureMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;canRenderCapeTexture()Z"))
    private boolean toggleCustomElytra(AbstractClientPlayerEntity abstractClientPlayerEntity) {

        final CapePlayerHandler capePlayerHandler = CapePlayerHandler.getCapePlayerHandler(abstractClientPlayerEntity.getGameProfile().getId());
        if (capePlayerHandler != null) {
            return capePlayerHandler.hasElytraTexture();
        }
        return abstractClientPlayerEntity.canRenderCapeTexture();
    }

}
