package neko.itskekoffcode.clientapi.event.event;

import neko.itskekoffcode.clientapi.event.Event;
public class EventMessage extends Event {

    public String message;

    public EventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}