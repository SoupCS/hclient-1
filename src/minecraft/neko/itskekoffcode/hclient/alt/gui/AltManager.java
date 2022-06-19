package neko.itskekoffcode.hclient.alt.gui;

import neko.itskekoffcode.hclient.alt.AltHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class AltManager extends GuiScreen
{
    private final GuiScreen parentScreen;
    private GuiTextField userNameField;
    String version;
    private final Minecraft mc = Minecraft.getMinecraft();

    public AltManager(final GuiScreen parentScreen) {
        this.version = "v1.6";
        this.parentScreen = parentScreen;
    }

    public void updateScreen() {
        this.userNameField.updateCursorCounter();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(0, width / 2 - 50, height / 4 + 96 + 18, 100, 20, I18n.format("Change User name")));
        this.buttonList.add(new GuiButton(1, width / 2 - 50, height / 4 + 120 + 18, 100, 20, I18n.format("Done")));
        (this.userNameField = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, 106, 190, 20)).setFocused(true);
        this.userNameField.setMaxStringLength(30);
        this.userNameField.setTextColor(16776960);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 0) {
            AltHelper.setusername(this.userNameField.getText());
            this.parentScreen.confirmClicked(true, 0);
        }
    }

    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        this.userNameField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 15) {
            this.userNameField.setFocused(!this.userNameField.isFocused());
        }
        if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws Exception {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.userNameField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawString(this.fontRendererObj, I18n.format("Current nickname: " + this.mc.getSession().getUsername()), width / 2 - 100, 53, 65535);
        this.drawString(this.fontRendererObj, I18n.format("New nickname:"), width / 2 - 100, 93, 16777215);
        this.userNameField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}


