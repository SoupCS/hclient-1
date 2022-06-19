package neko.itskekoffcode.hclient.proxy;

import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.socket.oio.OioSocketChannel;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.Socket;

public class ProxyUtils implements ChannelFactory<OioSocketChannel> {
    private Proxy proxy;

    public ProxyUtils(Proxy proxy) {
        this.proxy = proxy;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public OioSocketChannel newChannel() {
        if (this.proxy != null && this.proxy != Proxy.NO_PROXY) {
            Socket sock = new Socket(this.proxy);

            try {
                Method m = sock.getClass().getDeclaredMethod("getImpl");
                m.setAccessible(true);
                Object sd = m.invoke(sock);
                m = sd.getClass().getDeclaredMethod("setV4");
                m.setAccessible(true);
                m.invoke(sd);
                return new OioSocketChannel(sock);
            } catch (Exception var4) {
                var4.printStackTrace();
                return null;
            }
        } else {
            return new OioSocketChannel(new Socket(Proxy.NO_PROXY));
        }
    }

}
