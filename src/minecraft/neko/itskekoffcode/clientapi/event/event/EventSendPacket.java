package neko.itskekoffcode.clientapi.event.event;


import net.minecraft.network.Packet;
import neko.itskekoffcode.clientapi.event.Event;
public class EventSendPacket extends Event {
    private final Packet packet;

    private final boolean sending;

    public EventSendPacket(Packet packet, boolean sending) {
        this.packet = packet;
        this.sending = sending;
    }

    public boolean isSending() {
        return this.sending;
    }

    public boolean isRecieving() {
        return !this.sending;
    }

    public Packet getPacket() {
        return this.packet;
    }
}
