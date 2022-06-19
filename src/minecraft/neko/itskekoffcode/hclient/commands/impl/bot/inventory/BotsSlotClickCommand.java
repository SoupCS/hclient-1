package neko.itskekoffcode.hclient.commands.impl.bot.inventory;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.inventory.ClickType;

public class BotsSlotClickCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("slotclick")) {
                if (Bot.bots.size() != 0) {
                    int slot = 0;
                    try {
                        slot = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        send("Slot must be integer!", ChatFormatting.RED);
                    }
                    for (Bot bot : Bot.bots) {
                        //bot.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                        bot.getController().windowClick(bot.getBot().openContainer.windowId, slot, 0, ClickType.PICKUP, bot.getBot());
                    }
                } else {
                    send("Bots doesn't connected!", ChatFormatting.RED);
                }
            }
        }
    }


    @Override
    public String getName() {
        return "slotclick";
    }

    @Override
    public String getDescription() {
        return "makes all bots click slot in opened window";
    }

    @Override
    public String getUsage() {
        return "bots slotclick <slot>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
