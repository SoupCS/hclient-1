package neko.itskekoffcode.clientapi.utils;

public class ThreadUtils {
    public static void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception ignored) {}
    }
}
