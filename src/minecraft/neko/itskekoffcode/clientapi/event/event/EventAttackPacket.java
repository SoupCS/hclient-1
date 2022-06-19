package neko.itskekoffcode.clientapi.event.event;

import net.minecraft.entity.Entity;
import neko.itskekoffcode.clientapi.event.Event;

public class EventAttackPacket
        extends Event {
    private final Entity targetEntity;

    public EventAttackPacket(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }
}

