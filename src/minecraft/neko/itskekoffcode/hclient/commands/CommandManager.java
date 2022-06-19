package neko.itskekoffcode.hclient.commands;

import neko.itskekoffcode.hclient.commands.impl.bot.onebot.chat.*;
import neko.itskekoffcode.hclient.commands.impl.bot.onebot.connection.*;
import neko.itskekoffcode.hclient.commands.impl.bot.onebot.world.*;
import neko.itskekoffcode.hclient.commands.impl.bot.onebot.inventory.*;

import neko.itskekoffcode.hclient.commands.impl.bot.chat.*;
import neko.itskekoffcode.hclient.commands.impl.bot.connection.*;
import neko.itskekoffcode.hclient.commands.impl.bot.fun.*;
import neko.itskekoffcode.hclient.commands.impl.bot.inventory.*;
import neko.itskekoffcode.hclient.commands.impl.bot.test.localhost;
import neko.itskekoffcode.hclient.commands.impl.bot.world.*;

import neko.itskekoffcode.hclient.commands.impl.client.*;
import neko.itskekoffcode.hclient.commands.impl.server.*;
import neko.itskekoffcode.hclient.commands.impl.crash.*;

import neko.itskekoffcode.hclient.commands.impl.*;
import neko.itskekoffcode.clientapi.utils.ChatHelper;

// other
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private static final List<Command> commands = new ArrayList<>();
    private static final List<Command> botcommands = new ArrayList<>();
    private static final List<Command> servercommands = new ArrayList<>();
    private static final List<Command> clientcommands = new ArrayList<>();
    private static final List<Command> crashcommands = new ArrayList<>();

    public static String syntax = ".";
    public static String botsyntax = syntax + "bots";


    public static void commands() {
        addCommand(new BindChatCommand(), true, null);
        addCommand(new HelpCommand(), null, null);
        addCommand(new DumpCommand(), false, null);
        addCommand(new GetApiCommand(), false, null);
        addCommand(new SayCommand(), true, null);
        addCommand(new PacketCommand(), null, true);
        addCommand(new VClipCommand(), true, null);
        addCommand(new PingCommand(), null, true);
    }
    public static void botcommands() {
        addBotCommand(new BotsConnectCommand());
        addBotCommand(new localhost());
        addBotCommand(new BotsLookCommand());
        addBotCommand(new BotsDisconnectCommand());
        addBotCommand(new BotsFollowCommand());
        addBotCommand(new BotsHeldSlotCommand());
        addBotCommand(new BotsRandomRotationCommand());
        addBotCommand(new BotsSlotClickCommand());
        addBotCommand(new BotsStopConnectCommand());
        addBotCommand(new BotsBotChatCommand());
        addBotCommand(new BotsChatCommand());
        addBotCommand(new BotsChatToggleCommand());
        addBotCommand(new BotsBotDisconnectCommand());
        addBotCommand(new BotsBotHeldSlotCommand());
        addBotCommand(new BotsBotSlotClickCommand());
        addBotCommand(new BotsBotLookCommand());
        addBotCommand(new BotsLClickCommand());
        addBotCommand(new BotsRClickCommand());
    }

    public List<Command> getCommands() {
        return commands;
    }

    public List<Command> getBotCommands() {
        return botcommands;
    }
    public List<Command> getServerCommands() {
        return servercommands;
    }
    public List<Command> getCrashCommands() {
        return crashcommands;
    }
    public List<Command> getClientCommands() {
        return clientcommands;
    }

    public static boolean execute(String text) throws Exception {
        if (!text.startsWith(syntax)) {
            return false;
        }
        text = text.substring(syntax.length());
        String[] arguments = text.split(" ");
        for (Command cmd : commands) {
            if (!cmd.getName().equalsIgnoreCase(arguments[0])) continue;
            String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
            ChatHelper.addChatMessage("");
            ChatHelper.addChatMessage(Command.fix(String.format("&d%s&7%s", "> ", text)));
            ChatHelper.addChatMessage("");
            cmd.execute(args);
            return true;
        }
        return false;
    }

    public static boolean executeBot(String text) throws Exception {
        if (text.contains(botsyntax)) {
            text = text.substring(1);
            String[] arguments = text.split(" ");
            if (text.equalsIgnoreCase("bots")) {
                return false;
            }
            for (Command cmd : botcommands) {
                if (!cmd.getName().equalsIgnoreCase(arguments[1])) continue;
                String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
                ChatHelper.addChatMessage("");
                ChatHelper.addChatMessage(Command.fix(String.format("&d%s&7%s", "> ", text)));
                ChatHelper.addChatMessage("");
                cmd.execute(args);
                return true;
            }
        }
        return false;
    }

    public static void addCommand(Command command, Boolean client, Boolean crash) {
        commands.add(command);
        if (client == null & crash == null) {
            return;
        }
        if (Boolean.TRUE.equals(client) && crash == null) {
            clientcommands.add(command);
            return;
        }
        if (Boolean.FALSE.equals(client) && crash == null) {
            servercommands.add(command);
            return;
        }
        if (Boolean.TRUE.equals(crash)) {
            crashcommands.add(command);
        }
    }
    public static void addBotCommand(Command command) {
        botcommands.add(command);
    }
    public static void addServerCommand(Command command) {
        servercommands.add(command);
    }
    public static void addClientCommand(Command command) {
        clientcommands.add(command);
    }
    public static void addCrashCommand(Command command) {
        crashcommands.add(command);
    }
}