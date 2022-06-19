package neko.itskekoffcode.hclient.helpers;

import net.minecraft.client.*;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.Packet;


import java.util.Random;

import neko.itskekoffcode.clientapi.utils.TimerHelper;

public interface Helper {
    Minecraft mc = Minecraft.getMinecraft();
    Gui gui = new Gui();
    Random random = new Random();
    TimerHelper timerHelper = new TimerHelper();

    default void sendPacket(Packet<?> packet) {
        mc.player.connection.sendPacket(packet);
    }

}
