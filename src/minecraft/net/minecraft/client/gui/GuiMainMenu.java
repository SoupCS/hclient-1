package net.minecraft.client.gui;

import java.io.IOException;

import neko.itskekoffcode.clientapi.utils.DrawHelper;
import neko.itskekoffcode.hclient.HClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiMainMenu extends GuiScreen {
    String[] buttons = {"Singleplayer", "Multiplayer", "Options", "Quit"};
    public void initGui() {
    }
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 3) mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        if (button.id == 1) mc.displayGuiScreen(new GuiWorldSelection(this));
        if (button.id == 2) mc.displayGuiScreen(new GuiMultiplayer(this));
        if (button.id == 4) mc.shutdown();
    }

    public void mouseClicked(int mouseX, int mouseY, int particalTicks) throws Exception {
        int count = 0;
        for (String name : buttons) {
            float x = (width / buttons.length) * count + (width / buttons.length) / 2f + 8
                    - mc.fontRendererObj.getStringWidth(name) / 2f;
            float y = height - 20;
            if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name)
                && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
                    switch (name) {
                        case "Singleplayer":
                            mc.displayGuiScreen(new GuiWorldSelection(this));
                            break;
                        case "Multiplayer":
                            mc.displayGuiScreen(new GuiMultiplayer(this));
                            break;
                        case "Options":
                            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                            break;
                        case "Quit":
                            mc.shutdown();
                            break;
                    }
            }
            count++;
        }
        super.mouseClicked(mouseX, mouseY, particalTicks);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("hclient/mainmenu.jpg"));
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, width, height, width, height);
        //DrawHelper.drawRect(0, 0, width, height, HClient.getClientColorBackground().getRGB());
        int count = 0;
        for (String name : buttons) {
            float x = (width / buttons.length) * count + (width / buttons.length) / 2f + 8 - mc.fontRendererObj.getStringWidth(name) / 2f;
            float y = height - 20;
            boolean hovered = (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT);
            this.drawCenteredString(mc.fontRendererObj, name, (int) ((width / buttons.length) * count + (width / buttons.length / 2f + 8)), (int) y, hovered ? 0x4F0381 : -1);
            count++;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0D).tex(u * f, (v + (float)height) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0D).tex((u + (float)width) * f, (v + (float)height) * f1).endVertex();
        bufferbuilder.pos(x + width, y, 0.0D).tex((u + (float)width) * f, v * f1).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }
}
