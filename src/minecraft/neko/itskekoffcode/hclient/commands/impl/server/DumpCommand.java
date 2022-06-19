package neko.itskekoffcode.hclient.commands.impl.server;

import com.mojang.realmsclient.gui.ChatFormatting;
import neko.itskekoffcode.hclient.HClient;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DumpCommand extends Command {
    public static Minecraft mc = Minecraft.getMinecraft();
    public NetHandlerPlayClient connection;

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 0) {
            ChatHelper.addChatMessage(ChatFormatting.AQUA + "Getting players");
            File dumpdir = new File(Minecraft.getMinecraft().mcDataDir + "/HClient" + "/dump");
            if (!dumpdir.exists()) {
                dumpdir.mkdir();
            }
            (new Thread(() -> {
                try {
                    connection = mc.player.connection;
                    List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(connection.getPlayerInfoMap());
                    File txt = new File(dumpdir + "/" + GuiConnecting.ip + "-players.txt");

                    if (txt.exists()) {
                        Pattern regex = Pattern.compile("^[\\w.]+");
                        Scanner scanner = new Scanner(txt);

                        List<String> list = new ArrayList<>();
                        while (scanner.hasNextLine()) {
                            list.add(scanner.nextLine());
                        }
                        for (NetworkPlayerInfo n : players) {
                            if (list.contains(n.getGameProfile().getName())) {
                                continue;
                            }
                            if (n.getGameProfile().getName().contains("&")) {
                                continue;
                            }
                            if (!n.getGameProfile().getName().matches(regex.pattern())) {
                                continue;
                            }
                            FileWriter writer = new FileWriter(txt, true);
                            BufferedWriter bufferWriter = new BufferedWriter(writer);
                            if (!n.getGameProfile().getName().equalsIgnoreCase(mc.player.getGameProfile().getName())) {
                                bufferWriter.write(n.getGameProfile().getName() + "\n");
                                bufferWriter.close();
                            }
                        }
                    }
                    ChatHelper.addChatMessage("Saved to /HClient/dump/" + txt.getName());
                } catch (Exception ignored) {
                    ChatHelper.addChatMessage("Error: " + ChatFormatting.RED + ignored.getMessage());
                }

            })).start();

        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("onetxt")) {
                ChatHelper.addChatMessage(ChatFormatting.AQUA + "Getting players");
                HClient.getInstance().getFileManager().addSubDirectory("/dumplist");
                File txt = new File(HClient.getInstance().getFileManager().getClientDirectory() + "/dumplist/listofplayers.txt");
                if (!txt.exists()) {
                    txt.createNewFile();
                }
                (new Thread(() -> {
                    try {
                        connection = mc.player.connection;
                        List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(connection.getPlayerInfoMap());

                        if (txt.exists()) {
                            Pattern regex = Pattern.compile("^[\\w.]+");
                            Scanner scanner = new Scanner(txt);

                            List<String> list = new ArrayList<>();
                            while (scanner.hasNextLine()) {
                                list.add(scanner.nextLine());
                            }
                            for (NetworkPlayerInfo n : players) {
                                if (list.contains(n.getGameProfile().getName())) {
                                    continue;
                                }
                                if (n.getGameProfile().getName().contains("&")) {
                                    continue;
                                }
                                if (!n.getGameProfile().getName().matches(regex.pattern())) {
                                    continue;
                                }
                                FileWriter writer = new FileWriter(txt, true);
                                BufferedWriter bufferWriter = new BufferedWriter(writer);
                                if (!n.getGameProfile().getName().equalsIgnoreCase(mc.player.getGameProfile().getName())) {
                                    bufferWriter.write(n.getGameProfile().getName() + "\n");
                                    bufferWriter.close();
                                }
                            }
                        }
                        ChatHelper.addChatMessage("Saved to /HClient/dumplist/" + txt.getName());
                    } catch (Exception ignored) {
                        ChatHelper.addChatMessage("Error: " + ChatFormatting.RED + ignored.getMessage());
                    }
                })).start();
            }
        }
    }


    @Override
    public String getName() {
        return "dump";
    }

    @Override
    public String getDescription() {
        return "Dump players to txt";
    }

    @Override
    public String getUsage() {
        return "dump <onetxt &8(not required)&7>";
    }
}