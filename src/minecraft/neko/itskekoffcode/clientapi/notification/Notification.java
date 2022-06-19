package neko.itskekoffcode.clientapi.notification;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;

public class Notification {
    private final NotificationType type;
    private final String title;
    private final String messsage;
    private long start;
    private final long fadedIn;
    private final long fadeOut;
    private final long end;

    public Notification(NotificationType type, String title, String messsage, int length) {
        this.type = type;
        this.title = title;
        this.messsage = messsage;
        this.fadedIn = 200L * length;
        this.fadeOut = this.fadedIn + (500L * length);
        this.end = this.fadeOut + this.fadedIn;
    }

    public void show() {
        this.start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return this.getTime() <= this.end;
    }

    private long getTime() {
        return System.currentTimeMillis() - this.start;
    }

    public void render() {
        double offset = 0.0D;
        int width = 120;
        int height = 25;
        long time = this.getTime();
        if (time < this.fadedIn) {
            offset = Math.tanh((double) time / (double) this.fadedIn * 3.0D) * (double) width;
        } else if (time > this.fadeOut) {
            offset = Math.tanh(3.0D - (double) (time - this.fadeOut) / (double) (this.end - this.fadeOut) * 3.0D) * (double) width;
        } else {
            offset = (double) width;
        }

        Color color = new Color(0, 0, 0, 220);
        Color color1;
        if (this.type == NotificationType.INFO) {
            color1 = new Color(0, 26, 169);
        } else if (this.type == NotificationType.WARNING) {
            color1 = new Color(204, 193, 0);
        } else {
            color1 = new Color(204, 0, 18);
            int i = Math.max(0, Math.min(255, (int) (Math.sin((double) time / 100.0D) * 255.0D / 2.0D + 127.5D)));
            color = new Color(i, 0, 0, 220);
        }

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        drawRect((double) GuiScreen.width - offset, GuiScreen.height - 40 - height, (double) GuiScreen.width, (double) (GuiScreen.height - 30), color.getRGB());
        drawRect((double) GuiScreen.width - offset, (double) (GuiScreen.height - 40 - height), (double) GuiScreen.width - offset + 4.0D, (double) (GuiScreen.height - 30), color1.getRGB());
        fontRenderer.drawString(this.title, (int) ((double) GuiScreen.width - offset + 8.0D), GuiScreen.height - 35 - height, -1);
        fontRenderer.drawString(this.messsage, (int) ((double) GuiScreen.width - offset + 8.0D), GuiScreen.height - 45, -1);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        double j;
        if (left < right) {
            j = left;
            left = right;
            right = j;
        }

        if (top < bottom) {
            j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(int mode, double left, double top, double right, double bottom, int color) {
        double j;
        if (left < right) {
            j = left;
            left = right;
            right = j;
        }

        if (top < bottom) {
            j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(mode, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
