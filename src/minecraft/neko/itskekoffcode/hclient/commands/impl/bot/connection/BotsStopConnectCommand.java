package neko.itskekoffcode.hclient.commands.impl.bot.connection;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

public class BotsStopConnectCommand extends Command {
    public static boolean connecting = false;

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("stopconnect")) {
                if (!connecting) {
                    send("Bots already stopped connecting!", ChatFormatting.RED);
                } else {
                    connecting = false;
                    send("Bots stopped connecting!", null);
                }
            }
        }
    }


    @Override
    public String getName() {
        return "stopconnect";
    }

    @Override
    public String getDescription() {
        return "disable bots connecting";
    }

    @Override
    public String getUsage() {
        return "bots stopconnect";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
