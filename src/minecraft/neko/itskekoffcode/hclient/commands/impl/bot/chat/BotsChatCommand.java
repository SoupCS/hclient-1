package neko.itskekoffcode.hclient.commands.impl.bot.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

public class BotsChatCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("chat")) {
                if (Bot.bots.size() != 0) {
                    for (Bot bot : Bot.bots) {
                        StringBuilder msg = new StringBuilder();
                        for (int a = 1; a < args.length; ++a) {
                            msg.append(args[a]).append(" ");
                        }
                        bot.getBot().sendChatMessage(msg.toString());
                    }
                } else {
                    send("Bots doesn't connected!", ChatFormatting.RED);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public String getDescription() {
        return "makes all bots send your msg";
    }

    @Override
    public String getUsage() {
        return "bots chat <msg>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
