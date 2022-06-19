package neko.itskekoffcode.clientapi.event.event;
import neko.itskekoffcode.clientapi.event.Event;
public class EventJump extends Event {

    private float yaw;

    public EventJump(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}