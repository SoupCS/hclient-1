package neko.itskekoffcode.hclient.commands.impl.bot.connection;


import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.BotThread;
import neko.itskekoffcode.hclient.bots.nicks.NicksParser;
import neko.itskekoffcode.hclient.bots.proxy.ProxyParser;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import neko.itskekoffcode.clientapi.utils.ThreadUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BotsConnectCommand extends Command {

	public static BotThread thread;

	public static final ExecutorService executorService = Executors.newFixedThreadPool(1000);
	public static void join(String maxbot, String timeout, String filename){
		if (Integer.parseInt(maxbot) >= 1 && Integer.parseInt(timeout) >= 0) {
			for (int i = 0; i < Integer.parseInt(maxbot); i++) {
				executorService.submit(new BotThread(filename));
				ThreadUtils.sleep(Long.parseLong(timeout));
			}
		}

	}

	@Override
	public void execute(String[] args) {
		if (args.length >= 2) {
			if (args[0].equalsIgnoreCase("connect")) {
				BotsStopConnectCommand.connecting = true;
				// args[2] == number of the bots
				if (args[1] == null) {
					send("Error: include to your command argument: number to be connected | see: .bots help", ChatFormatting.RED);
					return;
				}
				if (args[2] == null && args[1] == null) {
					send("Error: include to your command argument: number to be connected, delay in ms | see: .bots help", ChatFormatting.RED);
					return;
				}
				if (args[2] == null) {
					send("Error: include to your command argument: delay in ms | see: .bots help", ChatFormatting.RED);
					return;
				}
				new Thread(() -> {
					new ProxyParser().init();
					new NicksParser().init("HClient/bots/nicks.txt");
					join(args[1], args[2], "HClient/bots/nicks.txt");
				}).start();
			}
		}
	}


	@Override
	public String getName() {
		return "connect";
	}

	@Override
	public String getDescription() {
		return "Connect bots to server";
	}

	@Override
	public String getUsage() {
		return "bots connect <bots> <delay(ms)>";
	}

	public void send(String msg, ChatFormatting format) {
		if (format != null) { ChatHelper.addChatMessage(format + msg); }
		if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
	}
}
