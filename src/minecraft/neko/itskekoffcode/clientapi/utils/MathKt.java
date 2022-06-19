package neko.itskekoffcode.clientapi.utils;

import kotlin.Metadata;

@Metadata(
        mv = {1, 4, 2},
        bv = {1, 0, 3},
        k = 2,
        xi = 48,
        d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0006\n\u0002\b\u0004\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\u0005\u001a\u00020\u0003*\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\u0006\u001a\u00020\u0004*\u00020\u0004\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0007\u001a\u00020\u0004*\u00020\u0004\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T?\u0006\u0002\n\u0000?\u0006\b"},
        d2 = {"PI_FLOAT", "", "ceilToInt", "", "", "floorToInt", "toDegree", "toRadian", "kamiblue"}
)
public final class MathKt {
    public static final float PI_FLOAT = 3.1415927F;

    public static final int floorToInt(double $this$floorToInt) {
        boolean var2 = false;
        return (int)Math.floor($this$floorToInt);
    }

    public static final int floorToInt(float $this$floorToInt) {
        boolean var1 = false;
        return (int)((float)Math.floor($this$floorToInt));
    }

    public static final int ceilToInt(double $this$ceilToInt) {
        boolean var2 = false;
        return (int)Math.ceil($this$ceilToInt);
    }

    public static final int ceilToInt(float $this$ceilToInt) {
        boolean var1 = false;
        return (int)((float)Math.ceil($this$ceilToInt));
    }

    public static final float toRadian(float $this$toRadian) {
        return $this$toRadian / 180.0F * 3.1415927F;
    }

    public static final double toRadian(double $this$toRadian) {
        return $this$toRadian / 180.0D * 3.141592653589793D;
    }

    public static final float toDegree(float $this$toDegree) {
        return $this$toDegree * 180.0F / 3.1415927F;
    }

    public static final double toDegree(double $this$toDegree) {
        return $this$toDegree * 180.0D / 3.141592653589793D;
    }
}
