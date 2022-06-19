package neko.itskekoffcode.hclient.alt;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class AltHelper {
    private final Minecraft mc;


    public AltHelper(Minecraft mc) {
        this.mc = Minecraft.getMinecraft();
    }

    public static void setusername(String username) {
        Minecraft.getMinecraft().session = new Session(username, "", "", "mojang");
    }
}
