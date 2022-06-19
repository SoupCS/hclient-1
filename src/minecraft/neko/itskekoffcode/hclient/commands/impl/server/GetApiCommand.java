package neko.itskekoffcode.hclient.commands.impl.server;

import neko.itskekoffcode.hclient.commands.Command;
import net.minecraft.client.Minecraft;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetApiCommand extends Command {

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            (new Thread(() -> {
                try {
                    if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
                        GetApiCommand.msg("Use Muliplayer Only", true);
                    } else {
                        assert Minecraft.getMinecraft().getCurrentServerData() != null;
                        GetApiCommand.msg(String.format("Retrieving server info (%s)...", Minecraft.getMinecraft().getCurrentServerData().serverIP), true);
                        final JSONObject jsonObject = getObjectFromWebsite("https://api.mcsrvstat.us/2/" + Minecraft.getMinecraft().getCurrentServerData().serverIP);

                        String ip = jsonObject.getString("ip");
                        int port = jsonObject.getInt("port");
                        String hostname = jsonObject.getString("hostname");
                        String version = jsonObject.getString("version");

                        GetApiCommand.msg("", false);
                        GetApiCommand.msg("&dIP: &7" + ip, true);
                        GetApiCommand.msg("&dPort: &7" + port, true);
                        GetApiCommand.msg("&dHostname: &7" + hostname, true);
                        GetApiCommand.msg("&dVersion: &7" + version, true);
                        GetApiCommand.msg("", false);
                    }
                } catch (Exception e) { e.printStackTrace(); }
            })).start();
        }
        if (args.length == 1) {
            (new Thread(() -> {
                try {
                    //System.out.println(args[0] + "/" + args[1]);
                    GetApiCommand.msg(String.format("Retrieving server info (%s)...", args[0]), true);
                    final JSONObject jsonObject = getObjectFromWebsite("https://api.mcsrvstat.us/2/" + args[0]);
                    String ip = jsonObject.getString("ip");
                    int port = jsonObject.getInt("port");
                    String hostname = jsonObject.getString("hostname");
                    String version = jsonObject.getString("version");

                    GetApiCommand.msg("", false);
                    GetApiCommand.msg("&dIP: &7" + ip, true);
                    GetApiCommand.msg("&dPort: &7" + port, true);
                    GetApiCommand.msg("&dHostname: &7" + hostname, true);
                    GetApiCommand.msg("&dVersion: &7" + version, true);
                    GetApiCommand.msg("", false);
                } catch (Exception e) { e.printStackTrace(); }
            })).start();
        }
    }

    @Override
    public String getName() {
        return "srvstat";
    }

    @Override
    public String getDescription() {
        return "Get info about server";
    }

    @Override
    public String getUsage() {
        return "srvstat <host &8(not required)&7>";
    }


    public static JSONObject getObjectFromWebsite(final String url) throws IOException, JSONException {

        try (InputStream inputStream = new URL(url).openStream()) {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            final String rawJsonText = read(bufferedReader);

            return new JSONObject(rawJsonText);
        }
    }

    private static String read(final Reader reader) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        int counter;
        while((counter = reader.read()) != -1) {
            stringBuilder.append((char)counter);
        }

        return stringBuilder.toString();
    }

}


