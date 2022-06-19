package neko.itskekoffcode.hclient.commands.impl.bot.connection;

import neko.itskekoffcode.hclient.HClient;
import neko.itskekoffcode.hclient.bots.proxy.ProxyParser;
import neko.itskekoffcode.hclient.commands.Command;

import java.io.File;

public class ProxyCommand extends Command {
    public String type = "4";
    public boolean mix = false;

    @Override
    public void execute(String[] args) throws Exception {
        if (args[1].equalsIgnoreCase("reset")) {
            ProxyParser.proxies.clear();
            ProxyCommand.msg("&aReseted!", true);
        }
        if (args[1].equalsIgnoreCase("reparse")) {
            ProxyParser.proxies.clear();
            new ProxyParser().init();
        }
        if (args[1].equalsIgnoreCase("fromfile")) {
            ProxyParser.proxies.clear();
            File proxydir = new File(HClient.getInstance().getFileManager().getClientDirectory() + "/bots/proxy");
            if (!proxydir.exists()) {
                HClient.getInstance().getFileManager().addSubDirectory("/bots/proxy");
                ProxyCommand.msg("&cCreated proxy dir, insert proxies in proxies.txt to process parsing.", true);
                return;
            }
            ProxyParser.loadfromfile(HClient.getInstance().getFileManager().getClientDirectory() + "/bots/proxy/proxies.txt", type);
        }
        if (args[1].equalsIgnoreCase("type")) {
            try {
                if (args[2].equalsIgnoreCase("mix")) {
                    mix = true;
                }
                int type1 = Integer.parseInt(args[2]);
                if (type1 != 4 && type1 != 5) {
                    ProxyCommand.msg("&cError: &eEnter valid integer! 4/5", true);
                    return;
                }
                type = args[2];
            } catch (Exception e) {
                ProxyCommand.msg("&cError: &eEnter valid integer! 4/5", true);
            }
        }
    }

    @Override
    public String getName() {
        return "proxy";
    }

    @Override
    public String getDescription() {
        return "settings proxy";
    }

    @Override
    public String getUsage() {
        return "bots proxy reset/reparse/fromfile/type <4/5>";
    }
}
