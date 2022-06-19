package neko.itskekoffcode.hclient.commands.impl.bot.onebot.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.clientapi.bot.entity.BotPlayer;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.network.play.client.CPacketPlayer;

public class BotsBotLookCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("botlook")) {
                float yaw;
                float pitch;
                yaw = Float.parseFloat(args[2]);
                pitch = Float.parseFloat(args[3]);
                if (BotStarter.getBotByName(args[1]) != null) {
                    BotPlayer bot = BotStarter.getBotByName(args[1]);
                    assert bot != null;
                    bot.rotationPitch = pitch;
                    bot.rotationYaw = yaw;
                    bot.connection.sendPacket(new CPacketPlayer.Rotation(yaw, pitch, bot.onGround));
                } else {
                    send(String.format("Bot (%s) offline/doesn't exists!", args[1]), ChatFormatting.RED);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "botlook";
    }

    @Override
    public String getDescription() {
        return "makes bot look to selected position";
    }

    @Override
    public String getUsage() {
        return "bots botlook <bot> <yaw> <pitch>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
