package neko.itskekoffcode.clientapi.bot.network;

import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.clientapi.bot.entity.BotPlayer;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.server.SPacketDisconnect;
import net.minecraft.network.login.server.SPacketEnableCompression;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.network.login.server.SPacketLoginSuccess;
import net.minecraft.util.text.ITextComponent;

public final class BotLoginClient implements INetHandlerLoginClient {
    private final BotNetwork networkManager;

    private BotPlayer bot;

    public BotLoginClient(BotNetwork networkManager){
        this.networkManager = networkManager;
    }
    public void handleEncryptionRequest(SPacketEncryptionRequest packetIn) {

    }

    public void handleLoginSuccess(SPacketLoginSuccess packetIn) {

        this.networkManager.setConnectionState(EnumConnectionState.PLAY);
        this.networkManager.setNetHandler(new BotPlayClient(this.networkManager, packetIn.getProfile()));
    }

    public void onDisconnect(ITextComponent reason) {
    }

    public void handleDisconnect(SPacketDisconnect packetIn) {
        BotStarter.message("&7(&e"+ bot.getDisplayName().getFormattedText() + "&7)&f Disconnect: "+ packetIn.getReason().getFormattedText());
        Bot.bots.remove(bot);
    }

    public void handleEnableCompression(SPacketEnableCompression packetIn) {

        if (!this.networkManager.isLocalChannel())
        {
            this.networkManager.setCompressionThreshold(packetIn.getCompressionThreshold());
        }
    }
}
