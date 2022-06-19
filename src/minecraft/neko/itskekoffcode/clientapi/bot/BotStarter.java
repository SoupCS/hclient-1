//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\�������\Desktop\mcp950\conf"!

package neko.itskekoffcode.clientapi.bot;

import com.mojang.authlib.GameProfile;
import neko.itskekoffcode.clientapi.bot.entity.BotPlayer;
import neko.itskekoffcode.clientapi.bot.network.BotLoginClient;
import neko.itskekoffcode.clientapi.bot.network.BotNetwork;
import neko.itskekoffcode.clientapi.utils.ChatHelper;
import neko.itskekoffcode.clientapi.utils.RandomUtils;
import neko.itskekoffcode.clientapi.utils.ThreadUtils;
import neko.itskekoffcode.hclient.bots.nicks.NicksParser;
import neko.itskekoffcode.hclient.bots.proxy.ProxyParser;
import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.hclient.commands.impl.bot.connection.BotsStopConnectCommand;
import neko.itskekoffcode.hclient.commands.impl.bot.fun.BotsFollowCommand;
import neko.itskekoffcode.hclient.commands.impl.bot.fun.BotsRandomRotationCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.init.Bootstrap;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.vecmath.Vector2f;
import java.net.InetAddress;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BotStarter {
	public static final Random random = new Random((long) (System.currentTimeMillis() * Math.random() + System.nanoTime()));
	public static ExecutorService exec = Executors.newCachedThreadPool();

	public static Minecraft mc = Minecraft.getInstance();
	public static int integer;
	private static int min = 0;


	@SuppressWarnings("InfiniteLoopStatement")
	public static void init() {
		Bootstrap.register();
		exec.submit(() -> {
			while (true) {
				for (Bot p : Bot.bots) {
					if (p.getBot().getHealth() <= 0.0F) p.getBot().respawnPlayer();
					p.getController().updateController();
					try {
						p.getWorld().tick();
						p.getWorld().updateEntities();
						if (BotsFollowCommand.Follow) {
							Vector2f angles = getBlockAngles(mc.player.posX, mc.player.posY + 1, mc.player.posZ, p.getBot().posX, p.getBot().posY, p.getBot().posZ);
							p.getBot().rotationYaw = normalizeYaw(angles.y);
							p.getBot().rotationPitch = normalizePitch(angles.x);
							p.getConnection().forward = true;
						}
						if (BotsRandomRotationCommand.Carusel) {
							p.getBot().rotationYaw += RandomUtils.nextInt(3, 20);
							p.getConnection().forward = true;
						}
					} catch (Exception ignored) {
						p.getNetManager().closeChannel();
						Bot.bots.remove(p);
					}
				}
				ThreadUtils.sleep(50L);
			}
		});
	}

	public static BotPlayer getBotByName(String name) {
		for(Bot bot : Bot.bots) { if (bot.getBot().getDisplayName().getUnformattedComponentText().equalsIgnoreCase(name)) { return bot.getBot(); } }
		return null;
	}

	public static Bot getRawBotByName(String name) {
		for(Bot bot : Bot.bots) { if (bot.getBot().getDisplayName().getUnformattedComponentText().equalsIgnoreCase(name)) { return bot; } }
		return null;
	}


	public static void test() {
		new Thread(() -> {
			GameProfile gameProfile;
			gameProfile = new GameProfile(UUID.randomUUID(), randomNickLetters(14, true));
			try {
				BotNetwork netManagerLogin = BotNetwork.createNetworkManagerAndConnect(InetAddress.getByName(GuiConnecting.ip), GuiConnecting.port, null);
				netManagerLogin.setNetHandler(new BotLoginClient(netManagerLogin));
				netManagerLogin.sendPacket(new C00Handshake(GuiConnecting.ip, GuiConnecting.port, EnumConnectionState.LOGIN));
				netManagerLogin.sendPacket(new CPacketLoginStart(gameProfile));
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
		}).start();
	}
	public static void run(String filename) {
		new Thread(() -> {
			if (BotsStopConnectCommand.connecting) {
				GameProfile gameProfile = null;
				if (filename.endsWith(".txt")) {
					gameProfile = new GameProfile(UUID.randomUUID(), NicksParser.nextNick());
				}
				ProxyParser.Proxy proxy = ProxyParser.getProxy();
				BotStarter.message("&7(&e" + gameProfile.getName() + "&7)&f Connecting using proxy: &7(&e" + proxy.getType() + " " + proxy.getAddress() + "&7)&r");
				try {
					BotNetwork netManagerLogin = BotNetwork.createNetworkManagerAndConnect(InetAddress.getByName(GuiConnecting.ip), GuiConnecting.port, proxy);
					System.out.println(InetAddress.getByName(GuiConnecting.ip) + ":" + GuiConnecting.port);
					netManagerLogin.setNetHandler(new BotLoginClient(netManagerLogin));
					netManagerLogin.sendPacket(new C00Handshake(GuiConnecting.ip, GuiConnecting.port, EnumConnectionState.LOGIN));
					netManagerLogin.sendPacket(new CPacketLoginStart(gameProfile));
				} catch (Exception ignored) { BotStarter.message("&7(&e"+ gameProfile.getName() + "&7)&f Disconnect:&5 "+ ignored.getMessage());
				}
			}
		}).start();
	}


	public static void component(ITextComponent component)
	{
		Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("").appendSibling(component));
	}
	public static void message(String message) {
		component(new TextComponentString(Command.fix(ChatHelper.chatPrefix + message)));
	}

	public static void click(int slot){
		for (Bot bot : Bot.bots) {
			bot.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
			bot.getController().windowClick(bot.getBot().openContainer.windowId, slot , 0, ClickType.PICKUP, bot.getBot());
		}
	}

	public static Vector2f getBlockAngles(double blockx, double blocky, double blockz, double entx, double enty, double entz) {

		double x = blockx - entx;
		double z = blockz - entz;
		double y = blocky - enty - 1f;

		double len = MathHelper.sqrt(x * x + z * z);
		float yaw = (float)Math.toDegrees(- Math.atan(x / z));
		float pitch = (float)(- Math.toDegrees(Math.atan(y / len)));

		double v = Math.toDegrees(Math.atan(z / x));
		if (z < 0.0 && x < 0.0) {
			yaw = (float)(90.0 + v);
		} else if (z < 0.0 && x > 0.0) {
			yaw = (float)(-90.0 + v);
		}
		return new Vector2f(pitch, normalizeYaw(yaw));
	}
	public static float normalizeYaw(float yaw) {
		while(yaw > 360.0f)
			yaw -= 360.0f;

		while(yaw < 0)
			yaw += 360.0f;
		return yaw;
	}
	public static float normalizePitch(float pitch) {
		while(pitch > 90.0f)
			pitch -= 90.0f;

		while(pitch < -90)
			pitch += 90.0f;

		return pitch;
	}



	public static String randomNick() {
		return String.valueOf(min = min + 1);
	}
	public static String randomNickLetters(int length, boolean numbers) {
		StringBuilder builder = new StringBuilder();
		char[] letters_numbers = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890".toCharArray();
		char[] letters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
		if (numbers) {
			 for (int i = 0; i < length; i++) {
				 if (random.nextBoolean()) builder.append(letters_numbers[random.nextInt(letters_numbers.length)]);
				 else builder.append(Character.toUpperCase(letters_numbers[random.nextInt(letters_numbers.length)]));
			 }
		} else {
			for (int i = 0; i < length; i++) {
				if (random.nextBoolean()) builder.append(letters[random.nextInt(letters.length)]);
				else builder.append(Character.toUpperCase(letters[random.nextInt(letters.length)]));
			}
		}
		return builder.toString();
	}
}
