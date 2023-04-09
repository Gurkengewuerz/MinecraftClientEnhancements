package de.mc8051.clientenhancements.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import de.mc8051.clientenhancements.ClientEnhancements;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.IOException;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) parent -> {
            ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("title.enhancements.option"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            CustomConfig defaultConfig = new CustomConfig();

            builder.setSavingRunnable(() -> {
                try {
                    ClientEnhancements.config.createConfig();
                } catch (IOException ignored) {
                }
            });

            ConfigCategory fly = builder.getOrCreateCategory(Text.translatable("title.enhancements.option.flying"));
            fly.addEntry(entryBuilder.startIntField(Text.translatable("title.enhancements.option.flying.tpr"), ClientEnhancements.config.getConfig().FLYHACK_TICK_PER_RESET)
                .setTooltip(Text.translatable("title.enhancements.option.flying.tpr.tooltip"))
                .setDefaultValue(defaultConfig.FLYHACK_TICK_PER_RESET)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().FLYHACK_TICK_PER_RESET = newValue)
                .build());

            ConfigCategory hud = builder.getOrCreateCategory(Text.translatable("title.enhancements.option.hud"));
            hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("title.enhancements.option.hud.enable"), ClientEnhancements.config.getConfig().HUD_VISIBLE)
                .setTooltip(Text.translatable("title.enhancements.option.hud.enable.tooltip"))
                .setDefaultValue(defaultConfig.HUD_VISIBLE)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().HUD_VISIBLE = newValue)
                .build());

            ConfigCategory brightness = builder.getOrCreateCategory(Text.translatable("title.enhancements.option.brightness"));
            brightness.addEntry(entryBuilder.startDoubleField(Text.translatable("title.enhancements.option.brightness.gamma"), ClientEnhancements.config.getConfig().FULLBRIGHTNESS_GAMMA)
                .setTooltip(Text.translatable("title.enhancements.option.brightness.gamma.tooltip"))
                .setDefaultValue(defaultConfig.FULLBRIGHTNESS_GAMMA)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().FULLBRIGHTNESS_GAMMA = newValue)
                .build());


            ConfigCategory invisible = builder.getOrCreateCategory(Text.translatable("title.enhancements.option.invisible"));
            invisible.addEntry(entryBuilder.startBooleanToggle(Text.translatable("title.enhancements.option.invisible.visible"), ClientEnhancements.config.getConfig().ANTI_INVISIBILITY_VISIBLE)
                .setTooltip(Text.translatable("title.enhancements.option.invisible.visible.tooltip"))
                .setDefaultValue(defaultConfig.ANTI_INVISIBILITY_VISIBLE)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().ANTI_INVISIBILITY_VISIBLE = newValue)
                .build());
            invisible.addEntry(entryBuilder.startBooleanToggle(Text.translatable("title.enhancements.option.invisible.glow"), ClientEnhancements.config.getConfig().ANTI_INVISIBILITY_GLOW)
                .setTooltip(Text.translatable("title.enhancements.option.invisible.glow.tooltip"))
                .setDefaultValue(defaultConfig.ANTI_INVISIBILITY_GLOW)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().ANTI_INVISIBILITY_GLOW = newValue)
                .build());
            invisible.addEntry(entryBuilder.startBooleanToggle(Text.translatable("title.enhancements.option.invisible.barrier"), ClientEnhancements.config.getConfig().ANTI_INVISIBILITY_BARRIER)
                .setTooltip(Text.translatable("title.enhancements.option.invisible.barrier.tooltip"))
                .setDefaultValue(defaultConfig.ANTI_INVISIBILITY_BARRIER)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().ANTI_INVISIBILITY_BARRIER = newValue)
                .build());

            ConfigCategory zoom = builder.getOrCreateCategory(Text.translatable("title.enhancements.option.zoom"));
            zoom.addEntry(entryBuilder.startBooleanToggle(Text.translatable("title.enhancements.option.zoom.smooth"), ClientEnhancements.config.getConfig().ZOOM_SMOOTH)
                .setTooltip(Text.translatable("title.enhancements.option.zoom.smooth.tooltip"))
                .setDefaultValue(defaultConfig.ZOOM_SMOOTH)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().ZOOM_SMOOTH = newValue)
                .build());
            zoom.addEntry(entryBuilder.startLongSlider(Text.translatable("title.enhancements.option.zoom.smoothspeed"), ClientEnhancements.config.getConfig().ZOOM_SPEED, 50, 1500)
                .setTooltip(Text.translatable("title.enhancements.option.zoom.smoothspeed.tooltip"))
                .setDefaultValue(defaultConfig.ZOOM_SPEED)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().ZOOM_SPEED = newValue)
                .build());
            zoom.addEntry(entryBuilder.startDoubleField(Text.translatable("title.enhancements.option.zoom.level"), ClientEnhancements.config.getConfig().ZOOM_LEVEL)
                .setTooltip(Text.translatable("title.enhancements.option.zoom.level.tooltip"))
                .setDefaultValue(defaultConfig.ZOOM_LEVEL)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().ZOOM_LEVEL = newValue)
                .build());


            ConfigCategory xray = builder.getOrCreateCategory(Text.translatable("title.enhancements.option.xray"));
            xray.addEntry(entryBuilder.startStrList(Text.translatable("title.enhancements.option.xray.items"), ClientEnhancements.config.getConfig().XRAY_MATERIALS)
                .setTooltip(Text.translatable("title.enhancements.option.xray.items.tooltip"))
                .setDefaultValue(defaultConfig.XRAY_MATERIALS)
                .setSaveConsumer(newValue -> ClientEnhancements.config.getConfig().XRAY_MATERIALS = newValue)
                .build());

            return builder.build();
        };
    }
}
