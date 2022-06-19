package neko.itskekoffcode.hclient.commands.impl.bot.onebot.inventory;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.clientapi.bot.entity.BotPlayer;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class BotsBotHeldSlotCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("botheldslot")) {
                if (BotStarter.getBotByName(args[1]) != null) {
                    BotPlayer bot = BotStarter.getBotByName(args[1]);
                    int slot = 0;
                    try {
                        slot = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        send("Slot must be integer!", ChatFormatting.RED);
                    }
                    assert bot != null;
                    bot.connection.sendPacket(new CPacketHeldItemChange(slot));
                    bot.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    send(String.format("Bot (%s) changed his held item slot and used it! Slot id: %d", bot.getDisplayName().getUnformattedComponentText(), slot), ChatFormatting.GREEN);
                } else {
                    send(String.format("Bot (%s) offline/doesn't exists!", args[1]), ChatFormatting.RED);
                }
            }
        }
    }


    @Override
    public String getName() {
        return "botheldslot";
    }

    @Override
    public String getDescription() {
        return "changes bot slot item";
    }

    @Override
    public String getUsage() {
        return "bots botheldslot <bot> <slot>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
