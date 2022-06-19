package neko.itskekoffcode.hclient.commands.impl.bot.onebot.inventory;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.clientapi.bot.entity.BotPlayer;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.inventory.ClickType;

public class BotsBotSlotClickCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("botslotclick")) {
                if (BotStarter.getBotByName(args[1]) != null) {
                    BotPlayer bot = BotStarter.getBotByName(args[1]);
                    Bot rbot = BotStarter.getRawBotByName(args[1]);
                    int slot = 0;
                    try {
                        slot = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        send("Slot must be integer!", ChatFormatting.RED);
                    }
                    assert bot != null;
                    assert rbot != null;
                    //bot.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    rbot.getController().windowClick(rbot.getBot().openContainer.windowId, slot, 0, ClickType.PICKUP, rbot.getBot());
                    send(String.format("Bot (%s) clicked slot: %d", bot.getDisplayName().getUnformattedComponentText(), slot), ChatFormatting.GREEN);
                } else {
                    send(String.format("Bot (%s) offline/doesn't exists!", args[1]), ChatFormatting.RED);
                }
            }
        }
    }


    @Override
    public String getName() {
        return "botslotclick";
    }

    @Override
    public String getDescription() {
        return "makes bot click slot in opened window";
    }

    @Override
    public String getUsage() {
        return "bots botslotclick <bot> <slot>";
    }

    public void send(String msg, ChatFormatting format) {
        if (format != null) { ChatHelper.addChatMessage(format + msg); }
        if (format == null) { ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + msg); }
    }
}
