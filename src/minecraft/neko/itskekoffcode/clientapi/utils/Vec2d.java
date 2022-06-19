//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\itskekoff\Documents\mcp\conf"!

package neko.itskekoffcode.clientapi.utils;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 4, 2},
        bv = {1, 0, 3},
        k = 1,
        xi = 48,
        d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u0000 %2\u00020\u0001:\u0001%B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003?\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0000?\u0006\u0002\u0010\u0006B\u0019\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b?\u0006\u0002\u0010\nJ\t\u0010\u000e\u001a\u00020\bH?\u0003J\t\u0010\u000f\u001a\u00020\bH?\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bH?\u0001J\u0011\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\bH\u0086\u0002J\u0016\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u0011\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0000H\u0086\u0002J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H?\u0003J\t\u0010\u0016\u001a\u00020\u0017H?\u0001J\u0006\u0010\u0018\u001a\u00020\bJ\u0006\u0010\u0019\u001a\u00020\bJ\u0011\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\bH\u0086\u0002J\u0016\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u0011\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0000H\u0086\u0002J\u0011\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\bH\u0086\u0002J\u0016\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u0011\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0000H\u0086\u0002J\u0011\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\bH\u0086\u0002J\u0016\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u0011\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0000H\u0086\u0002J\u0006\u0010 \u001a\u00020\u0000J\b\u0010!\u001a\u00020\"H\u0016J\u0006\u0010#\u001a\u00020$R\u0011\u0010\u0007\u001a\u00020\b?\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\t\u001a\u00020\b?\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f?\u0006&"},
        d2 = {"Lorg/kamiblue/client/util/math/Vec2d;", "", "vec3d", "Lnet/minecraft/util/math/Vec3d;", "(Lnet/minecraft/util/math/Vec3d;)V", "vec2d", "(Lorg/kamiblue/client/util/math/Vec2d;)V", "x", "", "y", "(DD)V", "getX", "()D", "getY", "component1", "component2", "copy", "div", "divider", "equals", "", "other", "hashCode", "", "length", "lengthSquared", "minus", "sub", "plus", "add", "times", "multiplier", "toRadians", "toString", "", "toVec2f", "Lorg/kamiblue/client/util/math/Vec2f;", "Companion", "kamiblue"}
)
public final class Vec2d {
    @NotNull
    public static final Vec2d.Companion Companion = new Vec2d.Companion(null);
    private final double x;
    private final double y;
    @JvmField
    @NotNull
    public static final Vec2d ZERO = new Vec2d(0.0D, 0.0D);

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // $FF: synthetic method
    public Vec2d(double var1, double var3, int var5, DefaultConstructorMarker var6) {
        this(var1, var3);
        if ((var5 & 1) != 0) {
            var1 = 0.0D;
        }

        if ((var5 & 2) != 0) {
            var3 = 0.0D;
        }
    }

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public Vec2d(@NotNull Vec3d vec3d) {
        this(vec3d.xCoord, vec3d.yCoord);
        Intrinsics.checkNotNullParameter(vec3d, "vec3d");
    }

    public Vec2d(@NotNull Vec2d vec2d) {
        this(vec2d.x, vec2d.y);
        Intrinsics.checkNotNullParameter(vec2d, "vec2d");
    }

    @NotNull
    public final Vec2d toRadians() {
        return new Vec2d(MathKt.toRadian(this.x), MathKt.toRadian(this.y));
    }

    public final double length() {
        double var1 = this.x;
        double var3 = this.y;
        boolean var5 = false;
        return Math.hypot(var1, var3);
    }

    public final double lengthSquared() {
        double var1 = this.x;
        byte var3 = 2;
        boolean var4 = false;
        double var10000 = Math.pow(var1, var3);
        var1 = this.y;
        var3 = 2;
        var4 = false;
        return var10000 + Math.pow(var1, var3);
    }

    @NotNull
    public final Vec2d div(@NotNull Vec2d vec2d) {
        Intrinsics.checkNotNullParameter(vec2d, "vec2d");
        return this.div(vec2d.x, vec2d.y);
    }

    @NotNull
    public final Vec2d div(double divider) {
        return this.div(divider, divider);
    }

    @NotNull
    public final Vec2d div(double x, double y) {
        return new Vec2d(this.x / x, this.y / y);
    }

    @NotNull
    public final Vec2d times(@NotNull Vec2d vec2d) {
        Intrinsics.checkNotNullParameter(vec2d, "vec2d");
        return this.times(vec2d.x, vec2d.y);
    }

    @NotNull
    public final Vec2d times(double multiplier) {
        return this.times(multiplier, multiplier);
    }

    @NotNull
    public final Vec2d times(double x, double y) {
        return new Vec2d(this.x * x, this.y * y);
    }

    @NotNull
    public final Vec2d minus(@NotNull Vec2d vec2d) {
        Intrinsics.checkNotNullParameter(vec2d, "vec2d");
        return this.minus(vec2d.x, vec2d.y);
    }

    @NotNull
    public final Vec2d minus(double sub) {
        return this.minus(sub, sub);
    }

    @NotNull
    public final Vec2d minus(double x, double y) {
        return this.plus(-x, -y);
    }

    @NotNull
    public final Vec2d plus(@NotNull Vec2d vec2d) {
        Intrinsics.checkNotNullParameter(vec2d, "vec2d");
        return this.plus(vec2d.x, vec2d.y);
    }

    @NotNull
    public final Vec2d plus(double add) {
        return this.plus(add, add);
    }

    @NotNull
    public final Vec2d plus(double x, double y) {
        return new Vec2d(this.x + x, this.y + y);
    }

    @NotNull
    public final Vec2f toVec2f() {
        return new Vec2f((float)this.x, (float)this.y);
    }

    @NotNull
    public String toString() {
        return "Vec2d[" + this.x + ", " + this.y + ']';
    }

    public final double component1() {
        return this.x;
    }

    public final double component2() {
        return this.y;
    }

    @NotNull
    public final Vec2d copy(double x, double y) {
        return new Vec2d(x, y);
    }

    // $FF: synthetic method
    public static Vec2d copy$default(Vec2d var0, double var1, double var3, int var5, Object var6) {
        if ((var5 & 1) != 0) {
            var1 = var0.x;
        }

        if ((var5 & 2) != 0) {
            var3 = var0.y;
        }

        return var0.copy(var1, var3);
    }

    public int hashCode() {
        int result = Double.hashCode(this.x);
        result = result * 31 + Double.hashCode(this.y);
        return result;
    }

    public Vec2d() {
        this(0.0D, 0.0D, 3, null);
    }

    @Metadata(
            mv = {1, 4, 2},
            bv = {1, 0, 3},
            k = 1,
            xi = 48,
            d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002?\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004?\u0006\u0002\n\u0000?\u0006\u0005"},
            d2 = {"Lorg/kamiblue/client/util/math/Vec2d$Companion;", "", "()V", "ZERO", "Lorg/kamiblue/client/util/math/Vec2d;", "kamiblue"}
    )
    public static final class Companion {
        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
