package neko.itskekoffcode.hclient.commands.impl.bot.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.network.play.client.CPacketPlayer;

public class BotsLookCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("look")) {
                if (Bot.bots.size() != 0) {
                    float pitch = 0;
                    float yaw = 0;
                    try {
                        yaw = Float.parseFloat(args[1]);
                    } catch (Exception ignored) {
                    }
                    try {
                        pitch = Float.parseFloat(args[2]);
                    } catch (Exception ignored) {
                    }
                    for (Bot bot : Bot.bots) {
                        bot.getBot().rotationPitch = pitch;
                        bot.getBot().rotationYaw = yaw;
                        bot.getBot().connection.sendPacket(new CPacketPlayer.Rotation(yaw, pitch, bot.getBot().onGround));
                    }
                    send(String.format("All bots set his look position to: %f,%f", pitch, yaw), ChatFormatting.GREEN);
                } else {
                    send("Bots doesn't connected!", ChatFormatting.RED);
                }

            }
        }
    }

    @Override
    public String getName() {
        return "look";
    }

    @Override
    public String getDescription() {
        return "makes all bots change rotation position";
    }

    @Override
    public String getUsage() {
        return "bots look <yaw> <pitch>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
