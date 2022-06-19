package neko.itskekoffcode.hclient.commands.impl.bot.connection;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

public class BotsDisconnectCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("disconnect")) {
                if (Bot.bots.size() != 0) {
                    for (Bot bot : Bot.bots) {
                        bot.getNetManager().closeChannel();
                        Bot.bots.remove(bot);
                        BotsDisconnectCommand.msg("&7(&e" + bot.getBot().getDisplayName().getFormattedText() + "&7)&f disconnected!", true);
                    }
                } else {
                    BotsDisconnectCommand.msg("Bots doesn't connected!", true);
                }
            }
        }
    }


    @Override
    public String getName() {
        return "disconnect";
    }

    @Override
    public String getDescription() {
        return "makes all bots disconnect";
    }

    @Override
    public String getUsage() {
        return "bots disconnect";
    }

}
