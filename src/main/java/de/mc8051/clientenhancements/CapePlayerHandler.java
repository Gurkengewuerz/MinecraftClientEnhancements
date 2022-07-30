package de.mc8051.clientenhancements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;

public class CapePlayerHandler {

    private static String API = "https://mc8051.de/minecraft/capes";

    private static HashMap<String, Boolean> capeUsers = new HashMap<>();
    private static HashMap<String, CapePlayerHandler> capes = new HashMap<>();


    private UUID uuid;
    private boolean hasCape = false;
    private boolean hasElytraTexture = true;

    private boolean isAnimated = false;
    int maxFrames = 0;
    int currentFrame = 0;
    long lastFrameTime = 0L;

    public CapePlayerHandler(UUID uuid) {
        this.uuid = uuid;
        ForkJoinPool.commonPool().submit(this::load);
    }

    private void load() {
        if (setCape(connection(API + "/" + this.uuid.toString() + ".png"))) {
            System.out.println("Successfully read cape for " + uuid.toString());
        } else System.err.println("Failed to read cape for " + uuid.toString());
    }

    public Identifier getCape() {
        if (!isAnimated) return new Identifier(uuid.toString());

        final long now = System.currentTimeMillis();
        if (now > lastFrameTime + 100L) {
            currentFrame = (currentFrame + 1) % maxFrames;
            lastFrameTime = now;
        }
        return new Identifier(uuid.toString() + "/" + currentFrame);
    }

    private boolean setCape(HttpURLConnection connection) {
        try {
            if (connection == null) return false;
            connection.connect();
            if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300) {
                setCapeTexture(connection.getInputStream());
                return true;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return false;
    }

    private boolean setCapeTexture(InputStream image) {
        try {
            NativeImage cape = NativeImage.read(image);
            MinecraftClient.getInstance().submit(() -> {
                if (capeUsers.getOrDefault(uuid.toString(), false)) {
                    final ArrayList<NativeImage> animatedFrames = parseAnimatedCape(cape);

                    for (int frame = 0; frame < animatedFrames.size(); frame++) {
                        final NativeImage texture = animatedFrames.get(frame);
                        MinecraftClient.getInstance().getTextureManager().registerTexture(
                                new Identifier(uuid.toString() + "/" + frame), new NativeImageBackedTexture(texture)
                        );
                    }
                    hasElytraTexture = Math.floorDiv(animatedFrames.get(0).getWidth(), animatedFrames.get(0).getHeight()) == 2;
                    currentFrame = 0;
                    maxFrames = animatedFrames.size();
                    isAnimated = true;
                } else {
                    hasElytraTexture = Math.floorDiv(cape.getWidth(), cape.getHeight()) == 2;
                    MinecraftClient.getInstance().getTextureManager().registerTexture(
                            new Identifier(uuid.toString()), new NativeImageBackedTexture(parseCape(cape))
                    );
                    isAnimated = false;
                }
                hasCape = true;
            });
            return true;
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return false;
    }

    private static HttpURLConnection connection(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(url).openConnection(MinecraftClient.getInstance().getNetworkProxy()));
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            return connection;
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return null;
    }

    private NativeImage parseCape(NativeImage img) {
        System.out.println("parseCape()");
        int imageWidth = 64;
        int imageHeight = 32;
        int srcWidth = img.getWidth();
        int srcHeight = img.getHeight();
        while (imageWidth < srcWidth || imageHeight < srcHeight) {
            imageWidth *= 2;
            imageHeight *= 2;
        }
        NativeImage imgNew = new NativeImage(imageWidth, imageHeight, true);

        for (int x = 0; x < srcWidth; x++) {
            for (int y = 0; y < srcHeight; y++) {
                imgNew.setColor(x, y, img.getColor(x, y));
            }
        }
        img.close();
        return imgNew;
    }

    private ArrayList<NativeImage> parseAnimatedCape(NativeImage img) {
        final ArrayList<NativeImage> animatedCape = new ArrayList<>();
        var totalFrames = img.getHeight() / (img.getWidth() / 2);

        for (int frame = 0; frame < totalFrames; frame++) {
            NativeImage thisFrame = new NativeImage(img.getWidth(), img.getWidth() / 2, true);
            for (int x = 0; x < thisFrame.getWidth(); x++) {
                for (int y = 0; y < thisFrame.getHeight(); y++) {
                    thisFrame.setColor(x, y, img.getColor(x, y + (frame * (img.getWidth() / 2))));
                }
            }
            animatedCape.add(thisFrame);
        }

        return animatedCape;
    }


    public boolean hasCape() {
        return hasCape;
    }

    public boolean hasElytraTexture() {
        return hasElytraTexture;
    }

    public static void init() {
        try {
            final HttpURLConnection connection = connection(API + "/capes.json");
            if (connection == null) return;
            connection.connect();
            if (!(connection.getResponseCode() >= 200 && connection.getResponseCode() < 300)) return;
            JsonArray json = JsonParser.parseReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)).getAsJsonArray();
            for (int i = 0; i < json.size(); i++) {
                final JsonObject asJsonObject = json.get(i).getAsJsonObject();
                capeUsers.put(asJsonObject.get("uuid").getAsString(), asJsonObject.get("animated").getAsBoolean());
            }
            System.out.println("Fetched " + json.size() + " cape users");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static boolean isCapeUser(UUID uuid) {
        return capeUsers.containsKey(uuid.toString());
    }

    public static CapePlayerHandler getCapePlayerHandler(UUID uuid) {
        if (!isCapeUser(uuid)) return null;
        if (!capes.containsKey(uuid.toString()))
            capes.put(uuid.toString(), new CapePlayerHandler(uuid));
        return capes.get(uuid.toString());
    }
}
