package neko.itskekoffcode.clientapi.utils;

import neko.itskekoffcode.hclient.helpers.Helper;

public class TimerHelper implements Helper {
    private final long previousTime = -1L;


    public boolean check(float milliseconds) {
        return (float)(this.getCurrentTime() - this.previousTime) >= milliseconds;
    }

    private long ms = getCurrentMS();

    private long getCurrentMS() {
        return System.currentTimeMillis();
    }

    public boolean hasReached(float milliseconds) {
        return getCurrentMS() - ms > milliseconds;
    }

    public void reset() {
        ms = getCurrentMS();
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public long getTime() {
        return System.currentTimeMillis() - this.previousTime;
    }
}
