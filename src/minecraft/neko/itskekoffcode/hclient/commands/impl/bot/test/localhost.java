package neko.itskekoffcode.hclient.commands.impl.bot.test;

import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.hclient.commands.impl.bot.connection.BotsStopConnectCommand;
import neko.itskekoffcode.clientapi.utils.ThreadUtils;

public class localhost extends Command {

    @Override
    public void execute(String[] args) throws Exception {
        if (args[0].equals("localhost")) {
            BotsStopConnectCommand.connecting = true;
            int connecting = Integer.parseInt(args[1]);
            localhost.msg("connecting", true);
            (new Thread(() -> {
                for (int i = 0; i < connecting; ++i) {
                    new Thread(BotStarter::test).start();
                    ThreadUtils.sleep(5L);
                }
            })).start();
        }
    }


    @Override
    public String getName() {
        return "localhost";
    }

    @Override
    public String getDescription() {
        return "send bots to localhost server";
    }

    @Override
    public String getUsage() {
        return "bots localhost <bots>";
    }
}
