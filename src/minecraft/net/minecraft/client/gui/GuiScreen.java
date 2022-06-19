package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import neko.itskekoffcode.clientapi.utils.DrawHelper;
import neko.itskekoffcode.hclient.HClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.storage.MapData;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<String> PROTOCOLS = Sets.newHashSet("http", "https");
    private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');

    /** Reference to the Minecraft object. */
    protected Minecraft mc;

    /**
     * Holds a instance of RenderItem, used to draw the achievement icons on screen (is based on ItemStack)
     */
    protected RenderItem itemRender;

    /** The width of the screen object. */
    public static int width1;
    public static int width;

    /** The height of the screen object. */
    public static int height1;
    public static int height;

    protected List<GuiButton> buttonList = Lists.newArrayList();
    protected List<GuiLabel> labelList = Lists.newArrayList();
    public boolean allowUserInput;

    /** The FontRenderer used by GuiScreen */
    protected FontRenderer fontRendererObj;

    /** The button that was just pressed. */
    protected GuiButton selectedButton;
    public int eventButton;
    private long lastMouseEvent;

    /**
     * Tracks the number of fingers currently on the screen. Prevents subsequent fingers registering as clicks.
     */
    private int touchValue;
    private URI clickedLinkURI;
    private boolean field_193977_u;

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        for (int i = 0; i < this.buttonList.size(); ++i)
        {
            this.buttonList.get(i).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }

        for (int j = 0; j < this.labelList.size(); ++j)
        {
            this.labelList.get(j).drawLabel(this.mc, mouseX, mouseY);
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws Exception
    {
        if (keyCode == 1)
        {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        }
    }

    protected <T extends GuiButton> T addButton(T p_189646_1_)
    {
        this.buttonList.add(p_189646_1_);
        return p_189646_1_;
    }

    /**
     * Returns a string stored in the system clipboard.
     */
    public static String getClipboardString()
    {
        try
        {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
            {
                return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception var1)
        {
        }

        return "";
    }

    /**
     * Stores the given string in the system clipboard
     */
    public static void setClipboardString(String copyText)
    {
        if (!StringUtils.isEmpty(copyText))
        {
            try
            {
                StringSelection stringselection = new StringSelection(copyText);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
            }
            catch (Exception var2)
            {
            }
        }
    }

    protected void renderToolTip(ItemStack stack, int x, int y)
    {

        this.drawHoveringText(this.func_191927_a(stack), x, y);
    }

    public List<String> func_191927_a(ItemStack p_191927_1_)
    {
        List<String> list = p_191927_1_.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);

        for (int i = 0; i < list.size(); ++i)
        {
            if (i == 0)
            {
                list.set(i, p_191927_1_.getRarity().rarityColor + list.get(i));
            }
            else
            {
                list.set(i, TextFormatting.GRAY + list.get(i));
            }
        }

        return list;
    }

    /**
     * Draws the text when mouse is over creative inventory tab. Params: current creative tab to be checked, current
     * mouse x position, current mouse y position.
     */
    public void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY)
    {
        this.drawHoveringText(Arrays.asList(tabName), mouseX, mouseY);
    }

    public void func_193975_a(boolean p_193975_1_)
    {
        this.field_193977_u = p_193975_1_;
    }

    public boolean func_193976_p()
    {
        return this.field_193977_u;
    }

    /**
     * Draws a List of strings as a tooltip. Every entry is drawn on a seperate line.
     */
    public void drawHoveringText(List<String> textLines, int x, int y)
    {
        if (!textLines.isEmpty())
        {
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int i = 0;

            for (String s : textLines)
            {
                int j = this.fontRendererObj.getStringWidth(s);

                if (j > i)
                {
                    i = j;
                }
            }

            int l1 = x + 12;
            int i2 = y - 12;
            int k = 8;

            if (textLines.size() > 1)
            {
                k += 2 + (textLines.size() - 1) * 10;
            }

            if (l1 + i > width)
            {
                l1 -= 28 + i;
            }

            if (i2 + k + 6 > height)
            {
                i2 = height - k - 6;
            }

            zLevel = 300.0F;
            this.itemRender.zLevel = 300.0F;
            int l = -267386864;
            drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
            drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
            drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
            drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
            drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
            int i1 = 1347420415;
            int j1 = 1344798847;
            drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
            drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
            drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
            drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);

            for (int k1 = 0; k1 < textLines.size(); ++k1)
            {
                String s1 = textLines.get(k1);
                this.fontRendererObj.drawStringWithShadow(s1, (float)l1, (float)i2, -1);

                if (k1 == 0)
                {
                    i2 += 2;
                }

                i2 += 10;
            }

            zLevel = 0.0F;
            this.itemRender.zLevel = 0.0F;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
        }
    }

    /**
     * Draws the hover event specified by the given chat component
     */
    protected void handleComponentHover(ITextComponent component, int x, int y)
    {
        if (component != null && component.getStyle().getHoverEvent() != null)
        {
            HoverEvent hoverevent = component.getStyle().getHoverEvent();

            if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM)
            {
                ItemStack itemstack = ItemStack.field_190927_a;

                try
                {
                    NBTBase nbtbase = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());

                    if (nbtbase instanceof NBTTagCompound)
                    {
                        itemstack = new ItemStack((NBTTagCompound)nbtbase);
                    }
                }
                catch (NBTException var9)
                {
                }

                if (itemstack.func_190926_b())
                {
                    this.drawCreativeTabHoveringText(TextFormatting.RED + "Invalid Item!", x, y);
                }
                else
                {
                    this.renderToolTip(itemstack, x, y);
                }
            }
            else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY)
            {
                if (this.mc.gameSettings.advancedItemTooltips)
                {
                    try
                    {
                        NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
                        List<String> list = Lists.newArrayList();
                        list.add(nbttagcompound.getString("name"));

                        if (nbttagcompound.hasKey("type", 8))
                        {
                            String s = nbttagcompound.getString("type");
                            list.add("Type: " + s);
                        }

                        list.add(nbttagcompound.getString("id"));
                        this.drawHoveringText(list, x, y);
                    }
                    catch (NBTException var8)
                    {
                        this.drawCreativeTabHoveringText(TextFormatting.RED + "Invalid Entity!", x, y);
                    }
                }
            }
            else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT)
            {
                this.drawHoveringText(this.mc.fontRendererObj.listFormattedStringToWidth(hoverevent.getValue().getFormattedText(), Math.max(width / 2, 200)), x, y);
            }

            GlStateManager.disableLighting();
        }
    }

    /**
     * Sets the text of the chat
     */
    protected void setText(String newChatText, boolean shouldOverwrite)
    {
    }

    /**
     * Executes the click event specified by the given chat component
     */
    public boolean handleComponentClick(ITextComponent component) throws Exception {
        if (component == null)
        {
            return false;
        }
        else
        {
            ClickEvent clickevent = component.getStyle().getClickEvent();

            if (isShiftKeyDown())
            {
                if (component.getStyle().getInsertion() != null)
                {
                    this.setText(component.getStyle().getInsertion(), false);
                }
            }
            else if (clickevent != null)
            {
                if (clickevent.getAction() == ClickEvent.Action.OPEN_URL)
                {
                    if (!this.mc.gameSettings.chatLinks)
                    {
                        return false;
                    }

                    try
                    {
                        URI uri = new URI(clickevent.getValue());
                        String s = uri.getScheme();

                        if (s == null)
                        {
                            throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
                        }

                        if (!PROTOCOLS.contains(s.toLowerCase(Locale.ROOT)))
                        {
                            throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase(Locale.ROOT));
                        }

                        if (this.mc.gameSettings.chatLinksPrompt)
                        {
                            this.clickedLinkURI = uri;
                            this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 31102009, false));
                        }
                        else
                        {
                            this.openWebLink(uri);
                        }
                    }
                    catch (URISyntaxException urisyntaxexception)
                    {
                        LOGGER.error("Can't open url for {}", clickevent, urisyntaxexception);
                    }
                }
                else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE)
                {
                    URI uri1 = (new File(clickevent.getValue())).toURI();
                    this.openWebLink(uri1);
                }
                else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND)
                {
                    this.setText(clickevent.getValue(), true);
                }
                else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
                {
                    this.sendChatMessage(clickevent.getValue(), false);
                }
                else
                {
                    LOGGER.error("Don't know how to handle {}", clickevent);
                }

                return true;
            }

            return false;
        }
    }

    /**
     * Used to add chat messages to the client's GuiChat.
     */
    public void sendChatMessage(String msg) throws Exception {
        this.sendChatMessage(msg, true);
    }

    public void sendChatMessage(String msg, boolean addToChat) throws Exception {
        if (addToChat)
        {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
        }

        this.mc.player.sendChatMessage(msg);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws Exception
    {
        if (mouseButton == 0)
        {
            for (int i = 0; i < this.buttonList.size(); ++i)
            {
                GuiButton guibutton = this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
                {
                    this.selectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                }
            }
        }
    }

    /**
     * Called when a mouse button is released.
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (this.selectedButton != null && state == 0)
        {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
    }

    /**
     * Causes the screen to lay out its subcomponents again. This is the equivalent of the Java call
     * Container.validate()
     */
    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRendererObj = mc.fontRendererObj;
        GuiScreen.width = width;
        GuiScreen.height = height;
        this.buttonList.clear();
        this.initGui();
    }

    /**
     * Set the gui to the specified width and height
     */
    public void setGuiSize(int w, int h)
    {
        width = w;
        height = h;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
    }

    /**
     * Delegates mouse and keyboard input.
     */
    public void handleInput() throws Exception
    {
        if (Mouse.isCreated())
        {
            while (Mouse.next())
            {
                this.handleMouseInput();
            }
        }

        if (Keyboard.isCreated())
        {
            while (Keyboard.next())
            {
                this.handleKeyboardInput();
            }
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws Exception
    {
        int i = Mouse.getEventX() * width / this.mc.displayWidth;
        int j = height - Mouse.getEventY() * height / this.mc.displayHeight - 1;
        int k = Mouse.getEventButton();

        if (Mouse.getEventButtonState())
        {
            if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0)
            {
                return;
            }

            this.eventButton = k;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(i, j, this.eventButton);
        }
        else if (k != -1)
        {
            if (this.mc.gameSettings.touchscreen && --this.touchValue > 0)
            {
                return;
            }

            this.eventButton = -1;
            this.mouseReleased(i, j, k);
        }
        else if (this.eventButton != -1 && this.lastMouseEvent > 0L)
        {
            long l = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(i, j, this.eventButton, l);
        }
    }

    /**
     * Handles keyboard input.
     */
    public void handleKeyboardInput() throws Exception
    {
        char c0 = Keyboard.getEventCharacter();

        if (Keyboard.getEventKey() == 0 && c0 >= ' ' || Keyboard.getEventKeyState())
        {
            this.keyTyped(c0, Keyboard.getEventKey());
        }

        this.mc.dispatchKeypresses();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
    }

    /**
     * Draws either a gradient over the background screen (when it exists) or a flat gradient over background.png
     */
    public void drawDefaultBackground()
    {
        this.drawWorldBackground();
    }

    public void drawWorldBackground()
    {
        if (this.mc.world != null)
        {
            GL11.glEnable(GL11.GL_BLEND);
            drawGradientRect(0, 0, width, height, -1072689136, -804253680);
        }
        else
        {
            this.drawBackground();
        }
    }

    public void drawBackground() {
        int red = HClient.getClientColorBackground().getRed();
        int green = HClient.getClientColorBackground().getGreen();
        int blue = HClient.getClientColorBackground().getBlue();
        int alpha = HClient.getClientColorBackground().getAlpha();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableTexture2D();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, height, 0.0D).tex(0.0D, (float) height / 32.0F + 0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(width, height, 0.0D).tex((float) width / 32.0F, (float) height / 32.0F + 0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(width, 0.0D, 0.0D).tex((float) width / 32.0F, 0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return true;
    }

    public void confirmClicked(boolean result, int id)
    {
        if (id == 31102009)
        {
            if (result)
            {
                this.openWebLink(this.clickedLinkURI);
            }

            this.clickedLinkURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    private void openWebLink(URI url)
    {
        try
        {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, url);
        }
        catch (Throwable throwable1)
        {
            Throwable throwable = throwable1.getCause();
            LOGGER.error("Couldn't open link: {}", throwable == null ? "<UNKNOWN>" : throwable.getMessage());
        }
    }

    /**
     * Returns true if either windows ctrl key is down or if either mac meta key is down
     */
    public static boolean isCtrlKeyDown()
    {
        if (Minecraft.IS_RUNNING_ON_MAC)
        {
            return Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220);
        }
        else
        {
            return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        }
    }

    /**
     * Returns true if either shift key is down
     */
    public static boolean isShiftKeyDown()
    {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }

    /**
     * Returns true if either alt key is down
     */
    public static boolean isAltKeyDown()
    {
        return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
    }

    public static boolean isKeyComboCtrlX(int keyID)
    {
        return keyID == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlV(int keyID)
    {
        return keyID == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlC(int keyID)
    {
        return keyID == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlA(int keyID)
    {
        return keyID == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Called when the GUI is resized in order to update the world and the resolution
     */
    public void onResize(Minecraft mcIn, int w, int h)
    {
        this.setWorldAndResolution(mcIn, w, h);
    }
}
