package neko.itskekoffcode.hclient.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

public class GuiOfflineName extends GuiScreen {
    private final GuiScreen parentScreen;
    private GuiTextField usernameTextField;

    public GuiOfflineName(GuiScreen guiscreen) {
        this.parentScreen = guiscreen;
    }

    public void updateScreen() {
        this.usernameTextField.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id == 1) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (guibutton.id == 0) {
                this.mc.session = new Session(this.usernameTextField.getText(), "", "", "");
            }

            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    protected void keyTyped(char c, int i) {
        this.usernameTextField.textboxKeyTyped(c, i);
        if (c == '\t' && this.usernameTextField.isFocused()) {
            this.usernameTextField.setFocused(false);
        }

        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }

    }

    protected void mouseClicked(int i, int j, int k) throws Exception {
        super.mouseClicked(i, j, k);
        this.usernameTextField.mouseClicked(i, j, k);
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Done"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Cancel"));
        this.usernameTextField = new GuiTextField(2, this.fontRendererObj, width / 2 - 100, 116, 200, 20);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Change nick", width / 2, height / 4 - 60 + 20, 16777215);
        this.drawString(this.fontRendererObj, "Nick", width / 2 - 100, 104, 10526880);
        this.usernameTextField.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
