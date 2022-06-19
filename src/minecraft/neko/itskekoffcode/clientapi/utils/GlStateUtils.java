//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\itskekoff\Documents\mcp\conf"!

package neko.itskekoffcode.clientapi.utils;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class GlStateUtils {
    @NotNull
    public static final GlStateUtils INSTANCE = new GlStateUtils();
    @NotNull
    private static final Minecraft mc = Minecraft.getMinecraft();
    @Nullable
    private static Quad<Integer, Integer, Integer, Integer> lastScissor;
    @NotNull
    private static final ArrayList<Quad<Integer, Integer, Integer, Integer>> scissorList;

    private GlStateUtils() {
    }

    public final void scissor(int x, int y, int width, int height) {
        lastScissor = new Quad(x, y, width, height);
        GL11.glScissor(x, y, width, height);
    }

    public final void pushScissor() {
        Quad var1 = lastScissor;
        if (var1 != null) {
            boolean var3 = false;
            boolean var4 = false;
            boolean var6 = false;
            scissorList.add(var1);
        }

    }

    public final void popScissor() {
        Quad var1 = (Quad) CollectionsKt.removeLastOrNull((List)scissorList);
        if (var1 != null) {
            boolean var3 = false;
            boolean var4 = false;
            boolean var6 = false;
            INSTANCE.scissor(((Number)var1.getFirst()).intValue(), ((Number)var1.getSecond()).intValue(), ((Number)var1.getThird()).intValue(), ((Number)var1.getFourth()).intValue());
        }

    }

    public final boolean useVbo() {
        return mc.gameSettings.useVbo;
    }

    public final void alpha(boolean state) {
        if (state) {
            GlStateManager.enableAlpha();
        } else {
            GlStateManager.disableAlpha();
        }

    }

    public final void blend(boolean state) {
        if (state) {
            GlStateManager.enableBlend();
        } else {
            GlStateManager.disableBlend();
        }

    }

    public final void smooth(boolean state) {
        if (state) {
            GlStateManager.shadeModel(7425);
        } else {
            GlStateManager.shadeModel(7424);
        }

    }

    public final void lineSmooth(boolean state) {
        if (state) {
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
        } else {
            GL11.glDisable(2848);
        }

    }

    public final void depth(boolean state) {
        if (state) {
            GlStateManager.enableDepth();
        } else {
            GlStateManager.disableDepth();
        }

    }

    public final void texture2d(boolean state) {
        if (state) {
            GlStateManager.enableTexture2D();
        } else {
            GlStateManager.disableTexture2D();
        }

    }

    public final void cull(boolean state) {
        if (state) {
            GlStateManager.enableCull();
        } else {
            GlStateManager.disableCull();
        }

    }

    public final void lighting(boolean state) {
        if (state) {
            GlStateManager.enableLighting();
        } else {
            GlStateManager.disableLighting();
        }

    }

    public final void rescaleMc() {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        this.rescale(resolution.getScaledWidth_double(), resolution.getScaledHeight_double());
    }

    public final void rescale(double width, double height) {
        GlStateManager.clear(256);
        GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, width, height, 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }

    static {
        Minecraft mc;
        mc = Minecraft.getMinecraft();
        scissorList = new ArrayList();
    }
}
