package neko.itskekoffcode.hclient.commands;

import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;


public abstract class Command {
    private static final Minecraft mc = Minecraft.getMinecraft();


    /**
     *
     * @param args arguments for command (e.g - .bots chat asd (showing: chat asd)
     * @throws Exception idk
     */
    public abstract void execute(String[] args) throws Exception;

    /**
     * Get command name.
     * @return command name
     */
    public abstract String getName();

    /**
     * Get command description
     * @return command description
     */
    public abstract String getDescription();

    /**
     * Get command usage
     * @return command usage
     */
    public abstract String getUsage();

    public static String fix(String string) {
        return string.replace('&', '\u00a7').replace(">>", "»");
    }

    /**
     * Sends chat message to the player
     * @param message Text to be sent
     * @param prefix Use default chat prefix or not
     */
    public static void msg(String message, boolean prefix) {
        mc.player.addChatMessage(new TextComponentString(fix("&d" + ChatHelper.chatPrefix + "&8>> &7" + message)));
    }
}
