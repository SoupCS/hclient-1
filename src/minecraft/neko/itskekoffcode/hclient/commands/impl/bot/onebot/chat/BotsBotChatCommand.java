package neko.itskekoffcode.hclient.commands.impl.bot.onebot.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

public class BotsBotChatCommand extends Command {

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("botchat")) {
                StringBuilder msg = new StringBuilder();
                for (int a = 2; a < args.length; ++a) {
                    msg.append(args[a]).append(" ");
                }
                if (BotStarter.getBotByName(args[1]) != null) {
                    BotStarter.getBotByName(args[1]).sendChatMessage(msg.toString());
                } else {
                    send(String.format("Bot (%s) offline/doesn't exists!", args[1]), ChatFormatting.RED);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "botchat";
    }

    @Override
    public String getDescription() {
        return "Sending msg from bot";
    }

    @Override
    public String getUsage() {
        return "bots botchat <bot> <msg>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
