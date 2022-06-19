package neko.itskekoffcode.hclient.commands.impl.bot.onebot.connection;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

public class BotsBotDisconnectCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 2) {
            if (args[0].equals("botdisconnect")) {
                if (BotStarter.getBotByName(args[1]) != null) {
                    Bot bot = BotStarter.getRawBotByName(args[1]);
                    assert bot != null;
                    bot.getNetManager().closeChannel();
                    Bot.bots.remove(bot);
                    send("&7(&e" + bot.getBot().getDisplayName().getFormattedText() + "&7)&f disconnected!", null);
                } else {
                    send(String.format("Bot (%s) offline/doesn't exists!", args[1]), ChatFormatting.RED);
                }
            }
        }
    }


    @Override
    public String getName() {
        return "botdisconnect";
    }

    @Override
    public String getDescription() {
        return "disconnecting bot";
    }

    @Override
    public String getUsage() {
        return "bots botdisconnect <bot>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
