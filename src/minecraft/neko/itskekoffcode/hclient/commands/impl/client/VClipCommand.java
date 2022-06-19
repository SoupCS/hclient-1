package neko.itskekoffcode.hclient.commands.impl.client;

import neko.itskekoffcode.hclient.commands.Command;

import static neko.itskekoffcode.hclient.helpers.Helper.mc;

public class VClipCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        System.out.println("init");
        int posY = Integer.parseInt(args[0]);
        mc.player.setLocationAndAngles(mc.player.posX, posY + mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch);
        VClipCommand.msg("Teleported you " + (posY < 0 ? "down " : "up ") + Math.abs(posY) + " block" + (Math.abs(posY) == 1 ? "" : "s"), true);
    }

    @Override
    public String getName() {
        return "vclip";
    }

    @Override
    public String getDescription() {
        return "Teleports you up/down a specified amount";
    }

    @Override
    public String getUsage() {
        return "vclip <Amount>";
    }
}
