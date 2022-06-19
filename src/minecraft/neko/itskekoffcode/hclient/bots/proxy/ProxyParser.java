package neko.itskekoffcode.hclient.bots.proxy;

import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.hclient.commands.impl.bot.connection.ProxyCommand;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ProxyParser {
    public static List<Proxy> proxies = new CopyOnWriteArrayList<>();
    private static int number = 0;

    public void init() {
        if (proxies.size() > 0) {
            BotStarter.message(String.format("(&6PROXY-PARSER&7) &eParsed &7(&6%s&7) &eproxies and successfully loaded it.", proxies.size()));
            return;
        }
        BotStarter.message("(&6PROXY-PARSER&7) &eLoading proxies...");
        if (new ProxyCommand().mix) {
            try {
                URL website = new URL("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks4");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS4, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(inputLine + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://openproxylist.xyz/socks4.txt");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS4, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(inputLine + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks4.txt");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS4, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(inputLine + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://openproxylist.xyz/socks5.txt");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS5, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(String.valueOf(inputLine) + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks5.txt");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS5, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(String.valueOf(inputLine) + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks5");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS5, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(String.valueOf(inputLine) + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            proxies = new CopyOnWriteArrayList<>(new HashSet<>(proxies));
            Collections.shuffle(proxies, new Random(System.nanoTime()));

            BotStarter.message(String.format("(&6PROXY-PARSER&7) &eParsed &7(&6%s&7) &eproxies and successfully loaded it.", proxies.size()));
        }
        if (Objects.equals(new ProxyCommand().type, "4")) {
            try {
                URL website = new URL("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks4");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS4, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(inputLine + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://openproxylist.xyz/socks4.txt");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS4, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(inputLine + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks4.txt");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS4, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(inputLine + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }

            proxies = new CopyOnWriteArrayList<>(new HashSet<>(proxies));
            Collections.shuffle(proxies, new Random(System.nanoTime()));

            BotStarter.message(String.format("(&6PROXY-PARSER&7) &eParsed &7(&6%s&7) &eproxies and successfully loaded it.", proxies.size()));
        }
        if (Objects.equals(new ProxyCommand().type, "5")) {
            try {
                URL website = new URL("https://openproxylist.xyz/socks5.txt");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS5, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(String.valueOf(inputLine) + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks5.txt");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS5, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(String.valueOf(inputLine) + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }
            try {
                URL website = new URL("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks5");
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains(":")) {
                        String ip = inputLine.split(":")[0];
                        int port = Integer.valueOf(inputLine.split(":")[1]);
                        Proxy proxy = new Proxy(ProxyType.SOCKS5, new InetSocketAddress(ip, port));
                        proxies.add(proxy);
                    } else {
                        System.out.println(String.valueOf(inputLine) + " error");
                    }
                }
            } catch (MalformedURLException var8) {
                System.err.println("Page does not exist!");
            } catch (IOException var9) {
                System.err.println("No internet!");
            }

            proxies = new CopyOnWriteArrayList<>(new HashSet<>(proxies));
            Collections.shuffle(proxies, new Random(System.nanoTime()));

            BotStarter.message(String.format("(&6PROXY-PARSER&7) &eParsed &7(&6%s&7) &eproxies and successfully loaded it.", proxies.size()));
        }

    }

    public void loadfromurl(String url, String proxytype) {
        BotStarter.message("(&6PROXY-PARSER&7) &eLoading proxies...");
        try {
            try {
                Document proxyList = Jsoup.connect(url).timeout(50000).get();
                if (proxytype == "4") {
                    proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(ProxyType.SOCKS4, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
                }
                else if (proxytype == "4") {
                    proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(ProxyType.SOCKS5, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
                }
                else if (proxytype == "h") {
                    proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(ProxyType.HTTP, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
                }
                else if (proxytype == "s") {
                    proxies.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map((proxy) -> new Proxy(ProxyType.HTTP, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
                }
            } catch (Exception ignored) {ignored.printStackTrace();}


            proxies = new CopyOnWriteArrayList<>(new HashSet<>(proxies));
            Collections.shuffle(proxies, new Random(System.nanoTime()));

            BotStarter.message(String.format("(&6PROXY-PARSER&7) &eParsed &7(&6%s&7) &eproxies and successfully loaded it.", proxies.size()));

        } catch (Exception ignored) {}
    }

    public static void loadfromfile(String path, String proxytype) {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            while (reader.ready()) {
                try {
                    String line = reader.readLine();

                    if (proxytype == "4") {
                        proxies.add(new Proxy(ProxyType.SOCKS4, new InetSocketAddress(line.split(":")[0], Integer.parseInt(line.split(":")[1]))));
                    }
                    else if (proxytype == "5") {
                        proxies.add(new Proxy(ProxyType.SOCKS5, new InetSocketAddress(line.split(":")[0], Integer.parseInt(line.split(":")[1]))));
                    }
                    else if (proxytype == "h") {
                        proxies.add(new Proxy(ProxyType.HTTP, new InetSocketAddress(line.split(":")[0], Integer.parseInt(line.split(":")[1]))));
                    }
                    else if (proxytype == "s") {
                        proxies.add(new Proxy(ProxyType.HTTP, new InetSocketAddress(line.split(":")[0], Integer.parseInt(line.split(":")[1]))));
                    }
                } catch (Exception ignored) {}

            }


            proxies = new CopyOnWriteArrayList<>(new HashSet<>(proxies));
            Collections.shuffle(proxies, new Random(System.nanoTime()));
        } catch (Exception ignored) {}
    }

    public static Proxy getProxy() {
        if (++number >= proxies.size() - 1)
            number = 0;

        return proxies.get(number);
    }

    public Proxy nextProxy() {
        ++number;

        if (number >= proxies.size())
            number = 0;

        return proxies.get(number);
    }


    public static class Proxy {
        private final ProxyType type;
        private final InetSocketAddress address;

        public Proxy(ProxyType type, InetSocketAddress address) {
            this.type = type;
            this.address = address;
        }

        public ProxyType getType() {
            return type;
        }

        public InetSocketAddress getAddress() {
            return address;
        }
    }

    public enum ProxyType {
        SOCKS4,
        SOCKS5,
        HTTP
    }
}
