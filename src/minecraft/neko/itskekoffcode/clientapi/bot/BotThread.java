package neko.itskekoffcode.clientapi.bot;

public class BotThread implements Runnable
{
    private final String name;

    public BotThread(String filename) {
        this.name = filename;
    }

    @Override
    public void run() {
        BotStarter.run(name);
    }

}
