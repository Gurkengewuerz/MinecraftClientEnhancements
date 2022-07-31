package de.mc8051.clientenhancements.config;

import java.util.Arrays;
import java.util.List;

public class CustomConfig {
    public boolean ANTI_INVISIBILITY_GLOW = false;
    public boolean ANTI_INVISIBILITY_VISIBLE = true;
    public boolean ANTI_INVISIBILITY_BARRIER = true;

    public double FULLBRIGHTNESS_GAMMA = 2.5;

    public int FLYHACK_TICK_PER_RESET = 20;

    public boolean HUD_VISIBLE = true;

    public boolean ZOOM_SMOOTH = false;
    public double ZOOM_LEVEL = 3.0;
    public long ZOOM_SPEED = 250;

    public List<String> XRAY_MATERIALS = List.of(
            "minecraft:coal_ore",
            "minecraft:iron_ore",
            "minecraft:gold_ore",
            "minecraft:diamond_ore",
            "minecraft:emerald_ore",
            "minecraft:lapis_ore",
            "minecraft:redstone_ore",
            "minecraft:deepslate_coal_ore",
            "minecraft:deepslate_iron_ore",
            "minecraft:deepslate_gold_ore",
            "minecraft:deepslate_diamond_ore",
            "minecraft:deepslate_emerald_ore",
            "minecraft:deepslate_lapis_ore",
            "minecraft:deepslate_redstone_ore",
            "minecraft:chest",
            "minecraft:mob_spawner",
            "minecraft:spawner",
            "minecraft:bookshelf",
            "minecraft:ancient_debris",
            "minecraft:nether_gold_ore",
            "minecraft:nether_quartz_ore",
            "minecraft:blackstone",
            "minecraft:glowstone",
            "minecraft:gold_block",
            "minecraft:bone_block",
            "minecraft:obsidian",
            "minecraft:nether_brick",
            "minecraft:magma_block",
            "minecraft:lava",
            "minecraft:water"
    );
}
