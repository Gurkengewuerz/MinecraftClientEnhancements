package de.mc8051.clientenhancements.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class KeyBindingController {

    public static String FLY = "key.enhancements.flying";
    public static String BRIGHTNESS = "key.enhancements.brightness";
    public static String INVISIBLE = "key.enhancements.invisible";
    public static String NOFALL = "key.enhancements.nofall";
    public static String ZOOM = "key.enhancements.zoom";
    private HashMap<String, Boolean> states = new HashMap<>();
    public void tick(MinecraftClient client, KeyBinding ...keyBindings) {
        Arrays.stream(keyBindings).forEach(key -> {
            if(key.wasPressed()) {
                states.put(key.getTranslationKey(), !states.getOrDefault(key.getTranslationKey(), false));
                final String msg = Text.translatable(key.getTranslationKey()).getString() + ": " + getState(key.getTranslationKey());
                if(client.player != null) client.player.sendMessage(Text.of(msg), false);
            }
        });
    }

    public boolean getState(String key) {
        return states.getOrDefault(key, false);
    }

    public TreeMap<String, Boolean> getSortedStates() {
        return new TreeMap<>(states);
    }
}
