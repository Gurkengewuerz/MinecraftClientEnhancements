package de.mc8051.clientenhancements.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Flying {

    int toggle = 0;
    int MAX_SPEED = 3;
    double FALL_SPEED = -0.04;
    double ACCELERATION = 0.1;

    public void tick(MinecraftClient client) {
        if (client.player != null && ClientEnhancementsClient.getKeyBindingController().getState(KeyBindingController.FLY)) {
            boolean jumpPressed = client.options.jumpKey.isPressed();
            boolean forwardPressed = client.options.forwardKey.isPressed();
            boolean leftPressed = client.options.leftKey.isPressed();
            boolean rightPressed = client.options.rightKey.isPressed();
            boolean backPressed = client.options.backKey.isPressed();

            Entity entity = client.player;
            if (client.player.hasVehicle()) return;

            final boolean anyMovement = forwardPressed || leftPressed || rightPressed || backPressed;
            Vec3d velocity = entity.getVelocity();
            Vec3d newVelocity = new Vec3d(velocity.x, -FALL_SPEED, velocity.z);
            if (forwardPressed) newVelocity = client.player.getRotationVector().multiply(ACCELERATION);
            if (leftPressed) {
                newVelocity = client.player.getRotationVector().multiply(ACCELERATION).rotateY((float) (Math.PI / 2));
                newVelocity = new Vec3d(newVelocity.x, 0, newVelocity.z);
            }
            if (rightPressed) {
                newVelocity = client.player.getRotationVector().multiply(ACCELERATION).rotateY((float) -(Math.PI / 2));
                newVelocity = new Vec3d(newVelocity.x, 0, newVelocity.z);
            }
            if (backPressed) newVelocity = client.player.getRotationVector().negate().multiply(ACCELERATION);
            newVelocity = new Vec3d(newVelocity.x, (toggle == 0 && newVelocity.y > FALL_SPEED) ? FALL_SPEED : (anyMovement ? newVelocity.y : 0), newVelocity.z);
            entity.setVelocity(newVelocity);

            if(anyMovement) {
                if (ACCELERATION < MAX_SPEED) ACCELERATION += 0.1;
            } else if (ACCELERATION > 0.2) ACCELERATION -= 0.2;

            if(toggle == 0 || newVelocity.y <= FALL_SPEED) toggle = 40;
            toggle--;
        }
    }
}
