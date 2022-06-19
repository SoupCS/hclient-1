package neko.itskekoffcode.hclient.commands.impl.bot.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

public class BotsChatToggleCommand extends Command {

    public static boolean BotChat = true;

    @Override
    public void execute(String[] args) throws Exception {
        System.out.println("called execute");
        if (BotChat) {
            BotChat = false;
            send("Chat disabled!", ChatFormatting.RED);
            return;
        }
        if (!BotChat) {
            BotChat = true;
            send("Chat enabled!", ChatFormatting.GREEN);
        }
    }


    @Override
    public String getName() {
        return "chattoggle";
    }

    @Override
    public String getDescription() {
        return "on/off bots chat";
    }

    @Override
    public String getUsage() {
        return "bots chattoggle";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
