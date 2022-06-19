package neko.itskekoffcode.clientapi.utils;

import neko.itskekoffcode.clientapi.event.event.EventMove;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.Vec2f;

public class MovementUtils  {
    public static boolean status;
    private static int cunt;
    public static float moveStrafe;
    public static float moveForward;
    public static boolean forwardKeyDown;
    public static boolean backKeyDown;
    public static boolean leftKeyDown;
    public static boolean rightKeyDown;
    public static boolean jump;
    public static boolean sneak;

    public void updatePlayerMoveState() {
    }

    public Vec2f getMoveVector() {
        return new Vec2f(moveStrafe, moveForward);
    }

    public static int getSpeedEffect() {
        if (Minecraft.getInstance().player.isPotionActive(Potion.getPotionById(1)))
            return Minecraft.getInstance().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() + 1;
        else
            return 0;
    }


    public static void setMotion(EventMove event, double speed) {
        double forward = moveForward;
        double strafe = moveStrafe;
        float yaw = Minecraft.getInstance().player.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
            event.setX(0.0D);
            event.setZ(0.0D);
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
        }
    }

    public static void setMotion(double speed) {
        double forward = moveForward;
        double strafe = moveStrafe;
        float yaw = Minecraft.getInstance().player.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            Minecraft.getInstance().player.motionX = 0;
            Minecraft.getInstance().player.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            Minecraft.getInstance().player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            Minecraft.getInstance().player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public static void strafe(float speed) {
        if (Minecraft.getInstance().gameSettings.keyBindBack.isKeyDown()) {
            return;
        }
        MovementUtils.strafe(MovementUtils.getSpeed());
    }
    public static float getSpeed() {
        return (float)Math.sqrt(Minecraft.getInstance().player.motionX * Minecraft.getInstance().player.motionX + Minecraft.getInstance().player.motionZ * Minecraft.getInstance().player.motionZ);
    }


}
