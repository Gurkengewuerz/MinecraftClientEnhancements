package de.mc8051.clientenhancements.mixin;

import com.mojang.authlib.GameProfile;
import de.mc8051.clientenhancements.CapePlayerHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(PlayerListEntry.class)
public class CapePlayerListMixin {

    // inspired by https://github.com/CaelTheColher/Capes/

    @Shadow
    @Final
    private GameProfile profile;

    @Inject(method = "texturesSupplier", at = @At("HEAD"))
    private static void loadTextures(GameProfile profile, CallbackInfoReturnable<Supplier<SkinTextures>> cir) {
        // this also loads the cape
        CapePlayerHandler.getCapePlayerHandler(profile.getId());
    }

    @Inject(method = "getSkinTextures", at = @At("TAIL"), cancellable = true)
    private void getCapeTexture(CallbackInfoReturnable<SkinTextures> cir) {

        final CapePlayerHandler capePlayerHandler = CapePlayerHandler.getCapePlayerHandler(profile.getId());
        if (capePlayerHandler != null && capePlayerHandler.hasCape()) {

            SkinTextures oldTextures = cir.getReturnValue();
            Identifier capeTexture = capePlayerHandler.getCape();
            Identifier elytraTexture = capePlayerHandler.hasElytraTexture() ? capeTexture : new Identifier("textures/entity/elytra.png");
            SkinTextures newTextures = new SkinTextures(
                oldTextures.texture(), oldTextures.textureUrl(),
                capeTexture, elytraTexture,
                oldTextures.model(), oldTextures.secure());

            cir.setReturnValue(newTextures);
        }
    }

}
