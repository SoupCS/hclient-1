package neko.itskekoffcode.hclient.commands.impl.client;

import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.hclient.commands.CommandManager;

public class BindChatCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args[0].equalsIgnoreCase("chat")) {
            String newkey = args[1];
            CommandManager.syntax = newkey;
            CommandManager.botsyntax = newkey + "bots";
            BindChatCommand.msg("New keybind: &7" + newkey + "&r", true);
        }
    }

    @Override
    public String getName() {
        return "bind";
    }

    @Override
    public String getDescription() {
        return "binds all bots/default commands trigger key.";
    }

    @Override
    public String getUsage() {
        return "bind chat <binding>";
    }
}
