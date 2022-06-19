package neko.itskekoffcode.clientapi.utils;

import org.jetbrains.annotations.NotNull;

public class AntiDisconnect {
    @NotNull
    public static final AntiDisconnect INSTANCE = new AntiDisconnect();

    @NotNull
    public final int getPresses() {
        return 3;
    }
}
