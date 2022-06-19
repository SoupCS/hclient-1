package neko.itskekoffcode.hclient.gui;

import java.io.IOException;
import java.net.Proxy;

import neko.itskekoffcode.hclient.proxy.ProxyManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public final class GuiProxy extends GuiScreen {
    static GuiTextField ip;
    static GuiScreen before;
    private static boolean isRunning;
    private GuiButton button;
    private String status;
    public static String renderText = "";

    public GuiProxy(GuiScreen before) {
        GuiProxy.before = before;
    }

    protected void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0:
                if (!isRunning) {
                    String[] split = ip.getText().split(":");
                    if (split.length == 2) {
                        ProxyManager.setProxy(ProxyManager.getProxyFromString(ip.getText()));
                        this.status = "&5Proxy used " + ProxyManager.getProxy().address().toString();
                        renderText = "&aSuccessful";
                        isRunning = true;
                        button.displayString = "&cDisconnect";
                    } else {
                        this.status = "&cPlease use: <host>:<port>";
                    }
                } else {
                    isRunning = false;
                    button.displayString = "&aConnect";
                    ProxyManager.setProxy((Proxy)null);
                }
                break;
            case 1:
                this.mc.displayGuiScreen(before);
        }

    }

    public void drawScreen(int x, int y, float z) {
        new ScaledResolution(this.mc);
        this.drawDefaultBackground();
        GL11.glPushMatrix();
        GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
        GL11.glScaled(4.0D, 4.0D, 4.0D);
        drawCenteredString(this.mc.fontRendererObj, renderText, width / 8, height / 4 - this.mc.fontRendererObj.FONT_HEIGHT, 0);
        GL11.glPopMatrix();
        drawCenteredString(this.mc.fontRendererObj, this.status, width / 2, 20, -1);
        ip.drawTextBox();
        drawCenteredString(this.mc.fontRendererObj, "&7Proxy IP:Port", width / 2, 50, -1);
        super.drawScreen(x, y, z);
    }

    public void initGui() {
        renderText = "";
        this.buttonList.add(this.button = new GuiButton(0, width / 2 - 100, height / 3 + 40, 200, 20, !isRunning ? "&aConnect" : "&cDisconnect"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 3 + 66, 200, 20, "Back"));
        ip = new GuiTextField(height, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        ip.setMaxStringLength(100);
        ip.setText("127.0.0.1:8080");
        this.status = "&5Waiting...";
        ip.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }

        ip.textboxKeyTyped(character, key);
    }

    protected void mouseClicked(int x, int y, int button) {
        try {
            super.mouseClicked(x, y, button);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        ip.mouseClicked(x, y, button);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        ip.updateCursorCounter();
    }
}