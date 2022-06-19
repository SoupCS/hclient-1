package neko.itskekoffcode.hclient.rcon;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

public class GuiRconConnection extends GuiScreen {
    private GuiTextField nameBox;
    private GuiTextField ipBox;
    private GuiTextField passwordBox;
    private GuiTextField commandBox;
    private GuiTextField portBox;
    
	private final GuiScreen before;
    
	public GuiRconConnection(GuiScreen before) {
		this.before = before;
		}

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 115, "�aStart"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 140, "Back"));
        
        this.ipBox = new GuiTextField(1, this.fontRendererObj, width / 2 - 100, height / 40 + 40, 90, 20);
        this.ipBox.setFocused(false);
        this.ipBox.setText("127.0.0.1");
        
        this.portBox = new GuiTextField(1, this.fontRendererObj, width / 2 + 10, height / 40 + 40, 90, 20);
        this.portBox.setFocused(false);
        this.portBox.setText("25565");
        
        this.commandBox = new GuiTextField(1, this.fontRendererObj, width / 2 - 100, height / 50 + 85, 200, 20);
        this.commandBox.setFocused(false);
        this.commandBox.setText("/op " + mc.getSession().getUsername());
        
        this.nameBox = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, height / 50 + 130, 90, 20);
        this.nameBox.setMaxStringLength(48);
        this.nameBox.setFocused(true);
        this.nameBox.setText(mc.getSession().getUsername());
        
        this.passwordBox = new GuiTextField(1, this.fontRendererObj, width / 2 + 10, height / 50 + 130, 90, 20);
        this.passwordBox.setFocused(false);
        this.passwordBox.setText("Password");


    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton clickedButton) {
        if (clickedButton.id == 1) {
            mc.displayGuiScreen(before);
            
        } else if (!(clickedButton.id != 0 || this.nameBox.getText().isEmpty() || this.passwordBox.getText().isEmpty() || this.ipBox.getText().isEmpty() || this.commandBox.getText().isEmpty() || this.portBox.getText().isEmpty())) {
            Throwable throwable = null;
            Object var3_4 = null;
            try {
                try (RconClient client = RconClient.open(this.ipBox.getText(), Integer.valueOf(this.portBox.getText()), this.passwordBox.getText())){
                    client.sendCommand(this.commandBox.getText());
                }
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.nameBox.textboxKeyTyped(par1, par2);
        this.passwordBox.textboxKeyTyped(par1, par2);
        this.ipBox.textboxKeyTyped(par1, par2);
        this.commandBox.textboxKeyTyped(par1, par2);
        this.portBox.textboxKeyTyped(par1, par2);
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws Exception {
        super.mouseClicked(par1, par2, par3);
        this.nameBox.mouseClicked(par1, par2, par3);
        this.passwordBox.mouseClicked(par1, par2, par3);
        this.ipBox.mouseClicked(par1, par2, par3);
        this.commandBox.mouseClicked(par1, par2, par3);
        this.portBox.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawString(this.fontRendererObj, "Server IP", width / 2 - 80, height / 50 + 30, 10526880);
        this.drawString(this.fontRendererObj, "Port", width / 2 + 45, height / 50 + 30, 10526880);
        this.drawString(this.fontRendererObj, "Command", width / 2 - 20, height / 50 + 75, 10526880);
        this.drawString(this.fontRendererObj, "Username", width / 2 - 80, height / 50 + 120, 10526880);
        this.drawString(this.fontRendererObj, "Password", width / 2 + 30, height / 50 + 120, 10526880);
        this.nameBox.drawTextBox();
        this.passwordBox.drawTextBox();
        this.ipBox.drawTextBox();
        this.commandBox.drawTextBox();
        this.portBox.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

