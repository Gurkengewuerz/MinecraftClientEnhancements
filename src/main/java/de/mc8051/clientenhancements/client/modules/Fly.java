package de.mc8051.clientenhancements.client.modules;

import de.mc8051.clientenhancements.ClientEnhancements;
import de.mc8051.clientenhancements.client.KeyController;
import de.mc8051.clientenhancements.client.Module;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Fly extends Module {

    int toggle = 0;
    double FALL_SPEED = -0.04;

    public Fly() {
        this.key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.enhancements.flying",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.enhancements.default"
        ));
    }

    @Override
    public void onToggle(KeyController controller, MinecraftClient client) {
        final ClientPlayerEntity player = client.player;
        if (player == null) return;

        if (!state) {
            boolean inFlyMode = player.isCreative() || player.isSpectator();
            PlayerAbilities abilities = player.getAbilities();

            abilities.flying = inFlyMode && !player.isOnGround();
            abilities.allowFlying = inFlyMode;
        }

    }

    @Override
    public void onTick(KeyController controller, MinecraftClient client) {
        final ClientPlayerEntity player = client.player;
        if (player == null) return;
        if (!state) return;
        boolean inFlyMode = player.isCreative() || player.isSpectator();

        player.getAbilities().allowFlying = true;
        if (player.hasVehicle()) return;
        if (inFlyMode) return;

        final Vec3d velocity = player.getVelocity();
        if (toggle == 0) {
            player.setVelocity(new Vec3d(
                    velocity.x, FALL_SPEED - velocity.y, velocity.z
            ));
        }
        // We have to consider lags, thats why we do a drip every second
        if (toggle == 0 || velocity.y < FALL_SPEED)
            toggle = ClientEnhancements.config.getConfig().FLYHACK_TICK_PER_RESET;
        toggle--;
    }
}
