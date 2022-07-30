package de.mc8051.clientenhancements.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import de.mc8051.clientenhancements.CapePlayerHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListEntry.class)
public class CapePlayerListMixin {

    @Shadow @Final private GameProfile profile;
    @Shadow private boolean texturesLoaded;

    @Inject(method = "loadTextures", at = @At("HEAD"))
    private void loadTextures(CallbackInfo ci) {
        if (!texturesLoaded) {
            // this also loads the cape
            CapePlayerHandler.getCapePlayerHandler(profile.getId());
        }
    }

    @Inject(method = "getCapeTexture", at = @At("TAIL"), cancellable = true)
    private void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        final CapePlayerHandler capePlayerHandler = CapePlayerHandler.getCapePlayerHandler(profile.getId());
        if (capePlayerHandler != null && capePlayerHandler.hasCape()) {
            cir.setReturnValue(capePlayerHandler.getCape());
        }
    }

}
