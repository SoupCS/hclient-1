package neko.itskekoffcode.hclient.commands.impl.bot.world;

import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.hclient.commands.Command;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;

public class BotsLClickCommand extends Command {

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 1) {
            if (args[0].equals("lclick")) {
                for (Bot bot : Bot.bots) {
                    bot.getNetManager().sendPacket(new CPacketAnimation());
                    bot.getNetManager().sendPacket(new CPacketUseEntity());
                }
                BotsLClickCommand.msg("clicked", true);
            }
        }
    }

    @Override
    public String getName() {
        return "lclick";
    }

    @Override
    public String getDescription() {
        return "all bots sends left click packet";
    }

    @Override
    public String getUsage() {
        return "bots lclick";
    }
}
