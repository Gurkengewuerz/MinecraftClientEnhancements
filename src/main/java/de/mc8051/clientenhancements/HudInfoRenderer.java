package de.mc8051.clientenhancements;

import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HudInfoRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (!ClientEnhancements.config.getConfig().HUD_VISIBLE) return;
        final TreeMap<String, Boolean> sortedStates = ClientEnhancementsClient.getKeyController().getSortedStates();
        final List<String> activeMods = sortedStates.entrySet().stream().filter(Map.Entry::getValue).map(entry -> Text.translatable(entry.getKey()).getString()).toList();
        drawText(drawContext, activeMods);
    }

    private void drawText(DrawContext drawContext, List<String> texts) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.inGameHud.getTextRenderer();

        final int stringHeight = textRenderer.fontHeight;
        for (int index = 0; index < texts.size(); index++) {
            final String text = texts.get(index);
            final Text generatedText = Text.of(text);

            drawContext.drawText(
                textRenderer,
                generatedText,
                2, 2 + ((2 + stringHeight) * index),
                0xffffff, true
            );
        }
    }

}
