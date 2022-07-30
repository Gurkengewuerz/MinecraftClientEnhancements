package de.mc8051.clientenhancements.config;

import java.util.Arrays;
import java.util.List;

public class CustomConfig {
    public boolean ANTI_INVISIBILITY_GLOW = false;
    public boolean ANTI_INVISIBILITY_VISIBLE = true;

    public double FULLBRIGHTNESS_GAMMA = 2.5;

    public int FLYHACK_TICK_PER_RESET = 20;

    public boolean HUD_VISIBLE = true;

    public boolean ZOOM_SMOOTH = false;
    public double ZOOM_LEVEL = 3.0;
    public long ZOOM_SPEED = 250;

    public List<String> XRAY_MATERIALS = List.of(
            "coal_ore",
            "iron_ore",
            "gold_ore",
            "diamond_ore",
            "emerald_ore",
            "lapis_ore",
            "redstone_ore",
            "deepslate_coal_ore",
            "deepslate_iron_ore",
            "deepslate_gold_ore",
            "deepslate_diamond_ore",
            "deepslate_emerald_ore",
            "deepslate_lapis_ore",
            "deepslate_redstone_ore",
            "chest",
            "mob_spawner",
            "spawner",
            "bookshelf",
            "ancient_debris",
            "nether_gold_ore",
            "nether_quartz_ore",
            "blackstone",
            "glowstone",
            "gold_block",
            "bone_block",
            "obsidian",
            "nether_brick",
            "magma_block",
            "lava",
            "water"
    );
}
