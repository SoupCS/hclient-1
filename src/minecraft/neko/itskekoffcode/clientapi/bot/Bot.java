package neko.itskekoffcode.clientapi.bot;

import io.netty.util.internal.ConcurrentSet;
import neko.itskekoffcode.clientapi.bot.entity.BotController;
import neko.itskekoffcode.clientapi.bot.entity.BotPlayer;
import neko.itskekoffcode.clientapi.bot.network.BotNetwork;
import neko.itskekoffcode.clientapi.bot.network.BotPlayClient;
import neko.itskekoffcode.clientapi.bot.world.BotWorld;

import java.util.Set;

public class Bot {
    public static Set<Bot> bots = new ConcurrentSet<>();

    private final BotNetwork netManager;
    private final BotPlayClient connection;

    private final BotController controller;
    private final BotPlayer bot;

    private final BotWorld world;

    public Bot(BotNetwork netManager, BotPlayClient connection, BotController controller, BotPlayer bot, BotWorld world) {
        this.netManager = netManager;
        this.connection = connection;
        this.controller = controller;
        this.bot = bot;
        this.world = world;
    }

    public BotController getController() {
        return controller;
    }

    public BotNetwork getNetManager() {
        return netManager;
    }

    public BotPlayClient getConnection() {
        return connection;
    }

    public BotWorld getWorld() {
        return world;
    }

    public BotPlayer getBot() {
        return bot;
    }

}
