//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\itskekoff\Documents\mcp\conf"!

package wdl.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import wdl.MessageTypeCategory;
import wdl.WDL;
import wdl.WDLMessages;
import wdl.api.IWDLMessageType;

public class GuiWDLMessages extends GuiScreen {
	/**
	 * Set from inner classes; this is the text to draw.
	 */
	private String hoveredButtonDescription = null;
	
	private class GuiMessageTypeList extends GuiListExtended {
		public GuiMessageTypeList() {
			super(GuiWDLMessages.this.mc, GuiScreen.width,
                    GuiScreen.height, 39,
					GuiScreen.height - 32, 20);
		}

		private class CategoryEntry implements IGuiListEntry {
			private final GuiButton button;
			private final MessageTypeCategory category;
			
			public CategoryEntry(MessageTypeCategory category) {
				this.category = category;
				this.button = new GuiButton(0, 0, 0, 80, 20, "");
			}
			
			@Override
			public boolean mousePressed(int slotIndex, int x, int y,
					int mouseEvent, int relativeX, int relativeY) {
				if (button.mousePressed(mc, x, y)) {
					WDLMessages.toggleGroupEnabled(category);
					
					button.playPressSound(mc.getSoundHandler());
					
					return true;
				}
				
				return false;
			}

			@Override
			public void mouseReleased(int slotIndex, int x, int y,
					int mouseEvent, int relativeX, int relativeY) {
				
			}



			@Override
			public void func_192633_a(int slotIndex, int x, int y, float partialTicks) {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void func_192634_a(int slotIndex, int x, int y, int listWidth,
					int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
				drawCenteredString(fontRendererObj, category.getDisplayName(),
						GuiScreen.width / 2 - 40, y + slotHeight
						- mc.fontRendererObj.FONT_HEIGHT - 1, 0xFFFFFF);
				
				button.xPosition = GuiScreen.width / 2 + 20;
				button.yPosition = y;
				
				button.displayString = I18n.format("wdl.gui.messages.group."
						+ WDLMessages.isGroupEnabled(category));
				button.enabled = WDLMessages.enableAllMessages;
				
				button.drawButton(mc, mouseX, mouseY, partialTicks);
				
			}
		}
		
		private class MessageTypeEntry implements IGuiListEntry {
			private final GuiButton button;
			private final IWDLMessageType type;
			private final MessageTypeCategory category;
			
			public MessageTypeEntry(IWDLMessageType type,
					MessageTypeCategory category) {
				this.type = type;
				this.button = new GuiButton(0, 0, 0, type.toString());
				this.category = category;
			}
			
			@Override
			public boolean mousePressed(int slotIndex, int x, int y,
					int mouseEvent, int relativeX, int relativeY) {
				if (button.mousePressed(mc, x, y)) {
					WDLMessages.toggleEnabled(type);
					
					button.playPressSound(mc.getSoundHandler());
					
					return true;
				}
				
				return false;
			}

			@Override
			public void mouseReleased(int slotIndex, int x, int y,
					int mouseEvent, int relativeX, int relativeY) {
			}


			@Override
			public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {

			}

			@Override
			public void func_192634_a(int slotIndex, int x, int y, int listWidth,
					int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
				button.xPosition = GuiScreen.width / 2 - 100;
				button.yPosition = y;

				button.displayString = I18n.format("wdl.gui.messages.message."
						+ WDLMessages.isEnabled(type), type.getDisplayName());
				button.enabled = WDLMessages.enableAllMessages &&
						WDLMessages.isGroupEnabled(category);

				button.drawButton(mc, mouseX, mouseY, partialTicks);

				if (button.isMouseOver()) {
					hoveredButtonDescription = type.getDescription();
				}

			}
		}
		
		private final List<IGuiListEntry> entries = new ArrayList<IGuiListEntry>() {{
				Map<MessageTypeCategory, Collection<IWDLMessageType>> map = 
						WDLMessages.getTypes().asMap();
				for (Map.Entry<MessageTypeCategory, Collection<IWDLMessageType>> e : map
						.entrySet()) {
				add(new CategoryEntry(e.getKey()));
				
				for (IWDLMessageType type : e.getValue()) {
					add(new MessageTypeEntry(type, e.getKey()));
				}
			}
		}};
		
		@Override
		public IGuiListEntry getListEntry(int index) {
			return entries.get(index);
		}

		@Override
		protected int getSize() {
			return entries.size();
		}
		
	}
	
	private final GuiScreen parent;
	private GuiMessageTypeList list;
	
	public GuiWDLMessages(GuiScreen parent) {
		this.parent = parent;
	}
	
	private GuiButton enableAllButton;
	private GuiButton resetButton;
	
	@Override
	public void initGui() {
		enableAllButton = new GuiButton(100, (width / 2) - 155, 18, 150,
				20, getAllEnabledText());
		this.buttonList.add(enableAllButton);
		resetButton = new GuiButton(101, (width / 2) + 5, 18, 150, 20,
				I18n.format("wdl.gui.messages.reset"));
		this.buttonList.add(resetButton);

		this.list = new GuiMessageTypeList();

		this.buttonList.add(new GuiButton(102, (width / 2) - 100,
				height - 29, I18n.format("gui.done")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (!button.enabled) {
			return;
		}
		
		if (button.id == 100) {
			//"Master switch"
			WDLMessages.enableAllMessages ^= true;
			
			WDL.baseProps.setProperty("Messages.enableAll",
					Boolean.toString(WDLMessages.enableAllMessages));
			
			button.displayString = getAllEnabledText();
		} else if (button.id == 101) {
			this.mc.displayGuiScreen(new GuiYesNo(this,
					I18n.format("wdl.gui.messages.reset.confirm.title"),
					I18n.format("wdl.gui.messages.reset.confirm.subtitle"),
					101));
		} else if (button.id == 102) {
			this.mc.displayGuiScreen(this.parent);
		}
	}
	
	@Override
	public void confirmClicked(boolean result, int id) {
		if (result) {
			if (id == 101) {
				WDLMessages.resetEnabledToDefaults();
			}
		}
		
		mc.displayGuiScreen(this);
	}
	
	@Override
	public void onGuiClosed() {
		WDL.saveProps();
	}
	
	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws Exception {
		super.handleMouseInput();
		this.list.handleMouseInput();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
			throws Exception {
		if (list.mouseClicked(mouseX, mouseY, mouseButton)) {
			return;
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (list.mouseReleased(mouseX, mouseY, state)) {
			return;
		}
		super.mouseReleased(mouseX, mouseY, state);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		hoveredButtonDescription = null;
		
		this.drawDefaultBackground();
		this.list.drawScreen(mouseX, mouseY, partialTicks);
		
		this.drawCenteredString(this.fontRendererObj,
				I18n.format("wdl.gui.messages.message.title"),
				width / 2, 8, 0xFFFFFF);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if (hoveredButtonDescription != null) {
			Utils.drawGuiInfoBox(hoveredButtonDescription, width, height, 48);
		} else if (enableAllButton.isMouseOver()) {
			Utils.drawGuiInfoBox(
					I18n.format("wdl.gui.messages.all.description"), width,
					height, 48);
		} else if (resetButton.isMouseOver()) {
			Utils.drawGuiInfoBox(
					I18n.format("wdl.gui.messages.reset.description"), width,
					height, 48);
		}
	}
	
	/**
	 * Gets the text for the "Enable all" button.
	 */
	private String getAllEnabledText() {
		return I18n.format("wdl.gui.messages.all."
				+ WDLMessages.enableAllMessages);
	}
}
