package neko.itskekoffcode.hclient.bots.nicks;

import neko.itskekoffcode.clientapi.bot.BotStarter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NicksParser {
    private static final List<String> nicks = new CopyOnWriteArrayList<>();
    private static int number = -1;

    public void init(String filepath) {
        if (nicks.size() > 0) {
            return;
        }
    	BotStarter.message("(&6NICK-PARSER&7) &eLoading nicknames...");

        File nicksFile = new File(filepath);

        if (nicksFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(nicksFile))) {
                while (reader.ready()) {
                    try {
                        String line = reader.readLine();
                        nicks.add(line);
                    } catch (Exception ignored) {}
                }
            } catch (Exception ignored) {}
        } else {
            BotStarter.message("(&6NICK-PARSER&7) &eNicknames folder doesn't exists!");
            return;
        }

        BotStarter.message("(&6NICK-PARSER&7) &eLoaded &7(&6%s&7) &enicknames.\n".replace("%s", String.valueOf(nicks.size())));
    }

    public static String nextNick() {
        ++number;

        if (number >= nicks.size())
            number = 0;

        return nicks.get(number);
    }
}
