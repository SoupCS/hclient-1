package neko.itskekoffcode.hclient.commands.impl.bot.world;

import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.hclient.commands.Command;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketUseEntity;

public class BotsRClickCommand extends Command {

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 1) {
            if (args[0].equals("rclick")) {
                for (Bot bot : Bot.bots) {
                    bot.getNetManager().sendPacket(new CPacketUseEntity());
                    bot.getNetManager().sendPacket(new CPacketPlayerTryUseItem());
                }
                BotsLClickCommand.msg("clicked", true);
            }
        }
    }

    @Override
    public String getName() {
        return "rclick";
    }

    @Override
    public String getDescription() {
        return "all bots sending rclick packet";
    }

    @Override
    public String getUsage() {
        return "bots rclick";
    }
}
