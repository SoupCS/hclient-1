package neko.itskekoffcode.clientapi.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.hclient.helpers.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class ChatHelper implements Helper {

    public static String chatPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "H" + ChatFormatting.WHITE + "Client"  + ChatFormatting.GRAY + "] " + ChatFormatting.RESET;

    public static void addChatMessage(String message) {
        Minecraft.getMinecraft().player.addChatMessage(new TextComponentString(chatPrefix + ChatFormatting.stripFormatting(message)));
    }
}
