package net.minecraft.network;

import net.minecraft.util.IThreadListener;

import java.io.IOException;

public class PacketThreadUtil
{
    public static <T extends INetHandler> void checkThreadAndEnqueue(final Packet<T> packetIn, final T processor, IThreadListener scheduler) throws ThreadQuickExitException
    {
        if (!scheduler.isCallingFromMinecraftThread())
        {
            scheduler.addScheduledTask(new Runnable()
            {
                public void run()
                {
                    try {
                        packetIn.processPacket(processor);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            throw ThreadQuickExitException.INSTANCE;
        }
    }
}
