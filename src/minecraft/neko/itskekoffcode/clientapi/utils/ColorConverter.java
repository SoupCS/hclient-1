package neko.itskekoffcode.clientapi.utils;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

public final class ColorConverter {
    @NotNull
    public static final ColorConverter INSTANCE = new ColorConverter();

    private ColorConverter() {
    }

    public final float toF(int i) {
        return (float)i / 255.0F;
    }

    public final float toF(double d) {
        return (float)(d / (double)255.0F);
    }

    public final int rgbToHex(int r, int g, int b, int a) {
        return r << 16 | g << 8 | b | a << 24;
    }

    public final int rgbToHex(int r, int g, int b) {
        return r << 16 | g << 8 | b;
    }

    @NotNull
    public final ColorHolder hexToRgb(int hexColor) {
        int r = hexColor >> 16 & 255;
        int g = hexColor >> 8 & 255;
        int b = hexColor & 255;
        return new ColorHolder(r, g, b, 0, 8, null);
    }
}
