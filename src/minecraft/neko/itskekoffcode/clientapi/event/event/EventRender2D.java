package neko.itskekoffcode.clientapi.event.event;

import neko.itskekoffcode.clientapi.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D extends Event {

    private final ScaledResolution resolution;
    private final float partialticks;

    public EventRender2D(ScaledResolution resolution, float partialticks) {
        this.resolution = resolution;
        this.partialticks = partialticks;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }

    public float getPartialTicks() {
        return partialticks;
    }
}