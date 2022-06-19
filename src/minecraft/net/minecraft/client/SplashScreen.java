//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\�������\Desktop\mcp950\conf"!

package net.minecraft.client;

import neko.itskekoffcode.clientapi.utils.DrawHelper;
import neko.itskekoffcode.hclient.HClient;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class SplashScreen implements IProgressUpdate
{
    private String message = "";
    private final Minecraft mc;
    private String currentlyDisplayedText = "";
    private long systemTime = Minecraft.getSystemTime();
    private boolean loadingSuccess;
    private final ScaledResolution scaledResolution;
    private final Framebuffer framebuffer;
    private final boolean minecraftTexture;
    private long start;
    public boolean displayed = false;

    /**
     * Starting loading screen
     * @param mcIn Minecraft
     */
    public SplashScreen(Minecraft mcIn, boolean minecraftTexture) {
        this.mc = mcIn;
        this.scaledResolution = new ScaledResolution(mcIn);
        this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
        this.framebuffer.setFramebufferFilter(9728);
        this.minecraftTexture = minecraftTexture;
        this.start = System.currentTimeMillis();
    }

    /**
     * Resetting progress and settings message
     * @param message Message to be displayed
     */
    public void resetProgressAndMessage(String message) {
        this.loadingSuccess = false;
        this.displayString(message);
    }

    /**
     * Setting loading success to true, displays String.
     * @param message Message to be displayed
     */
    public void displaySavingString(String message) {
        this.loadingSuccess = true;
        this.displayString(message);
    }

    /**
     * Displays String
     * @param message Message to be displayed
     */
    public void displayString(String message)
    {
        this.currentlyDisplayedText = message;

        if (!this.mc.running) {
            if (!this.loadingSuccess) {
                throw new MinecraftError();
            }
        } else {
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();

            if (OpenGlHelper.isFramebufferEnabled()) {
                int i = this.scaledResolution.getScaleFactor();
                GlStateManager.ortho(0.0D, this.scaledResolution.getScaledWidth() * i, this.scaledResolution.getScaledHeight() * i, 0.0D, 100.0D, 300.0D);
            } else {
                ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
            }

            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -200.0F);
        }
    }

    /**
     * Displays loading string
     * @param message Message to be displayed
     */
    public void displayLoadingString(String message) {
        if (!this.mc.running) {
            if (!this.loadingSuccess) {
                throw new MinecraftError();
            }
        } else {
            this.systemTime = 0L;
            this.message = message;
            this.setLoadingProgress(-1, "");
            this.displayed = false;
            this.systemTime = 0L;
        }
    }

    /**
     * Updates the progress bar on the loading screen to the specified amount.
     * @param progress Progress to be displayed (0-100)
     */
    public void setLoadingProgress(int progress, String status) {
        if (!this.mc.running) {
            if (!this.loadingSuccess) {
                throw new MinecraftError();
            }
        } else {
            long i = Minecraft.getSystemTime();

            if (i - this.systemTime >= 100L) {
                this.systemTime = i;
                ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                int j = scaledresolution.getScaleFactor();
                int k = scaledresolution.getScaledWidth();
                int l = scaledresolution.getScaledHeight();

                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.framebuffer.framebufferClear();
                } else {
                    GlStateManager.clear(256);
                }

                this.framebuffer.bindFramebuffer(false);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0F, 0.0F, -200.0F);

                if (!OpenGlHelper.isFramebufferEnabled()) {
                    GlStateManager.clear(16640);
                }
                DrawHelper.drawRect(0, 0, Display.getWidth(), Display.getHeight(), HClient.getClientColorBackground().getRGB()); //- gray background
                //DrawHelper.drawRect(0, 0, Display.getWidth(), Display.getHeight(), new Color(255, 255, 255).getRGB());
                int width = 200;
                int height = 2;
                int y = l / 2 + 50;
                int x = k / 2 - width / 2;
                if (progress >= 0) {
                    //DrawHelper.drawRect(0, 0, Display.getWidth(), Display.getHeight(), new Color(255, 255, 255).getRGB());
                    DrawHelper.drawImage(new ResourceLocation("hclient/ua.jpg"), k / 2 - 100 / 2, l / 2 - 50, 100, 50, Color.white);
                    displayed = true;
                    GlStateManager.disableTexture2D();
                    DrawHelper.drawSmoothRectBetter(x, y, width, height, Color.gray.getRGB());
                    DrawHelper.drawSmoothRectBetter(x, y, progress * 2 + 2, height, Color.GREEN.getRGB());
                    //mc.neverlose900_15.drawString("Starting Be..HClient", x + 25, y, Color.BLACK.getRGB());
                    GlStateManager.enableTexture2D();
                }

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                this.mc.fontRendererObj.drawStringWithShadow(this.currentlyDisplayedText, (float)((k - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2), (float)(l / 2 - 4 - 16), 16777215);
                this.mc.fontRendererObj.drawStringWithShadow(this.message, (float)((k - this.mc.fontRendererObj.getStringWidth(this.message)) / 2), (float)(l / 2 - 4 + 8), 16777215);
                this.mc.fontRendererObj.drawStringWithShadow(status, x, y - 10, new Color(255, 255, 255).getRGB());
                this.framebuffer.unbindFramebuffer();

                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.framebuffer.framebufferRender(k * j, l * j);
                }

                this.mc.updateDisplay();

                try {
                    Thread.yield();
                } catch (Exception ignored) {}
            }
        }
    }

    /**
     * Setting progress bar done.
     */
    public void setDoneWorking() {
        displayed = false;
    }
}
