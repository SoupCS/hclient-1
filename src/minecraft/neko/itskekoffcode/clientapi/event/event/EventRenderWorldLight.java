package neko.itskekoffcode.clientapi.event.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import neko.itskekoffcode.clientapi.event.Event;

public class EventRenderWorldLight extends Event {

    private final EnumSkyBlock enumSkyBlock;
    private final BlockPos pos;

    public EventRenderWorldLight(EnumSkyBlock enumSkyBlock, BlockPos pos) {
        this.enumSkyBlock = enumSkyBlock;
        this.pos = pos;
    }

    public EnumSkyBlock getEnumSkyBlock() {
        return enumSkyBlock;
    }

    public BlockPos getPos() {
        return pos;
    }
}