package neko.itskekoffcode.hclient.gui.multiplayer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

import neko.itskekoffcode.clientapi.utils.FindUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerListEntryLanScan;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.resources.I18n;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lwjgl.input.Keyboard;

public class MonitoringParser
        extends GuiScreen
        implements IGuiMultiplayer {
    private final ServerPinger oldServerPinger = new ServerPinger();
    private final GuiScreen parentScreen;
    private ServerSelectionList serverListSelector;
    private ServerList savedServerList;
    private GuiTextField domain;
    public static ArrayList<String> servers = new ArrayList();

    public MonitoringParser(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.savedServerList = new ServerList(this.mc);
        this.buttonList.add(new GuiButton(0, 2, height /2 - 40, "Get Servers"));
        this.buttonList.add(new GuiButton(1, 2, height /2 - 15, "Back"));
        this.buttonList.add(new GuiButton(4, 2, height /2 + 10, "Update servers"));
        this.domain = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, height - 50, 200, 20);
        this.domain.setMaxStringLength(1000);
        this.domain.setText("https://monitoringminecraft.ru/novie-servera-1.12.2");
        this.serverListSelector = new ServerSelectionList(this, this.mc, width, height, 32, height - 64, 36);
        this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
        this.selectServer(this.serverListSelector.getSelected());
    }

    @Override
    public void handleMouseInput() throws Exception {
        super.handleMouseInput();
        this.serverListSelector.handleMouseInput();
    }

    @Override
    public void updateScreen() {
        this.domain.updateCursorCounter();
        super.updateScreen();
        this.oldServerPinger.pingPendingNetworks();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.oldServerPinger.clearPendingNetworks();
    }

    public void parse(String domain) {
        try {
            String pattern = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?):\\d{1,5}\\b";
            ArrayList servers = new ArrayList();
            CopyOnWriteArrayList<String> temp = new CopyOnWriteArrayList<String>();
            Document document = Jsoup.connect(domain).get();
            Elements elements = document.getElementsByAttributeValue("class", "server");
            for (Element element : elements) {
                temp.addAll(FindUtils.findStringsByRegex(element.text(), Pattern.compile(pattern)));
            }
            temp.removeIf(str -> !str.matches(pattern));
            servers.addAll(temp);
            for (Object serv : servers) {
                this.getServerList().addShkoloServerData(new ServerData((String) serv, (String) serv, false));
            }
            this.getServerList().saveShkoloServerList();
            this.mc.displayGuiScreen(this);
        }
        catch (Exception eg2) {
            eg2.printStackTrace();
        }
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        GuiListExtended.IGuiListEntry guilistextended$iguilistentry;
        if (!guibutton.enabled) {
            return;
        }
        GuiListExtended.IGuiListEntry iGuiListEntry = guilistextended$iguilistentry = this.serverListSelector.getSelected() < 0 ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
        if (guibutton.id == 0) {
            this.parse(this.domain.getText());
        } else if (guibutton.id == 1) {
            this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
        } else if (guibutton.id == 4) {
            for (int i = 0; i < this.getServerList().getShkoloservers().size(); ++i) {
                this.getServerList().removeShkoloServerData(i);
            }
            this.getServerList().saveServerList();
            this.parse(this.domain.getText());
        }
    }

    @Override
    public void confirmClicked(boolean result, int id2) {
        GuiListExtended.IGuiListEntry guilistextended$iguilistentry = this.serverListSelector.getSelected() < 0 ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws Exception {
        int i2 = this.serverListSelector.getSelected();
        GuiListExtended.IGuiListEntry guilistextended$iguilistentry = i2 < 0 ? null : this.serverListSelector.getListEntry(i2);
        this.domain.textboxKeyTyped(typedChar, keyCode);
        if (keyCode != 63) {
            if (i2 >= 0) {
                if (keyCode == 200) {
                    if (MonitoringParser.isShiftKeyDown()) {
                        if (i2 > 0 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
                            this.savedServerList.swapShkoloServers(i2, i2 - 1);
                            this.selectServer(this.serverListSelector.getSelected() - 1);
                            this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                            this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
                        }
                    } else if (i2 > 0) {
                        this.selectServer(this.serverListSelector.getSelected() - 1);
                        this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                        if (this.serverListSelector.getListEntry(this.serverListSelector.getSelected()) instanceof ServerListEntryLanScan) {
                            if (this.serverListSelector.getSelected() > 0) {
                                this.selectServer(this.serverListSelector.getSize() - 1);
                                this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                            } else {
                                this.selectServer(-1);
                            }
                        }
                    } else {
                        this.selectServer(-1);
                    }
                } else if (keyCode == 208) {
                    if (MonitoringParser.isShiftKeyDown()) {
                        if (i2 < this.savedServerList.countShkoloServers() - 1) {
                            this.savedServerList.swapShkoloServers(i2, i2 + 1);
                            this.selectServer(i2 + 1);
                            this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                            this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
                        }
                    } else if (i2 < this.serverListSelector.getSize()) {
                        this.selectServer(this.serverListSelector.getSelected() + 1);
                        this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                        if (this.serverListSelector.getListEntry(this.serverListSelector.getSelected()) instanceof ServerListEntryLanScan) {
                            if (this.serverListSelector.getSelected() < this.serverListSelector.getSize() - 1) {
                                this.selectServer(this.serverListSelector.getSize() + 1);
                                this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                            } else {
                                this.selectServer(-1);
                            }
                        }
                    } else {
                        this.selectServer(-1);
                    }
                } else if (keyCode != 28 && keyCode != 156) {
                    super.keyTyped(typedChar, keyCode);
                } else {
                    this.actionPerformed(this.buttonList.get(2));
                }
            } else {
                super.keyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    public void drawScreen(int i2, int j2, float f2) {
        this.drawDefaultBackground();
        this.serverListSelector.drawScreen(i2, j2, f2);
        this.drawCenteredString(this.fontRendererObj, I18n.format("Monitoring Parser | Loaded " + this.getServerList().getShkoloservers().size() + " servers"), width / 2, 20, 0xFFFFFF);
        this.domain.drawTextBox();
        super.drawScreen(i2, j2, f2);
    }

    @Override
    public void connectToSelected() {
        GuiListExtended.IGuiListEntry guilistextended$iguilistentry;
        GuiListExtended.IGuiListEntry iGuiListEntry = guilistextended$iguilistentry = this.serverListSelector.getSelected() < 0 ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
        if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
            this.connectToServer(((ServerListEntryNormal)guilistextended$iguilistentry).getServerData());
        } else if (guilistextended$iguilistentry instanceof ServerListEntryLanDetected) {
            LanServerInfo lanserverinfo = ((ServerListEntryLanDetected)guilistextended$iguilistentry).getServerData();
            this.connectToServer(new ServerData(lanserverinfo.getServerMotd(), lanserverinfo.getServerIpPort(), true));
        }
    }

    private void connectToServer(ServerData server) {
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, server));
    }

    @Override
    public void selectServer(int index) {
        GuiListExtended.IGuiListEntry guilistextended$iguilistentry;
        this.serverListSelector.setSelectedSlotIndex(index);
        GuiListExtended.IGuiListEntry iGuiListEntry = guilistextended$iguilistentry = index < 0 ? null : this.serverListSelector.getListEntry(index);
        if (guilistextended$iguilistentry != null && !(guilistextended$iguilistentry instanceof ServerListEntryLanScan)) {
            boolean cfr_ignored_0 = guilistextended$iguilistentry instanceof ServerListEntryNormal;
        }
    }

    @Override
    public ServerPinger getOldServerPinger() {
        return this.oldServerPinger;
    }

    @Override
    public void setHoveringText(String p_146793_1_) {
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws Exception {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.domain.mouseClicked(mouseX, mouseY, mouseButton);
        this.serverListSelector.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.serverListSelector.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public ServerList getServerList() {
        return this.savedServerList;
    }

    @Override
    public boolean canMoveUp(ServerListEntryNormal p_175392_1_, int p_175392_2_) {
        return p_175392_2_ > 0;
    }

    @Override
    public boolean canMoveDown(ServerListEntryNormal p_175394_1_, int p_175394_2_) {
        return p_175394_2_ < this.savedServerList.countShkoloServers() - 1;
    }

    @Override
    public void moveServerUp(ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_) {
        int i2 = p_175391_3_ ? 0 : p_175391_2_ - 1;
        this.savedServerList.swapShkoloServers(p_175391_2_, i2);
        if (this.serverListSelector.getSelected() == p_175391_2_) {
            this.selectServer(i2);
        }
        this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
    }

    @Override
    public void moveServerDown(ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_) {
        int i2 = p_175393_3_ ? this.savedServerList.countShkoloServers() - 1 : p_175393_2_ + 1;
        this.savedServerList.swapShkoloServers(p_175393_2_, i2);
        if (this.serverListSelector.getSelected() == p_175393_2_) {
            this.selectServer(i2);
        }
        this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
    }
}
