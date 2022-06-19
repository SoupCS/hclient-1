package neko.itskekoffcode.hclient.commands.impl.bot.test;

import neko.itskekoffcode.hclient.commands.Command;

public class testcommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        System.out.println("pablo ez");
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("test")) {
                testcommand.msg("it just works!", true);
            }
        }
    }


    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "test";
    }

    @Override
    public String getUsage() {
        return "bots test";
    }
}
