package de.mc8051.clientenhancements.mixin;

import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;

@Mixin(GameRenderer.class)
public class ZoomMixin implements AutoCloseable, SynchronousResourceReloader {

    @Inject(at = @At(value = "RETURN", ordinal = 1), method = {"getFov(Lnet/minecraft/client/render/Camera;FZ)D"}, cancellable = true)
    private void onGetFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(ClientEnhancementsClient.getZoomer().changeFovBasedOnZoom(cir.getReturnValueD()));
    }

    @Override
    public void reload(ResourceManager var1) {

    }

    @Shadow
    @Override
    public void close() throws Exception {

    }
}
