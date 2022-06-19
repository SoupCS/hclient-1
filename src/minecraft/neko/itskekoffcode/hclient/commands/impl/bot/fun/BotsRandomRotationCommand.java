package neko.itskekoffcode.hclient.commands.impl.bot.fun;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

public class BotsRandomRotationCommand extends Command {

    public static boolean Carusel = false;

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("randomrotation")) {
                if (Carusel) {
                    Carusel = false;
                    send("Random rotation disabled!", ChatFormatting.RED);
                    return;
                }
                if (!Carusel) {
                    Carusel = true;
                    send("Random rotation enabled!", ChatFormatting.GREEN);
                    return;
                }
                return;
            }
        }
    }


    @Override
    public String getName() {
        return "randomrotation";
    }

    @Override
    public String getDescription() {
        return "makes all bots randomly change his rotation";
    }

    @Override
    public String getUsage() {
        return "bots randomrotation";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
