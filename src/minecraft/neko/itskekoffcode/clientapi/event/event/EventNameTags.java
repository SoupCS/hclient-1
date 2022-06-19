package neko.itskekoffcode.clientapi.event.event;

import net.minecraft.entity.EntityLivingBase;
import neko.itskekoffcode.clientapi.event.Event;
public class EventNameTags extends Event {
    private final EntityLivingBase entity;
    private String renderedName;

    public EventNameTags(EntityLivingBase entity, String renderedName) {
        this.entity = entity;
        this.renderedName = renderedName;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }

    public String getRenderedName() {
        return this.renderedName;
    }

    public void setRenderedName(String renderedName) {
        this.renderedName = renderedName;
    }

}
