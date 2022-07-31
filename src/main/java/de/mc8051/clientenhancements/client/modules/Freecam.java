package de.mc8051.clientenhancements.client.modules;

import de.mc8051.clientenhancements.client.FakePlayerEntity;
import de.mc8051.clientenhancements.client.KeyController;
import de.mc8051.clientenhancements.client.Module;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Freecam extends Module {

    public FakePlayerEntity fakePlayer;

    public Freecam() {
        this.key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.enhancements.freecam",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.enhancements.default"
        ));
    }

    @Override
    public void onToggle(KeyController controller, MinecraftClient client) {
        final ClientPlayerEntity player = client.player;
        if (player == null) return;
        final ClientWorld world = player.clientWorld;
        if (!state) {
            player.setVelocity(Vec3d.ZERO);
            if (fakePlayer != null) {
                fakePlayer.resetPlayerPosition();
                fakePlayer.despawn();
            }
            player.getAbilities().flying = false;
        } else {
            fakePlayer = new FakePlayerEntity(player, world);
            player.getAbilities().flying = true;
        }
    }
}
