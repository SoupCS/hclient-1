package neko.itskekoffcode.hclient.commands.impl.bot.inventory;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class BotsHeldSlotCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("heldslot")) {
                int slot = 0;
                try {
                    slot = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    send("Slot must be integer!", ChatFormatting.RED);
                }
                for (Bot bot : Bot.bots) {
                    bot.getBot().connection.sendPacket(new CPacketHeldItemChange(slot));
                    bot.getBot().connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                }
                send(String.format("All bots changed his held item slot to: %d", slot), ChatFormatting.GREEN);
            }
        }
    }


    @Override
    public String getName() {
        return "heldslot";
    }

    @Override
    public String getDescription() {
        return "makes all bots change his item slot";
    }

    @Override
    public String getUsage() {
        return "bots heldslot <slot>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
