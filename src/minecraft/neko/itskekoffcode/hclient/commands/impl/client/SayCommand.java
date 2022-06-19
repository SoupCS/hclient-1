package neko.itskekoffcode.hclient.commands.impl.client;

import neko.itskekoffcode.hclient.commands.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.Objects;

public class SayCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        StringBuilder msg = new StringBuilder();
        for (String arg : args) {
            msg.append(arg).append(" ");
        }
        Objects.requireNonNull(Minecraft.getMinecraft().getConnection()).sendPacket(new CPacketChatMessage(msg.toString()));
    }


    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getDescription() {
        return "makes player send chat message";
    }

    @Override
    public String getUsage() {
        return "say <msg>";
    }
}
