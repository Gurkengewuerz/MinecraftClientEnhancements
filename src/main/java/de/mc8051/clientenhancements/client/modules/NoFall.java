package de.mc8051.clientenhancements.client.modules;

import de.mc8051.clientenhancements.client.KeyController;
import de.mc8051.clientenhancements.client.Module;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;

public class NoFall extends Module {

    public NoFall() {
        this.key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.enhancements.nofall",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.enhancements.default"
        ));
    }

    @Override
    public void onTick(KeyController controller, MinecraftClient client) {
        if (client.player == null) return;
        if (!state) return;
        ClientPlayerEntity player = client.player;
        if (player.fallDistance <= (player.isFallFlying() ? 1 : 2))
            return;

        if (player.isFallFlying() && player.isSneaking()
                && !isFallingFastEnoughToCauseDamage(player))
            return;

        player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }

    private boolean isFallingFastEnoughToCauseDamage(ClientPlayerEntity player) {
        return player.getVelocity().y < -0.5;
    }
}
