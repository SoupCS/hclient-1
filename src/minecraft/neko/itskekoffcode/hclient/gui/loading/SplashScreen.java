package neko.itskekoffcode.hclient.gui.loading;

import neko.itskekoffcode.clientapi.utils.DrawHelper;
import neko.itskekoffcode.clientapi.utils.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;

import java.awt.*;

public class SplashScreen {
    private static final int max = 7;
    private static int progress = 0;
    private static String current = "";
    private static UnicodeFontRenderer font;

    public static void update() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) {
            return;
        }
        drawSplashScreen(Minecraft.getMinecraft().getTextureManager());
    }

    public static void setProgress(int givenProgress, String text) {
        progress = givenProgress;
        current = text;
        update();
    }
    public static void drawSplashScreen(TextureManager tm) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        // draw background outline
        int x = (sr.getScaledWidth() - sr.getScaledWidth()) / 2;
        int y = (sr.getScaledHeight() - sr.getScaledHeight()) / 2;
        DrawHelper.drawBorderedRect(x, sr.getScaledHeight(), y, 2F, 1F, Color.WHITE.getRGB(), Color.GRAY.getRGB(), false);
        // draw inside
        DrawHelper.drawRect(x - 1, sr.getScaledHeight(), y - 15, sr.getScaledWidth(), Color.red.getRGB());
    }
}
