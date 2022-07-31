package de.mc8051.clientenhancements.client;

import net.minecraft.client.MinecraftClient;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

public class KeyController {

    private final HashMap<String, Boolean> states = new HashMap<>();
    private final List<Module> modules;

    public KeyController(List<Module> modules) {
        this.modules = modules;
    }

    public void tick(MinecraftClient client) {
        modules.forEach(module -> module.onTick(this, client));
        modules.stream().filter(module -> module.getKey() != null).forEach(module -> {
            if (module.getKey().wasPressed()) {
                final String name = module.getClass().getName();
                states.put(name, !states.getOrDefault(name, false));
                final boolean newState = states.get(name);
                module.setState(newState);
                module.onToggle(this, client);
            }
        });
    }

    public boolean getState(Class<?> clazz) {
        return states.getOrDefault(clazz.getName(), false);
    }

    public TreeMap<String, Boolean> getSortedStates() {
        final TreeMap<String, Boolean> keyStates = new TreeMap<>();
        states.forEach((key, value) -> {
            final Optional<Module> foundModule = modules.stream().filter(module -> module.getClass().getName().equals(key)).findFirst();
            foundModule.ifPresent(module -> keyStates.put(module.getKey().getTranslationKey(), value));
        });
        return keyStates;
    }
}
