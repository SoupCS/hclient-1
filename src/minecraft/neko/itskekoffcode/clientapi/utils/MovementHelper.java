package neko.itskekoffcode.clientapi.utils;

import neko.itskekoffcode.clientapi.event.event.MoveEvent;
import neko.itskekoffcode.hclient.helpers.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;

public class MovementHelper implements Helper {

    public static float getMoveDirection() {
        double motionX = mc.player.motionX;
        double motionZ = mc.player.motionZ;
        float direction = (float) (Math.atan2(motionX, motionZ) / Math.PI * 180);
        return -direction;
    }

    public static float getDirection() {
        float rotationYaw = mc.player.rotationYaw;

        float factor = 0f;

        if (mc.player.movementInput.moveForward > 0)
            factor = 1;
        if (mc.player.movementInput.moveForward < 0)
            factor = -1;

        if (factor == 0) {
            if (mc.player.movementInput.moveStrafe > 0)
                rotationYaw -= 90;

            if (mc.player.movementInput.moveStrafe < 0)
                rotationYaw += 90;
        } else {
            if (mc.player.movementInput.moveStrafe > 0)
                rotationYaw -= 45 * factor;

            if (mc.player.movementInput.moveStrafe < 0)
                rotationYaw += 45 * factor;
        }

        if (factor < 0)
            rotationYaw -= 180;

        return (float) Math.toRadians(rotationYaw);
    }

    public static float getDirection2() {
        Minecraft mc = Minecraft.getMinecraft();
        float var1 = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            forward = -50.5f;
        } else if (mc.player.moveForward > 0.0f) {
            forward = 50.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            var1 -= 22.0f * forward;
        }
        if (mc.player.moveStrafing < 0.0f) {
            var1 += 22.0f * forward;
        }
        return var1 *= (float) Math.PI / 180;
    }

    public static double getXDirAt(float angle) {
        double rot = 90.0;
        return Math.cos((rot += angle) * Math.PI / 180.0);
    }

    public static double getZDirAt(float angle) {
        double rot = 90.0;
        return Math.sin((rot += angle) * Math.PI / 180.0);
    }

    public static void setSpeedAt(MoveEvent e, float angle, double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
            if (mc.player.onGround) {
                e.setX(MovementHelper.getXDirAt(angle) * speed);
                e.setZ(MovementHelper.getZDirAt(angle) * speed);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isOnGround() {
        if (!mc.player.onGround) return false;
        return mc.player.isCollidedVertically;
    }

    public static void setMotion(MoveEvent e, double speed, float pseudoYaw, double aa, double po4) {
        double forward = po4;
        double strafe = aa;
        float yaw = pseudoYaw;
        if (po4 != 0.0) {
            if (aa > 0.0) {
                yaw = pseudoYaw + (float) (po4 > 0.0 ? -45 : 45);
            } else if (aa < 0.0) {
                yaw = pseudoYaw + (float) (po4 > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (po4 > 0.0) {
                forward = 1.0;
            } else if (po4 < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double kak = Math.cos(Math.toRadians(yaw + 90.0f));
        double nety = Math.sin(Math.toRadians(yaw + 90.0f));
        e.setX(forward * speed * kak + strafe * speed * nety);
        e.setZ(forward * speed * nety - strafe * speed * kak);
    }

    public static void setSpeed(double d, float f, double d2, double d3) {
        double d4 = d3;
        double d5 = d2;
        float f2 = f;
        if (d4 == 0.0 && d5 == 0.0) {
            mc.player.motionZ = 0.0;
            mc.player.motionX = 0.0;
        } else {
            if (d4 != 0.0) {
                if (d5 > 0.0) {
                    f2 += (float) (d4 > 0.0 ? -45 : 45);
                } else if (d5 < 0.0) {
                    f2 += (float) (d4 > 0.0 ? 45 : -45);
                }
                d5 = 0.0;
                if (d4 > 0.0) {
                    d4 = 1.0;
                } else if (d4 < 0.0) {
                    d4 = -1.0;
                }
            }
            double d6 = Math.cos(Math.toRadians(f2 + 90.0f));
            double d7 = Math.sin(Math.toRadians(f2 + 90.0f));
            mc.player.motionX = d4 * d * d6 + d5 * d * d7;
            mc.player.motionZ = d4 * d * d7 - d5 * d * d6;
        }
    }

    public static void setSpeed(double speed) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            mc.player.motionX = 0.0;
            mc.player.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float) (forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float) (forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            mc.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            mc.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

    public static void strafe() {
        if (MovementHelper.mc.gameSettings.keyBindBack.isKeyDown()) {
            return;
        }
        MovementHelper.strafe(MovementHelper.getSpeed());
    }

    public static float getSpeed() {
        return (float) Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isMoving() {
        if (mc.player == null) return false;
        if (mc.player.movementInput.moveForward != 0.0f) return true;
        return mc.player.movementInput.moveStrafe != 0.0f;
    }

    public static float getDirection3() {
        float rotationYaw = mc.player.rotationYaw;

        float factor = 0f;

        if (mc.player.movementInput.moveForward > 0)
            factor = 1;
        if (mc.player.movementInput.moveForward < 0)
            factor = -1;

        if (factor == 0) {
            if (mc.player.movementInput.moveStrafe > 0)
                rotationYaw -= 50;

            if (mc.player.movementInput.moveStrafe < 0)
                rotationYaw += 50;
        } else {
            if (mc.player.movementInput.moveStrafe > 0)
                rotationYaw -= 22 * factor;

            if (mc.player.movementInput.moveStrafe < 0)
                rotationYaw += 22 * factor;
        }

        if (factor < 0)
            rotationYaw -= 180;

        return (float) Math.toRadians(rotationYaw);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean hasMotion() {
        if (mc.player.motionX == 0.0) return false;
        if (mc.player.motionZ == 0.0) return false;
        return mc.player.motionY != 0.0;
    }

    public static void strafe(float speed) {
        if (!MovementHelper.isMoving()) {
            return;
        }
        double yaw = MovementHelper.getDirection();
        mc.player.motionX = -Math.sin(yaw) * (double) speed;
        mc.player.motionZ = Math.cos(yaw) * (double) speed;
    }

    public static double getMoveSpeed(MoveEvent e) {
        double xspeed = e.getX();
        double zspeed = e.getZ();
        return Math.sqrt(xspeed * xspeed + zspeed * zspeed);
    }


    public static boolean isBlockAboveHead() {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(mc.player.posX - 0.3, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ + 0.3, mc.player.posX + 0.3, mc.player.posY + (!mc.player.onGround ? 1.5 : 2.5), mc.player.posZ - 0.3);
        return mc.world.getCollisionBoxes(mc.player, axisAlignedBB).isEmpty();
    }

    public static void sunriseStrafe(float speed) {
        if (!MovementHelper.isMoving()) {
            return;
        }
        double yaw = MovementHelper.getDirection3();
        mc.player.motionX = -Math.sin(yaw) * (double)speed;
        mc.player.motionZ = Math.cos(yaw) * (double)speed;
    }
    public static void setMotionEvent(MoveEvent event, double speed) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float) (forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float) (forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
}
