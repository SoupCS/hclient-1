package neko.itskekoffcode.hclient.commands.impl;

import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.hclient.commands.CommandManager;

import java.util.Objects;

public class HelpCommand extends Command {
    private boolean showbots = false;

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            showbots = false;
            HelpCommand.msg(String.format("&d%shelp bots:  &7%shelp bots &8(&dPrints all bots commands&8)&r", CommandManager.syntax, CommandManager.syntax), true);
            HelpCommand.msg(String.format("&d%shelp client:  &7%shelp client &8(&dPrints all client commands&8)&r", CommandManager.syntax, CommandManager.syntax), true);
            HelpCommand.msg(String.format("&d%shelp server:  &7%shelp server &8(&dPrints all server commands&8)&r", CommandManager.syntax, CommandManager.syntax), true);
            HelpCommand.msg(String.format("&d%shelp crash:  &7%shelp crash &8(&dPrints all crash commands&8)&r", CommandManager.syntax, CommandManager.syntax), true);
        }
        if (args.length == 1) {
            if (args[0].equals("bots")) {
                showbots = true;
                for (Command cmd : new CommandManager().getBotCommands()) {
                    HelpCommand.msg(String.format("&d%s%s &8(&7%s&8)&r", CommandManager.syntax, cmd.getUsage(), cmd.getDescription()), true);
                }
            }
            if (args[0].equals("client")) {
                for (Command cmd : new CommandManager().getClientCommands()) {
                    HelpCommand.msg(String.format("&d%s%s &8(&7%s&8)&r", CommandManager.syntax, cmd.getUsage(), cmd.getDescription()), true);
                }
            }
            if (args[0].equals("server")) {
                for (Command cmd : new CommandManager().getServerCommands()) {
                    HelpCommand.msg(String.format("&d%s%s &8(&7%s&8)&r", CommandManager.syntax, cmd.getUsage(), cmd.getDescription()), true);
                }
            }
            if (args[0].equals("crash")) {
                for (Command cmd : new CommandManager().getCrashCommands()) {
                    HelpCommand.msg(String.format("&d%s%s &8(&7%s&8)&r", CommandManager.syntax, cmd.getUsage(), cmd.getDescription()), true);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Prints this list";
    }

    @Override
    public String getUsage() {
        return "help";
    }
}
