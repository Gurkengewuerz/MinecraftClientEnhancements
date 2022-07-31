package de.mc8051.clientenhancements.client;

import de.mc8051.clientenhancements.ClientEnhancements;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Zoom {

    public KeyBinding keyZoom = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.enhancements.zoom",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.enhancements.default"
    ));
    private Double defaultMouseSensitivity;
    private long zoomStarted = 0L;

    public double changeFovBasedOnZoom(double fov) {
        SimpleOption<Double> mouseSensitivitySetting = MinecraftClient.getInstance().options.getMouseSensitivity();
        if (defaultMouseSensitivity == null) defaultMouseSensitivity = mouseSensitivitySetting.getValue();

        if (!keyZoom.isPressed()) {
            zoomStarted = 0L;
            if (defaultMouseSensitivity != null) {
                mouseSensitivitySetting.setValue(defaultMouseSensitivity);
                defaultMouseSensitivity = null;
            }

            return fov;
        }

        // https://github.com/Wurst-Imperium/WI-Zoom/blob/master/src/main/java/net/wurstclient/zoom/WiZoom.java
        Double currentLevel = ClientEnhancements.config.getConfig().ZOOM_LEVEL;
        if (ClientEnhancements.config.getConfig().ZOOM_SMOOTH) {
            long now = System.currentTimeMillis();
            if (zoomStarted == 0L) {
                zoomStarted = now;
            }
            long diff = now - zoomStarted;
            double percentageIn = 1.0;
            if (diff <= ClientEnhancements.config.getConfig().ZOOM_SPEED) {
                percentageIn = (double) diff / (double) ClientEnhancements.config.getConfig().ZOOM_SPEED;
            }
            currentLevel = (ClientEnhancements.config.getConfig().ZOOM_LEVEL * percentageIn) + 1.0;
        }

        // Adjust mouse sensitivity in relation to zoom level.
        // 1.0 / currentLevel is a value between 0.02 (50x zoom)
        // and 1 (no zoom).
        mouseSensitivitySetting.setValue(defaultMouseSensitivity * (1.0 / ClientEnhancements.config.getConfig().ZOOM_LEVEL));

        return fov / currentLevel;
    }

}
