package neko.itskekoffcode.hclient.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public class ProxyManager {
    public static volatile Proxy proxy;

    public static Proxy getProxyFromString(String proxy) {
        return new Proxy(Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.valueOf(proxy.split(":")[1])));
    }

    public static void setProxy(Proxy proxy) {
        ProxyManager.proxy = proxy;
    }

    public static Proxy getProxy() {
        return proxy;
    }
}
