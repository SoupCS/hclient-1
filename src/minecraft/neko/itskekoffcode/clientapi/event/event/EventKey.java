package neko.itskekoffcode.clientapi.event.event;

import neko.itskekoffcode.clientapi.event.Event;
public class EventKey extends Event {
    private final int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}