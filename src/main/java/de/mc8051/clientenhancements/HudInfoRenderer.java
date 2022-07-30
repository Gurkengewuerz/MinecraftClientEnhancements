package de.mc8051.clientenhancements;

import de.mc8051.clientenhancements.client.ClientEnhancementsClient;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.List;
import java.util.TreeMap;

public class HudInfoRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(MatrixStack matrices, float delta) {
        if (!ClientEnhancements.config.getConfig().HUD_VISIBLE) return;
        final TreeMap<String, Boolean> sortedStates = ClientEnhancementsClient.getKeyBindingController().getSortedStates();
        final List<String> activeMods = sortedStates.keySet().stream().filter(s -> ClientEnhancementsClient.getKeyBindingController().getState(s)).map(key -> Text.translatable(key).getString()).toList();
        drawText(matrices, activeMods);
    }

    private void drawText(MatrixStack matrices, List<String> texts) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.inGameHud.getTextRenderer();

        final int stringHeight = textRenderer.fontHeight;
        for (int index = 0; index < texts.size(); index++) {
            final String text = texts.get(index);
            final Text generatedText = Text.of(text);

            textRenderer.drawWithShadow(
                    matrices,
                    generatedText,
                    2, 2 + ((2 + stringHeight) * index),
                    0xffffff
            );
        }
    }

}
