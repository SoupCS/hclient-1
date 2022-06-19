package neko.itskekoffcode.clientapi.event.event;

import neko.itskekoffcode.clientapi.event.Event;

public class Event3D extends Event {
    private final float partialTicks;

    public Event3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}