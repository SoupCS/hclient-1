package neko.itskekoffcode.clientapi.event.event;

import net.minecraft.entity.Entity;
import neko.itskekoffcode.clientapi.event.Event;

public class EventAttackClient extends Event {

    private final Entity entity;

    public EventAttackClient(Entity targetEntity) {
        this.entity = targetEntity;
    }

    public Entity getEntity() {
        return entity;
    }
}