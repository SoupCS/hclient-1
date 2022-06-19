package neko.itskekoffcode.clientapi.utils;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public final class Quad<A, B, C, D> implements Serializable {
    private final A first;
    private final B second;
    private final C third;
    private final D fourth;

    public Quad(A first, B second, C third, D fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public final C getThird() {
        return this.third;
    }

    public final D getFourth() {
        return this.fourth;
    }

    @NotNull
    public String toString() {
        return "" + '(' + this.first + ", " + this.second + ", " + this.third + ", " + this.fourth + ')';
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public final C component3() {
        return this.third;
    }

    public final D component4() {
        return this.fourth;
    }

    @NotNull
    public final Quad<A, B, C, D> copy(A first, B second, C third, D fourth) {
        return new Quad(first, second, third, fourth);
    }

    // $FF: synthetic method
    public static Quad copy$default(Quad var0, Object var1, Object var2, Object var3, Object var4, int var5, Object var6) {
        if ((var5 & 1) != 0) {
            var1 = var0.first;
        }

        if ((var5 & 2) != 0) {
            var2 = var0.second;
        }

        if ((var5 & 4) != 0) {
            var3 = var0.third;
        }

        if ((var5 & 8) != 0) {
            var4 = var0.fourth;
        }

        return var0.copy(var1, var2, var3, var4);
    }

    public int hashCode() {
        int result = this.first == null ? 0 : this.first.hashCode();
        result = result * 31 + (this.second == null ? 0 : this.second.hashCode());
        result = result * 31 + (this.third == null ? 0 : this.third.hashCode());
        result = result * 31 + (this.fourth == null ? 0 : this.fourth.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Quad)) {
            return false;
        } else {
            Quad var2 = (Quad)other;
            if (!Intrinsics.areEqual(this.first, var2.first)) {
                return false;
            } else if (!Intrinsics.areEqual(this.second, var2.second)) {
                return false;
            } else if (!Intrinsics.areEqual(this.third, var2.third)) {
                return false;
            } else {
                return Intrinsics.areEqual(this.fourth, var2.fourth);
            }
        }
    }
}
