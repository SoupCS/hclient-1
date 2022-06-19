package neko.itskekoffcode.clientapi.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.client.Minecraft;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ProxyHelper {
    private static int number = 0;
    public static List<Proxy> proxies = new ArrayList();
    private static int currentPosition = 0;

    public void fromFile() {
        try {
            proxies.clear();
            File proxydir = new File(Minecraft.getMinecraft().mcDataDir + "/HClient/proxy/proxies.txt");
            if (!proxydir.exists()) {
                ChatHelper.addChatMessage(ChatFormatting.RED + "File not found! Do \".proxy reParse\" instead.");
            } else {
                try (BufferedReader reader = new BufferedReader(new FileReader(proxydir))) {
                    while (reader.ready()) {
                        try {
                            String line = reader.readLine();
                            proxies.add(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(line.split(":")[0], Integer.parseInt(line.split(":")[1]))));
                        } catch (Exception ignored) {
                        }
                    }
                } catch (Exception ignored) {}
            }
            ChatHelper.addChatMessage("Loaded " + proxies.size() + " proxies.");
        } catch (Exception ignored) {}
    }
    public void reparse() {
        try {
            proxies.clear();
            File proxydir = new File(Minecraft.getMinecraft().mcDataDir + "/HClient/proxy/proxies.txt");
            if (proxydir.exists()) {
                proxydir.delete();
            }

            try {
                Document proxyList = Jsoup.connect("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks4").get();
                proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}


            try {
                Document proxyList = Jsoup.connect("https://openproxylist.xyz/socks4.txt").get();
                proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}

            try {
                Document proxyList = Jsoup.connect("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks5").get();
                proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}


            try {
                Document proxyList = Jsoup.connect("https://openproxylist.xyz/socks5.txt").get();
                proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}
            proxies = new CopyOnWriteArrayList<>(new HashSet<>(proxies));
            Random pablo = new Random(System.currentTimeMillis());
            Collections.shuffle(proxies, pablo);
            new File(Minecraft.getMinecraft().mcDataDir + "/HClient/proxy/").mkdirs();
            proxydir.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(proxydir))) {
                for (Proxy proxy : proxies) {
                    writer.write(proxy.toString().split("/")[1] + "\n");
                }
            }
            ChatHelper.addChatMessage("Reparsed " + proxies.size() + " proxies.");
        } catch (Exception ignored) {}
    }
    public void loadProxies() {
        try {
            File proxydir = new File(Minecraft.getMinecraft().mcDataDir + "/HClient/proxy/proxies.txt");
            if (!proxydir.exists()) {
                try {
                    Document proxyList = Jsoup.connect("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks4").get();
                    proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
                } catch (Throwable ignored) {}


                try {
                    Document proxyList = Jsoup.connect("https://openproxylist.xyz/socks4.txt").get();
                    proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
                } catch (Throwable ignored) {}

                try {
                    Document proxyList = Jsoup.connect("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks5").get();
                    proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
                } catch (Throwable ignored) {}


                try {
                    Document proxyList = Jsoup.connect("https://openproxylist.xyz/socks5.txt").get();
                    proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
                } catch (Throwable ignored) {}
                proxies = new CopyOnWriteArrayList<>(new HashSet<>(proxies));
                Random pablo = new Random(System.currentTimeMillis());
                Collections.shuffle(proxies, pablo);
                new File(Minecraft.getMinecraft().mcDataDir + "/HClient/proxy/").mkdirs();
                proxydir.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(proxydir))) {
                    for (Proxy proxy : proxies) {
                        writer.write(proxy.toString().split("/")[1] + "\n");
                    }
                }


            } else {
                proxies.clear();
                try (BufferedReader reader = new BufferedReader(new FileReader(proxydir))) {
                    while (reader.ready()) {
                        try {
                            String line = reader.readLine();
                            proxies.add(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(line.split(":")[0], Integer.parseInt(line.split(":")[1]))));
                        } catch (Exception ignored) {}
                    }
                } catch (Exception ignored) {}
                return;
            }
        } catch (Exception ignored) {}
    }

    public static Proxy getRandomProxy() {
        ++number;
        if (number >= proxies.size()) {
            number = 0;
        }

        return proxies.get(number);
    }

    public static synchronized void reset() {
        currentPosition = 0;
    }

    public synchronized Proxy nextProxy() {
        int next = currentPosition + 1;
        if (next >= proxies.size() - 1) {
            next = 0;
        }

        return proxies.get(currentPosition = next);
    }

    public static void removeProxy(Proxy proxy) {
        proxies.remove(proxy);
    }

    public static Socket ready(Proxy proxy) {
        return new Socket(proxy);
    }


}
