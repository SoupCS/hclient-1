//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\itskekoff\Documents\mcp\conf"!

package neko.itskekoffcode.clientapi.utils;


import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;

public final class GuiAntiDisconnect extends GuiScreen {
    private int disconnectCount;
    @NotNull
    private GuiButton button;

    public GuiAntiDisconnect() {
        this.disconnectCount = (AntiDisconnect.INSTANCE.getPresses());
        this.button = new GuiButton(1, width / 2 - 100, 230, this.getButtonText());
    }

    private final String getButtonText() {
        return (TextFormatting.RED + "Press me " + this.disconnectCount + " time(s) to disconnect.");
    }

    public void initGui() {
        super.initGui();
        this.button = new GuiButton(1, width / 2 - 100, 230, this.getButtonText());
        this.buttonList.add(new GuiButton(0, width / 2 - 100, 200, "Back to Game"));
        this.buttonList.add(this.button);
    }

    protected void actionPerformed(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        int var2 = button.id;
        switch(var2) {
            case 0:
                this.mc.displayGuiScreen(null);
                break;
            case 1:
                if (this.disconnectCount > 1) {
                    int var4 = this.disconnectCount;
                    this.disconnectCount = var4 + -1;
                    button.displayString = this.getButtonText();
                } else {
                    button.enabled = false;
                    if (this.mc.isIntegratedServerRunning()) {
                        this.mc.displayGuiScreen(new GuiWorldSelection(new GuiMainMenu()));
                    } else {
                        this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                    }

                    this.mc.world.sendQuittingDisconnectingPacket();
                    this.mc.loadWorld(null);
                }
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Disconnect Confirmation", width / 2, 40, ColorConverter.INSTANCE.rgbToHex(155, 144, 255));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
