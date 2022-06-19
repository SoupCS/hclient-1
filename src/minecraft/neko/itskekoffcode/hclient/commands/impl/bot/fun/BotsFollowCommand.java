package neko.itskekoffcode.hclient.commands.impl.bot.fun;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

public class BotsFollowCommand extends Command {

    public static boolean Follow = false;

    @Override
    public void execute(String[] args) throws Exception {
        if (args[0].equalsIgnoreCase("follow")) {
            if (Follow) {
                Follow = false;
                send("Player follow disabled!", ChatFormatting.RED);
                return;
            }
            if (!Follow) {
                Follow = true;
                send("Player follow enabled!", ChatFormatting.GREEN);
            }
        }
    }


    @Override
    public String getName() {
        return "follow";
    }

    @Override
    public String getDescription() {
        return "makes all bots follow your position";
    }

    @Override
    public String getUsage() {
        return "bots follow";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
