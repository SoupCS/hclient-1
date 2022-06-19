package neko.itskekoffcode.clientapi.event.event;

import neko.itskekoffcode.clientapi.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Event2D extends Event {
    private final float width;
    private final float height;
    private ScaledResolution resolution;

    public Event2D(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    public ScaledResolution getResolution() {
        return resolution;
    }

}


