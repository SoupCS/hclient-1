package neko.itskekoffcode.hclient.gui;

import java.io.IOException;

import neko.itskekoffcode.hclient.gui.multiplayer.MonitoringParser;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GuiHackTools extends GuiScreen {
    private final GuiScreen parentScreen;

    public GuiHackTools(GuiScreen guiscreen) {
        this.parentScreen = guiscreen;
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id == 0) {
                this.mc.displayGuiScreen(parentScreen);
            } else if (guibutton.id == 1) {
                this.mc.displayGuiScreen(new GuiProxy(this));
            } else if (guibutton.id == 2) {
                this.mc.displayGuiScreen(new GuiOfflineName(this));
            } else if (guibutton.id == 3) {
                this.mc.displayGuiScreen(new GuiBungeeIpNick(this));
            } else if (guibutton.id == 4) {
                (new Thread(() -> {
                    GuiFTP_SSH_MySQL_Brute ps3 = new GuiFTP_SSH_MySQL_Brute();
                    ps3.setVisible(true);
                })).start();
            } else if (guibutton.id == 5) {
                this.mc.displayGuiScreen(new GuiSpoofUUID(this));
            } else if (guibutton.id == 6) {
                (new Thread(() -> {
                    GuiSubdomainBrute ps = new GuiSubdomainBrute();
                    ps.setVisible(true);
                })).start();
            } else if (guibutton.id == 7) {
                (new Thread(() -> {
                    GuiServerFinder ps4 = new GuiServerFinder();
                    ps4.setVisible(true);
                })).start();
            } else if (guibutton.id == 8) {
                (new Thread(() -> {
                    GuiPortScanner ps5 = new GuiPortScanner();
                    ps5.setVisible(true);
                })).start();
            } else if (guibutton.id == 9) {
                (new Thread(() -> {
                    GuiHttpGetBrute ps6 = new GuiHttpGetBrute();
                    ps6.setVisible(true);
                })).start();
            } else if (guibutton.id == 10) {
                this.mc.displayGuiScreen(new MonitoringParser(this));
            }
        }
    }

    protected void mouseClicked(int i, int j, int k) throws Exception {
        super.mouseClicked(i, j, k);
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(7, width / 2 - 100, height / 4 - 48 + 12, "Server Finder"));
        this.buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 - 24 + 12, "Subdomain Brute"));
        this.buttonList.add(new GuiButton(8, width / 2 - 100, height / 4 + 12, "PortScanner"));
        this.buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + 12, "FTP/SSH/MySQL Brute"));
        this.buttonList.add(new GuiButton(6, width / 2 - 100, height / 4 + 48 + 12, "Spoof UUID"));
        this.buttonList.add(new GuiButton(9, width / 2 - 100, height / 4 + 72 + 12, "HTTP/HTTPS GET Method Brute"));
        this.buttonList.add(new GuiButton(10, width / 2 - 100, height / 4 + 96 + 12, "Monitoring parser"));
        this.buttonList.add(new GuiButton(3, width / 2 - 100, height / 4 + 120 + 12, "Bungee Offline UUID Spoof"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 144 + 12, "Offline Name"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 168 + 12, "Proxy"));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 192 + 12, "Cancel"));
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        super.drawScreen(i, j, f);
    }
}
