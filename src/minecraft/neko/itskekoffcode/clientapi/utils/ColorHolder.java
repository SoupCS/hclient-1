package neko.itskekoffcode.clientapi.utils;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.lwjgl.opengl.GL11;

public class ColorHolder {
    private int r;
    private int g;
    private int b;
    private int a;

    public ColorHolder(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColorHolder(int var1, int var2, int var3, int var4, int var5, DefaultConstructorMarker var6) {
        this(var1, var2, var3, var4);
        if ((var5 & 1) != 0) {
            var1 = 255;
        }

        if ((var5 & 2) != 0) {
            var2 = 255;
        }

        if ((var5 & 4) != 0) {
            var3 = 255;
        }

        if ((var5 & 8) != 0) {
            var4 = 255;
        }
    }

    public final int getR() {
        return this.r;
    }

    public final void setR(int var1) {
        this.r = var1;
    }

    public final int getG() {
        return this.g;
    }

    public final void setG(int var1) {
        this.g = var1;
    }

    public final int getB() {
        return this.b;
    }

    public final void setB(int var1) {
        this.b = var1;
    }

    public final int getA() {
        return this.a;
    }

    public final void setA(int var1) {
        this.a = var1;
    }

    public ColorHolder(@NotNull Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        Intrinsics.checkNotNullParameter(color, "color");
    }

    public final float getBrightness() {
        int[] var2 = new int[]{this.r, this.g, this.b};
        Integer var1 = ArraysKt.maxOrNull(var2);
        Intrinsics.checkNotNull(var1);
        return (float)var1 / 255.0F;
    }

    public final float getAverageBrightness() {
        int[] var1 = new int[]{this.r, this.g, this.b};
        return (float)(ArraysKt.average(var1) / 255.0D);
    }

    @NotNull
    public final ColorHolder multiply(float multiplier) {
        return new ColorHolder(RangesKt.coerceIn((int)((float)this.r * multiplier), 0, 255), RangesKt.coerceIn((int)((float)this.g * multiplier), 0, 255), RangesKt.coerceIn((int)((float)this.b * multiplier), 0, 255), this.a);
    }

    @NotNull
    public final ColorHolder mix(@NotNull ColorHolder other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new ColorHolder((this.r + other.r) / 2 + (this.g + other.g) / 2, (this.b + other.b) / 2, (this.a + other.a) / 2, 0, 8, null);
    }


    public final void setGLColor() {
        GL11.glColor4f((float)this.r / 255.0F, (float)this.g / 255.0F, (float)this.b / 255.0F, (float)this.a / 255.0F);
    }

    public final int toHex() {
        return -16777216 | (this.r & 255) << 16 | (this.g & 255) << 8 | this.b & 255;
    }

    @NotNull
    public final ColorHolder clone() {
        return new ColorHolder(this.r, this.g, this.b, this.a);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else if (!Intrinsics.areEqual(this.getClass(), other == null ? null : other.getClass())) {
            return false;
        } else if (other == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.kamiblue.client.util.color.ColorHolder");
        } else {
            ColorHolder var10000 = (ColorHolder)other;
            if (this.r != ((ColorHolder)other).r) {
                return false;
            } else if (this.g != ((ColorHolder)other).g) {
                return false;
            } else if (this.b != ((ColorHolder)other).b) {
                return false;
            } else {
                return this.a == ((ColorHolder)other).a;
            }
        }
    }

    public int hashCode() {
        int result = this.r;
        result = 31 * result + this.g;
        result = 31 * result + this.b;
        result = 31 * result + this.a;
        return result;
    }

    public final int component1() {
        return this.r;
    }

    public final int component2() {
        return this.g;
    }

    public final int component3() {
        return this.b;
    }

    public final int component4() {
        return this.a;
    }

    @NotNull
    public final ColorHolder copy(int r, int g, int b, int a) {
        return new ColorHolder(r, g, b, a);
    }

    // $FF: synthetic method
    public static ColorHolder copy$default(ColorHolder var0, int var1, int var2, int var3, int var4, int var5, Object var6) {
        if ((var5 & 1) != 0) {
            var1 = var0.r;
        }

        if ((var5 & 2) != 0) {
            var2 = var0.g;
        }

        if ((var5 & 4) != 0) {
            var3 = var0.b;
        }

        if ((var5 & 8) != 0) {
            var4 = var0.a;
        }

        return var0.copy(var1, var2, var3, var4);
    }

    @NotNull
    public String toString() {
        return "ColorHolder(r=" + this.r + ", g=" + this.g + ", b=" + this.b + ", a=" + this.a + ')';
    }

    public ColorHolder() {
        this(0, 0, 0, 0, 15, null);
    }
}
