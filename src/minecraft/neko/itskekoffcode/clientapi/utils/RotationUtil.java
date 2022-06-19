package neko.itskekoffcode.clientapi.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class RotationUtil {
    public static RotationUtil instance = new RotationUtil();
    private static final Minecraft mc = Minecraft.getInstance();

    public static void canSeeEntityAtFov(Entity entityLiving, float scope) {
        Minecraft.getInstance();
        double diffX = entityLiving.posX - mc.player.posX;
        Minecraft.getInstance();
        double diffZ = entityLiving.posZ - mc.player.posZ;
        float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double d = newYaw;
        Minecraft.getInstance();
    }


    public static float updateRotation(float current, float intended, float speed) {
        float f = MathHelper.wrapDegrees(intended - current);
        if (f > speed) {
            f = speed;
        }
        if (f < -speed) {
            f = -speed;
        }
        return current + f;
    }
}

