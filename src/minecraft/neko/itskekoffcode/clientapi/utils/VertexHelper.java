//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\itskekoff\Documents\mcp\conf"!

package neko.itskekoffcode.clientapi.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
        mv = {1, 4, 2},
        bv = {1, 0, 3},
        k = 1,
        xi = 48,
        d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003?\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\u000bJ&\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015J\u001e\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0015R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004?\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0007*\u0004\u0018\u00010\t0\tX\u0082\u0004?\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004?\u0006\u0002\n\u0000?\u0006\u0019"},
        d2 = {"Lorg/kamiblue/client/util/graphics/VertexHelper;", "", "useVbo", "", "(Z)V", "buffer", "Lnet/minecraft/client/renderer/BufferBuilder;", "kotlin.jvm.PlatformType", "tessellator", "Lnet/minecraft/client/renderer/Tessellator;", "begin", "", "mode", "", "end", "put", "x", "", "y", "z", "color", "Lorg/kamiblue/client/util/color/ColorHolder;", "pos", "Lnet/minecraft/util/math/Vec3d;", "Lorg/kamiblue/client/util/math/Vec2d;", "kamiblue"}
)
public final class VertexHelper {
    private final boolean useVbo;
    private final Tessellator tessellator;
    private final BufferBuilder buffer;

    public VertexHelper(boolean useVbo) {
        this.useVbo = useVbo;
        this.tessellator = Tessellator.getInstance();
        this.buffer = this.tessellator.getBuffer();
    }

    public final void begin(int mode) {
        if (this.useVbo) {
            this.buffer.begin(mode, DefaultVertexFormats.POSITION_COLOR);
        } else {
            GL11.glBegin(mode);
        }

    }

    public final void put(@NotNull Vec3d pos, @NotNull ColorHolder color) {
        Intrinsics.checkNotNullParameter(pos, "pos");
        Intrinsics.checkNotNullParameter(color, "color");
        this.put(pos.xCoord, pos.yCoord, pos.zCoord, color);
    }

    public final void put(double x, double y, double z, @NotNull ColorHolder color) {
        Intrinsics.checkNotNullParameter(color, "color");
        if (this.useVbo) {
            this.buffer.pos(x, y, z).color(color.getR(), color.getG(), color.getB(), color.getA()).endVertex();
        } else {
            color.setGLColor();
            GL11.glVertex3d(x, y, z);
        }

    }

    public final void put(@NotNull Vec2d pos, @NotNull ColorHolder color) {
        Intrinsics.checkNotNullParameter(pos, "pos");
        Intrinsics.checkNotNullParameter(color, "color");
        this.put(pos.getX(), pos.getY(), color);
    }

    public final void put(double x, double y, @NotNull ColorHolder color) {
        Intrinsics.checkNotNullParameter(color, "color");
        if (this.useVbo) {
            this.buffer.pos(x, y, 0.0D).color(color.getR(), color.getG(), color.getB(), color.getA()).endVertex();
        } else {
            color.setGLColor();
            GL11.glVertex2d(x, y);
        }

    }

    public final void end() {
        if (this.useVbo) {
            this.tessellator.draw();
        } else {
            GL11.glEnd();
        }

    }
}
