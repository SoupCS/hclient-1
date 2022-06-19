package neko.itskekoffcode.clientapi.event.event;

import neko.itskekoffcode.clientapi.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class EventMove extends Event {
    private double x;
    private double y;
    private double z;
    public float strafe;
    public float forward;
    public float friction;
    public float yaw;
    public boolean canceled;

    public EventMove(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getMovementSpeed() {
        double baseSpeed = 0.2873D;
        if (Minecraft.getInstance().player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.getInstance().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public double getMovementSpeed(double baseSpeed) {
        double speed = baseSpeed;
        if (Minecraft.getInstance().player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.getInstance().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            return speed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return speed;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public double getLegitMotion() {
        return 0.41999998688697815D;
    }

    public double getMotionY(double mY) {
        if (Minecraft.getInstance().player.isPotionActive(Potion.getPotionById(8))) {
            mY += (Minecraft.getInstance().player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier() + 1) * 0.1;
        }
        return mY;
    }
}
