package neko.itskekoffcode.clientapi.event.event;

import neko.itskekoffcode.clientapi.event.Event;
public class EventReceiveMessage extends Event {

    public String message;
    public boolean cancelled;

    public EventReceiveMessage(String chat) {
        message = chat;
    }

    public String getMessage() {
        return message;
    }

    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}