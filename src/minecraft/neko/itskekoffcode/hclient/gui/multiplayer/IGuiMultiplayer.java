package neko.itskekoffcode.hclient.gui.multiplayer;

import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.ServerPinger;

public interface IGuiMultiplayer {
    ServerPinger getOldServerPinger();

    ServerList getServerList();

    void setHoveringText(String var1);

    boolean canMoveUp(ServerListEntryNormal var1, int var2);

    boolean canMoveDown(ServerListEntryNormal var1, int var2);

    void selectServer(int var1);

    void connectToSelected();

    void moveServerUp(ServerListEntryNormal var1, int var2, boolean var3);

    void moveServerDown(ServerListEntryNormal var1, int var2, boolean var3);
}
