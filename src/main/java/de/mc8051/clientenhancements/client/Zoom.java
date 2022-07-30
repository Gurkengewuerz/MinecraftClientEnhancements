package de.mc8051.clientenhancements.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;

public class Zoom {

    // https://github.com/Wurst-Imperium/WI-Zoom/blob/master/src/main/java/net/wurstclient/zoom/WiZoom.java
    private final double defaultLevel = 3;
    private Double currentLevel;
    private Double defaultMouseSensitivity;

    public double changeFovBasedOnZoom(double fov)
    {
        SimpleOption<Double> mouseSensitivitySetting = MinecraftClient.getInstance().options.getMouseSensitivity();

        if(currentLevel == null) currentLevel = defaultLevel;

        if(!ClientEnhancementsClient.keyZoom.isPressed())
        {
            currentLevel = defaultLevel;
            if(defaultMouseSensitivity != null)
            {
                mouseSensitivitySetting.setValue(defaultMouseSensitivity);
                defaultMouseSensitivity = null;
            }

            return fov;
        }

        if(defaultMouseSensitivity == null) defaultMouseSensitivity = mouseSensitivitySetting.getValue();

        // Adjust mouse sensitivity in relation to zoom level.
        // 1.0 / currentLevel is a value between 0.02 (50x zoom)
        // and 1 (no zoom).
        mouseSensitivitySetting.setValue(defaultMouseSensitivity * (1.0 / currentLevel));

        return fov / currentLevel;
    }

}
