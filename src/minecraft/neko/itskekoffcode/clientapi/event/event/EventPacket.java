package neko.itskekoffcode.clientapi.event.event;

import net.minecraft.network.Packet;
import neko.itskekoffcode.clientapi.event.Event;
public class EventPacket extends Event {
    private Packet<?> packet;
    private boolean outgoing;

    public EventPacket(Packet<?> packet, boolean outgoing) {
        this.packet = packet;
        this.outgoing = outgoing;
        this.pre = true;
    }

    public EventPacket(Packet<?> packet) {
        this.packet = packet;
        this.pre = false;
    }

    public boolean isPre() {
        return pre;
    }

    public boolean isPost() {
        return !pre;
    }

    private final boolean pre;

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public boolean isOutgoing() {
        return outgoing;
    }

    public boolean isIncoming() {
        return !outgoing;
    }
}