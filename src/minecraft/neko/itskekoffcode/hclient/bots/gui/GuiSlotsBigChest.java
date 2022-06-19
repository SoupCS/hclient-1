package neko.itskekoffcode.hclient.bots.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiSlotsBigChest extends GuiScreen {
    private static ResourceLocation CHEST_GUI_TEXTURE;
    private final int x = 176;
    private final int y = 166;

    public GuiSlotsBigChest() {
        CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void drawScreen(int w, int h, float p_73863_3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int offsetFromScreenLeft = (width - x) / 2;
        drawTexturedModalRect(offsetFromScreenLeft, 2, 0, 0, x, y);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws Exception {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
