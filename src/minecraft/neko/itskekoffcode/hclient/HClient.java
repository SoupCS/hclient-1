package neko.itskekoffcode.hclient;


import java.awt.*;
import java.io.File;
import java.io.IOException;

import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.clientapi.event.EventManager;
import neko.itskekoffcode.hclient.bots.proxy.ProxyParser;
import neko.itskekoffcode.clientapi.utils.FileManager;
import neko.itskekoffcode.hclient.shader.ShaderShell;
import neko.itskekoffcode.clientapi.utils.HackPack;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import neko.itskekoffcode.hclient.commands.CommandManager;
import neko.itskekoffcode.hclient.helpers.Helper;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import viamcp.ViaMCP;


public class HClient extends GuiScreen implements Helper {
	public File dir;
	private static final HClient INSTANCE = new HClient();
	private static final Minecraft mc = Minecraft.getMinecraft();
	public CommandManager commandManager;
	public String name = "HClient", type = "Premium", version = "1.4", status = "BETA", build = "15052022";
	private FileManager fileManager;
	public static String API;
	public static ProxyParser scraper = new ProxyParser();
	private static HackPack hackpack;
	public EventManager eventManager;


    public void load() throws IOException {
		GL11.glColor4f(1, 1, 1, 1);
		Display.setTitle(name + " " + type);
		commandManager = new CommandManager();
		this.fileManager = new FileManager();
		//fileManager.addSubDirectory("/bots/captcha/api/");
		//try { API = Files.toString(new File(fileManager.getClientDirectory() + "/bots/captcha/api/key.txt"), Charsets.UTF_8); } catch (Exception ignored) {}
		BotStarter.init();
		ShaderShell.init();
		EventManager.register(this);
		CommandManager.commands();
		CommandManager.botcommands();
		this.eventManager = new EventManager();
		hackpack = new HackPack();
		try {
			ViaMCP.getInstance().start();
		}
		catch (Exception e) { e.printStackTrace(); }
		EventManager.register(this);
	}

	public FileManager getFileManager() {
		return fileManager;
	}
	public static EntityPlayerSP player() {
		return mc.player;
	}

	public void sendPacket(Packet p) {
		player().connection.sendPacket(p);
	}

	public static void sendPacketBypass(Packet p) {
		player().connection.sendPacket(p);
	}
	public static HackPack getHackPack() {
		return hackpack;
	}


	public static Color getClientColor() {
		return new Color(41, 41, 41, 255);
	}
	public static Color getClientColorBackground() { return new Color(22, 22, 22, 255); }


	public static HClient getInstance() {
		return INSTANCE;
	}
	public Minecraft getMC() {
		return mc;
	}

	private static Color primaryColor;
}
