package neko.itskekoffcode.clientapi.bot.network;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import neko.itskekoffcode.clientapi.bot.Bot;
import neko.itskekoffcode.clientapi.bot.BotStarter;
import neko.itskekoffcode.clientapi.bot.entity.BotController;
import neko.itskekoffcode.clientapi.bot.entity.BotPlayer;
import neko.itskekoffcode.clientapi.bot.world.BotWorld;
import neko.itskekoffcode.clientapi.utils.ThreadUtils;
import neko.itskekoffcode.hclient.HClient;
import neko.itskekoffcode.hclient.bots.BasicColor;
import neko.itskekoffcode.hclient.commands.impl.bot.chat.BotsChatToggleCommand;
import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.player.inventory.ContainerLocalMenu;
import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.lwjgl.input.Keyboard;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import static neko.itskekoffcode.hclient.commands.impl.server.DumpCommand.mc;

public class BotPlayClient implements INetHandlerPlayClient {
    private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();

    private final BotNetwork netManager;
    private final GameProfile profile;

    public boolean forward = false;
    public boolean backward = false;
    public boolean left = false;
    public boolean right = false;

    private BotController botController;
    private BotPlayer bot;
    private Bot rbot;

    private BotWorld world;

    private boolean doneLoadingTerrain;

    private final float yaw = -1.1f;

    private boolean solved = false;

    /*private Thread msgSpam = new Thread(() -> {
        for (Map.Entry<UUID, NetworkbotInfo> pair : botInfoMap.entrySet()) {
            if (!bot.currentContainerName.toLowerCase().contains("нажмите"))
                sendPacket(new CPacketChatMessage(String.format("/w %s !Появилась новая программа для взлома доната на ЛЮБОМ СЕРВЕРЕ! > clck ru/YRD2e < Скачивай и взламывай ДОНАТ!", pair.getValue().getGameProfile().getName())));

            ThreadUtils.sleep(28000L);
        }
    });*/

    public final MovementInput movementInput = new MovementInput() {
        public void updatePlayerMoveState() {
            this.field_192832_b = 0.0F;
            this.moveStrafe = 0.0F;
            this.jump = false;
            this.sneak = false;
            forward = false;
            backward = false;
            left = false;
            right = false;

            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8) || Keyboard.isKeyDown(Keyboard.KEY_UP) || forward) { // вперед
                if (mc.currentScreen instanceof GuiChat) {}
                else { ++field_192832_b; }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5) || Keyboard.isKeyDown(Keyboard.KEY_DOWN) || backward) { // назад
                if (mc.currentScreen instanceof GuiChat) {}
                else { --field_192832_b; }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4) || Keyboard.isKeyDown(Keyboard.KEY_LEFT) || left) { // лево
                if (mc.currentScreen instanceof GuiChat) {}
                else { ++moveStrafe; }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || right) { // право
                if (mc.currentScreen instanceof GuiChat) {}
                else { --moveStrafe; }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1) ) { // прыжок
                if (mc.currentScreen instanceof GuiChat) {}
                else { jump = true; }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3) ) { // шифт
                if (mc.currentScreen instanceof GuiChat) {}
                else {
                    sneak = true;
                    this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
                    this.field_192832_b = (float) ((double) this.field_192832_b * 0.3D);
                }
            }

            if (yaw != -1.1f) {
                bot.rotationYaw = yaw;
                bot.rotationPitch = 0f;
            }
        }
    };




    public BotPlayClient(BotNetwork networkManagerIn, GameProfile profileIn) {
        this.netManager = networkManagerIn;
        this.profile = profileIn;
    }

    /**
     * Registers some server properties (gametype,hardcore-mode,terraintype,difficulty,bot limit), creates a new
     * WorldClient and sets the bot initial dimension
     */
    public void cleanup()
    {
        this.world = null;
    }



    /**
     * Registers some server properties (gametype,hardcore-mode,terraintype,difficulty,bot limit), creates a new
     * WorldClient and sets the bot initial dimension
     */
    public void handleJoinGame(SPacketJoinGame packetIn){
        this.botController = new BotController(this);
        this.world = new BotWorld(this, new WorldSettings(0L, packetIn.getGameType(), false, packetIn.isHardcoreMode(), packetIn.getWorldType()), packetIn.getDimension(), packetIn.getDifficulty());
        this.loadWorld(this.world);
        this.bot.dimension = packetIn.getDimension();
        this.bot.setEntityId(packetIn.getPlayerId());
        this.bot.setReducedDebug(packetIn.isReducedDebugInfo());
        this.botController.setGameType(packetIn.getGameType());
        this.sendPacket(new CPacketClientSettings("ru_ru", 2, EntityPlayer.EnumChatVisibility.FULL, false, 0, EnumHandSide.RIGHT));
        this.netManager.sendPacket(new CPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
        this.world.setBot(bot);
        BotStarter.message("&7(&e"+ bot.getName() + "&7)&a Connected!");

        Bot.bots.add(new Bot(netManager, this, botController, bot, world));
        this.netManager.connection = true;
    }



    private void loadWorld(BotWorld world) {
        this.world = world;
        this.bot = new BotPlayer(this);
        this.botController.flipPlayer(this.bot);
        this.bot.preparePlayerToSpawn();
        this.world.spawnEntityInWorld(this.bot);
        this.botController.setPlayerCapabilities(this.bot);
        this.bot.movementInput = movementInput;

        this.world.setBot(bot);
    }

    private void setDimensionAndSpawnPlayer(int dimension) {
        this.world.setInitialSpawnLocation();
        this.world.removeAllEntities();

        this.world.removeEntity(this.bot);
        this.bot = new BotPlayer(this);
        this.bot.getDataManager().setEntryValues(Objects.requireNonNull(this.bot.getDataManager().getAll()));
        this.bot.dimension = dimension;
        this.bot.preparePlayerToSpawn();
        this.world.spawnEntityInWorld(this.bot);
        this.botController.flipPlayer(this.bot);
        try {
            this.bot.movementInput = movementInput;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.botController.setPlayerCapabilities(this.bot);
        this.bot.setReducedDebug(this.bot.hasReducedDebug());

        this.world.setBot(bot);
    }
    /**
     * Spawns an instance of the objecttype indicated by the packet and sets its position and momentum
     */
    public void handleSpawnObject(SPacketSpawnObject packetIn)
    {
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        Entity entity = null;

        if (packetIn.getType() == 10)
        {
            entity = EntityMinecart.create(this.world, d0, d1, d2, EntityMinecart.Type.getById(packetIn.getData()));
        }
        else if (packetIn.getType() == 90)
        {
            Entity entity1 = this.world.getEntityByID(packetIn.getData());

            if (entity1 instanceof EntityPlayer)
            {
                entity = new EntityFishHook(this.world, (EntityPlayer)entity1, d0, d1, d2);
            }

            packetIn.setData(0);
        }
        else if (packetIn.getType() == 60)
        {
            entity = new EntityTippedArrow(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 91)
        {
            entity = new EntitySpectralArrow(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 61)
        {
            entity = new EntitySnowball(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 68)
        {
            entity = new EntityLlamaSpit(this.world, d0, d1, d2, (double)packetIn.getSpeedX() / 8000.0D, (double)packetIn.getSpeedY() / 8000.0D, (double)packetIn.getSpeedZ() / 8000.0D);
        }
        else if (packetIn.getType() == 71)
        {
            entity = new EntityItemFrame(this.world, new BlockPos(d0, d1, d2), EnumFacing.getHorizontal(packetIn.getData()));
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 77)
        {
            entity = new EntityLeashKnot(this.world, new BlockPos(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2)));
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 65)
        {
            entity = new EntityEnderPearl(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 72)
        {
            entity = new EntityEnderEye(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 76)
        {
            entity = new EntityFireworkRocket(this.world, d0, d1, d2, ItemStack.field_190927_a);
        }
        else if (packetIn.getType() == 63)
        {
            entity = new EntityLargeFireball(this.world, d0, d1, d2, (double)packetIn.getSpeedX() / 8000.0D, (double)packetIn.getSpeedY() / 8000.0D, (double)packetIn.getSpeedZ() / 8000.0D);
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 93)
        {
            entity = new EntityDragonFireball(this.world, d0, d1, d2, (double)packetIn.getSpeedX() / 8000.0D, (double)packetIn.getSpeedY() / 8000.0D, (double)packetIn.getSpeedZ() / 8000.0D);
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 64)
        {
            entity = new EntitySmallFireball(this.world, d0, d1, d2, (double)packetIn.getSpeedX() / 8000.0D, (double)packetIn.getSpeedY() / 8000.0D, (double)packetIn.getSpeedZ() / 8000.0D);
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 66)
        {
            entity = new EntityWitherSkull(this.world, d0, d1, d2, (double)packetIn.getSpeedX() / 8000.0D, (double)packetIn.getSpeedY() / 8000.0D, (double)packetIn.getSpeedZ() / 8000.0D);
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 67)
        {
            entity = new EntityShulkerBullet(this.world, d0, d1, d2, (double)packetIn.getSpeedX() / 8000.0D, (double)packetIn.getSpeedY() / 8000.0D, (double)packetIn.getSpeedZ() / 8000.0D);
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 62)
        {
            entity = new EntityEgg(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 79)
        {
            entity = new EntityEvokerFangs(this.world, d0, d1, d2, 0.0F, 0, null);
        }
        else if (packetIn.getType() == 73)
        {
            entity = new EntityPotion(this.world, d0, d1, d2, ItemStack.field_190927_a);
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 75)
        {
            entity = new EntityExpBottle(this.world, d0, d1, d2);
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 1)
        {
            entity = new EntityBoat(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 50)
        {
            entity = new EntityTNTPrimed(this.world, d0, d1, d2, null);
        }
        else if (packetIn.getType() == 78)
        {
            entity = new EntityArmorStand(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 51)
        {
            entity = new EntityEnderCrystal(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 2)
        {
            entity = new EntityItem(this.world, d0, d1, d2);
        }
        else if (packetIn.getType() == 70)
        {
            entity = new EntityFallingBlock(this.world, d0, d1, d2, Block.getStateById(packetIn.getData() & 65535));
            packetIn.setData(0);
        }
        else if (packetIn.getType() == 3)
        {
            entity = new EntityAreaEffectCloud(this.world, d0, d1, d2);
        }

        if (entity != null)
        {
            EntityTracker.updateServerPosition(entity, d0, d1, d2);
            entity.rotationPitch = (float)(packetIn.getPitch() * 360) / 256.0F;
            entity.rotationYaw = (float)(packetIn.getYaw() * 360) / 256.0F;
            Entity[] aentity = entity.getParts();

            if (aentity != null)
            {
                int i = packetIn.getEntityID() - entity.getEntityId();

                for (Entity entity2 : aentity)
                {
                    entity2.setEntityId(entity2.getEntityId() + i);
                }
            }

            entity.setEntityId(packetIn.getEntityID());
            entity.setUniqueId(packetIn.getUniqueId());
            this.world.addEntityToWorld(packetIn.getEntityID(), entity);

            if (packetIn.getData() > 0)
            {
                if (packetIn.getType() == 60 || packetIn.getType() == 91)
                {
                    Entity entity3 = this.world.getEntityByID(packetIn.getData() - 1);

                    if (entity3 instanceof EntityLivingBase && entity instanceof EntityArrow)
                    {
                        ((EntityArrow)entity).shootingEntity = entity3;
                    }
                }

                entity.setVelocity((double)packetIn.getSpeedX() / 8000.0D, (double)packetIn.getSpeedY() / 8000.0D, (double)packetIn.getSpeedZ() / 8000.0D);
            }
        }
    }

    /**
     * Spawns an experience orb and sets its value (amount of XP)
     */
    public void handleSpawnExperienceOrb(SPacketSpawnExperienceOrb packetIn)
    {
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        Entity entity = new EntityXPOrb(this.world, d0, d1, d2, packetIn.getXPValue());
        EntityTracker.updateServerPosition(entity, d0, d1, d2);
        entity.rotationYaw = 0.0F;
        entity.rotationPitch = 0.0F;
        entity.setEntityId(packetIn.getEntityID());
        this.world.addEntityToWorld(packetIn.getEntityID(), entity);
    }

    /**
     * Handles globally visible entities. Used in vanilla for lightning bolts
     */
    public void handleSpawnGlobalEntity(SPacketSpawnGlobalEntity packetIn)
    {
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        Entity entity = null;

        if (packetIn.getType() == 1)
        {
            entity = new EntityLightningBolt(this.world, d0, d1, d2, false);
        }

        if (entity != null)
        {
            EntityTracker.updateServerPosition(entity, d0, d1, d2);
            entity.rotationYaw = 0.0F;
            entity.rotationPitch = 0.0F;
            entity.setEntityId(packetIn.getEntityId());
            this.world.addWeatherEffect(entity);
        }
    }

    /**
     * Handles the spawning of a painting object
     */
    public void handleSpawnPainting(SPacketSpawnPainting packetIn)
    {
        EntityPainting entitypainting = new EntityPainting(this.world, packetIn.getPosition(), packetIn.getFacing(), packetIn.getTitle());
        entitypainting.setUniqueId(packetIn.getUniqueId());
        this.world.addEntityToWorld(packetIn.getEntityID(), entitypainting);
    }

    /**
     * Sets the velocity of the specified entity to the specified value
     */
    public void handleEntityVelocity(SPacketEntityVelocity packetIn)
    {
        Entity entity = this.world.getEntityByID(packetIn.getEntityID());

        if (entity != null)
        {
            entity.setVelocity((double)packetIn.getMotionX() / 8000.0D, (double)packetIn.getMotionY() / 8000.0D, (double)packetIn.getMotionZ() / 8000.0D);
        }
    }

    /**
     * Invoked when the server registers new proximate objects in your watchlist or when objects in your watchlist have
     * changed -> Registers any changes locally
     */
    public void handleEntityMetadata(SPacketEntityMetadata packetIn)
    {
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity != null && packetIn.getDataManagerEntries() != null)
        {
            entity.getDataManager().setEntryValues(packetIn.getDataManagerEntries());
        }
    }

    /**
     * Handles the creation of a nearby bot entity, sets the position and held item
     */
    public void handleSpawnPlayer(SPacketSpawnPlayer packetIn)
    {
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        float f = (float)(packetIn.getYaw() * 360) / 256.0F;
        float f1 = (float)(packetIn.getPitch() * 360) / 256.0F;
        EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(this.world, this.getPlayerInfo(packetIn.getUniqueId()).getGameProfile());
        entityotherplayermp.prevPosX = d0;
        entityotherplayermp.lastTickPosX = d0;
        entityotherplayermp.prevPosY = d1;
        entityotherplayermp.lastTickPosY = d1;
        entityotherplayermp.prevPosZ = d2;
        entityotherplayermp.lastTickPosZ = d2;
        EntityTracker.updateServerPosition(entityotherplayermp, d0, d1, d2);
        entityotherplayermp.setPositionAndRotation(d0, d1, d2, f, f1);
        this.world.addEntityToWorld(packetIn.getEntityID(), entityotherplayermp);
        List<EntityDataManager.DataEntry<?>> list = packetIn.getDataManagerEntries();

        if (list != null) {
            entityotherplayermp.getDataManager().setEntryValues(list);
        }
    }

    /**
     * Updates an entity's position and rotation as specified by the packet
     */
    public void handleEntityTeleport(SPacketEntityTeleport packetIn)
    {
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity != null)
        {
            double d0 = packetIn.getX();
            double d1 = packetIn.getY();
            double d2 = packetIn.getZ();
            EntityTracker.updateServerPosition(entity, d0, d1, d2);

            if (!entity.canPassengerSteer())
            {
                float f = (float)(packetIn.getYaw() * 360) / 256.0F;
                float f1 = (float)(packetIn.getPitch() * 360) / 256.0F;

                if (Math.abs(entity.posX - d0) < 0.03125D && Math.abs(entity.posY - d1) < 0.015625D && Math.abs(entity.posZ - d2) < 0.03125D)
                {
                    entity.setPositionAndRotationDirect(entity.posX, entity.posY, entity.posZ, f, f1, 0, true);
                }
                else
                {
                    entity.setPositionAndRotationDirect(d0, d1, d2, f, f1, 3, true);
                }

                entity.onGround = packetIn.getOnGround();
            }
        }
    }

    /**
     * Updates which hotbar slot of the bot is currently selected
     */
    public void handleHeldItemChange(SPacketHeldItemChange packetIn)
    {


        if (InventoryPlayer.isHotbar(packetIn.getHeldItemHotbarIndex())) {
            this.bot.inventory.currentItem = packetIn.getHeldItemHotbarIndex();
        }
    }

    /**
     * Updates the specified entity's position by the specified relative moment and absolute rotation. Note that
     * subclassing of the packet allows for the specification of a subset of this data (e.g. only rel. position, abs.
     * rotation or both).
     */
    public void handleEntityMovement(SPacketEntity packetIn)
    {
        Entity entity = packetIn.getEntity(this.world);

        if (entity != null)
        {
            entity.serverPosX += packetIn.getX();
            entity.serverPosY += packetIn.getY();
            entity.serverPosZ += packetIn.getZ();
            double d0 = (double)entity.serverPosX / 4096.0D;
            double d1 = (double)entity.serverPosY / 4096.0D;
            double d2 = (double)entity.serverPosZ / 4096.0D;

            if (!entity.canPassengerSteer())
            {
                float f = packetIn.isRotating() ? (float)(packetIn.getYaw() * 360) / 256.0F : entity.rotationYaw;
                float f1 = packetIn.isRotating() ? (float)(packetIn.getPitch() * 360) / 256.0F : entity.rotationPitch;
                entity.setPositionAndRotationDirect(d0, d1, d2, f, f1, 3, false);
                entity.onGround = packetIn.getOnGround();
            }
        }
    }

    /**
     * Updates the direction in which the specified entity is looking, normally this head rotation is independent of the
     * rotation of the entity itself
     */
    public void handleEntityHeadLook(SPacketEntityHeadLook packetIn)
    {
        Entity entity = packetIn.getEntity(this.world);

        if (entity != null)
        {
            float f = (float)(packetIn.getYaw() * 360) / 256.0F;
            entity.setRotationYawHead(f);
        }
    }

    /**
     * Locally eliminates the entities. Invoked by the server when the items are in fact destroyed, or the bot is no
     * longer registered as required to monitor them. The latter  happens when distance between the bot and item
     * increases beyond a certain treshold (typically the viewing distance)
     */
    public void handleDestroyEntities(SPacketDestroyEntities packetIn)
    {

        for (int i = 0; i < packetIn.getEntityIDs().length; ++i)
        {
            this.world.removeEntityFromWorld(packetIn.getEntityIDs()[i]);
        }
    }

    public void handlePlayerPosLook(SPacketPlayerPosLook packetIn)
    {
        EntityPlayer EntityPlayer = this.bot;
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        float f = packetIn.getYaw();
        float f1 = packetIn.getPitch();

        if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X))
        {
            d0 += EntityPlayer.posX;
        }
        else
        {
            EntityPlayer.motionX = 0.0D;
        }

        if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y))
        {
            d1 += EntityPlayer.posY;
        }
        else
        {
            EntityPlayer.motionY = 0.0D;
        }

        if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z))
        {
            d2 += EntityPlayer.posZ;
        }
        else
        {
            EntityPlayer.motionZ = 0.0D;
        }

        if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X_ROT))
        {
            f1 += EntityPlayer.rotationPitch;
        }

        if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT))
        {
            f += EntityPlayer.rotationYaw;
        }

        EntityPlayer.setPositionAndRotation(d0, d1, d2, f, f1);
        this.netManager.sendPacket(new CPacketConfirmTeleport(packetIn.getTeleportId()));
        this.netManager.sendPacket(new CPacketPlayer.PositionRotation(EntityPlayer.posX, EntityPlayer.getEntityBoundingBox().minY, EntityPlayer.posZ, EntityPlayer.rotationYaw, EntityPlayer.rotationPitch, false));


        if (!this.doneLoadingTerrain) {
            this.bot.prevPosX = this.bot.posX;
            this.bot.prevPosY = this.bot.posY;
            this.bot.prevPosZ = this.bot.posZ;
            this.doneLoadingTerrain = true;
        }
    }

    /**
     * Received from the servers botManager if between 1 and 64 blocks in a chunk are changed. If only one block
     * requires an update, the server sends S23PacketBlockChange and if 64 or more blocks are changed, the server sends
     * S21PacketChunkData
     */
    public void handleMultiBlockChange(SPacketMultiBlockChange packetIn)
    {

        for (SPacketMultiBlockChange.BlockUpdateData spacketmultiblockchange$blockupdatedata : packetIn.getChangedBlocks())
        {
            this.world.invalidateRegionAndSetBlock(spacketmultiblockchange$blockupdatedata.getPos(), spacketmultiblockchange$blockupdatedata.getBlockState());
        }
    }

    /**
     * Updates the specified chunk with the supplied data, marks it for re-rendering and lighting recalculation
     */
    public void handleChunkData(SPacketChunkData packetIn)
    {

        if (packetIn.doChunkLoad())
        {
            this.world.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), true);
        }

        this.world.invalidateBlockReceiveRegion(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
        Chunk chunk = this.world.getChunkFromChunkCoords(packetIn.getChunkX(), packetIn.getChunkZ());
        chunk.fillChunk(packetIn.getReadBuffer(), packetIn.getExtractedSize(), packetIn.doChunkLoad());
        this.world.markBlockRangeForRenderUpdate(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);

        if (!packetIn.doChunkLoad() || !(this.world.provider instanceof WorldProviderSurface))
        {
            chunk.resetRelightChecks();
        }

        for (NBTTagCompound nbttagcompound : packetIn.getTileEntityTags())
        {
            BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
            TileEntity tileentity = this.world.getTileEntity(blockpos);

            if (tileentity != null)
            {
                tileentity.readFromNBT(nbttagcompound);
            }
        }
    }

    public void processChunkUnload(SPacketUnloadChunk packetIn)
    {
        this.world.doPreChunk(packetIn.getX(), packetIn.getZ(), false);
    }

    /**
     * Updates the block and metadata and generates a blockupdate (and notify the clients)
     */
    public void handleBlockChange(SPacketBlockChange packetIn)
    {
        this.world.invalidateRegionAndSetBlock(packetIn.getBlockPosition(), packetIn.getBlockState());

    }

    /**
     * Closes the network channel
     */
    private Bot getBotAboba() {
        Bot bot1 = null;
        for(Bot bot2 : Bot.bots) {
            if (bot2.getBot().getDisplayName().getUnformattedComponentText().equalsIgnoreCase(bot.getDisplayName().getUnformattedText())) {
                bot1 = bot2;
            }
        }
        return bot1;
    }
    public void handleDisconnect(SPacketDisconnect packetIn)
    {
        Bot.bots.remove(getBotAboba());
        BotStarter.message("&7(&e"+ bot.getDisplayName().getFormattedText() + "&7)&f Disconnected: "+ packetIn.getReason().getFormattedText());
        this.netManager.closeChannel();
    }

    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    public void onDisconnect(ITextComponent reason)
    {
        Bot.bots.remove(getBotAboba());
        BotStarter.message("&7(&e"+ bot.getDisplayName().getFormattedText() + "&7)&f Disconnected: "+ reason);
        this.netManager.closeChannel();
    }

    public void sendPacket(Packet<?> packetIn)
    {
        this.netManager.sendPacket(packetIn);
    }

    public void handleCollectItem(SPacketCollectItem packetIn)
    {

    }

    /**
     * Prints a chatmessage in the chat GUI
     */
    public void handleChat(SPacketChat packetIn) {
        String message = packetIn.getChatComponent().getFormattedText().replaceAll("&.?", "");
        if (BotsChatToggleCommand.BotChat) {
            BotStarter.message("&7(&e" + bot.getDisplayName().getFormattedText() + "&7)&f Chat: " + packetIn.getChatComponent().getFormattedText());
        }
        if (message.contains("/reg") || message.contains("/l") || message.contains("/register") || message.contains("/login")) {
            //captcha = false;
            bot.sendChatMessage(String.format("/register %s %s", "4321qq4321", "4321qq4321"));
            bot.sendChatMessage(String.format("/login %s", "4321qq4321"));
            //Configs.AutoLogin = false;
                /*for (final Bot p : Bot.bots) {
                    p.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    p.getController().windowClick(p.getBot().openContainer.windowId, 21, 0, ClickType.PICKUP, p.getBot());
                }*/

        }
    }

    /**
     * Renders a specified animation: Waking up a bot, a living entity swinging its currently held item, being hurt
     * or receiving a critical hit by normal or magical means
     */
    public void handleAnimation(SPacketAnimation packetIn)
    {

    }

    /**
     * Retrieves the bot identified by the packet, puts him to sleep if possible (and flags whether all bots are
     * asleep)
     */
    public void handleUseBed(SPacketUseBed packetIn)
    {
        packetIn.getPlayer(this.world).trySleep(packetIn.getBedPosition());
    }

    /**
     * Spawns the mob entity at the specified location, with the specified rotation, momentum and type. Updates the
     * entities Datawatchers with the entity metadata specified in the packet
     */
    public void handleSpawnMob(SPacketSpawnMob packetIn)
    {
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        float f = (float)(packetIn.getYaw() * 360) / 256.0F;
        float f1 = (float)(packetIn.getPitch() * 360) / 256.0F;
        EntityLivingBase entitylivingbase = (EntityLivingBase)EntityList.createEntityByID(packetIn.getEntityType(), this.world);

        if (entitylivingbase != null)
        {
            EntityTracker.updateServerPosition(entitylivingbase, d0, d1, d2);
            entitylivingbase.renderYawOffset = (float)(packetIn.getHeadPitch() * 360) / 256.0F;
            entitylivingbase.rotationYawHead = (float)(packetIn.getHeadPitch() * 360) / 256.0F;
            Entity[] aentity = entitylivingbase.getParts();

            if (aentity != null)
            {
                int i = packetIn.getEntityID() - entitylivingbase.getEntityId();

                for (Entity entity : aentity)
                {
                    entity.setEntityId(entity.getEntityId() + i);
                }
            }

            entitylivingbase.setEntityId(packetIn.getEntityID());
            entitylivingbase.setUniqueId(packetIn.getUniqueId());
            entitylivingbase.setPositionAndRotation(d0, d1, d2, f, f1);
            entitylivingbase.motionX = (float)packetIn.getVelocityX() / 8000.0F;
            entitylivingbase.motionY = (float)packetIn.getVelocityY() / 8000.0F;
            entitylivingbase.motionZ = (float)packetIn.getVelocityZ() / 8000.0F;
            this.world.addEntityToWorld(packetIn.getEntityID(), entitylivingbase);
            List < EntityDataManager.DataEntry<? >> list = packetIn.getDataManagerEntries();

            if (list != null)
            {
                entitylivingbase.getDataManager().setEntryValues(list);
            }
        }
        else
        {
        }
    }

    public void handleTimeUpdate(SPacketTimeUpdate packetIn)
    {
        this.world.setTotalWorldTime(packetIn.getTotalWorldTime());
        this.world.setWorldTime(packetIn.getWorldTime());
    }

    public void handleSpawnPosition(SPacketSpawnPosition packetIn)
    {
        this.bot.setSpawnPoint(packetIn.getSpawnPos(), true);
        this.world.getWorldInfo().setSpawn(packetIn.getSpawnPos());
    }

    public void handleSetPassengers(SPacketSetPassengers packetIn) {
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity != null) {
            entity.removePassengers();

            for (int i : packetIn.getPassengerIds()) {
                Entity entity1 = this.world.getEntityByID(i);

                if (entity1 != null) {
                    entity1.startRiding(entity, true);
                }
            }
        }
    }

    public void handleEntityAttach(SPacketEntityAttach packetIn)
    {
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());
        Entity entity1 = this.world.getEntityByID(packetIn.getVehicleEntityId());

        if (entity instanceof EntityLiving)
        {
            if (entity1 != null)
            {
                ((EntityLiving)entity).setLeashedToEntity(entity1, false);
            }
            else
            {
                ((EntityLiving)entity).clearLeashed(false, false);
            }
        }
    }

    /**
     * Invokes the entities' handleUpdateHealth method which is implemented in LivingBase (hurt/death),
     * MinecartMobSpawner (spawn delay), FireworkRocket & MinecartTNT (explosion), IronGolem (throwing,...), Witch
     * (spawn particles), Zombie (villager transformation), Animal (breeding mode particles), Horse (breeding/smoke
     * particles), Sheep (...), Tameable (...), Villager (particles for breeding mode, angry and happy), Wolf (...)
     */
    public void handleEntityStatus(SPacketEntityStatus packetIn) {
        Entity entity = packetIn.getEntity(this.world);

        if (entity != null) {
            if (packetIn.getOpCode() != 21 && packetIn.getOpCode() != 35) {
                entity.handleStatusUpdate(packetIn.getOpCode());
            }
        }
    }

    private long currentTimeMillis()
    {
        return System.nanoTime() / 1000000L;
    }


    public void handleUpdateHealth(SPacketUpdateHealth packetIn) {
        this.bot.setPlayerSPHealth(packetIn.getHealth());
        this.bot.getFoodStats().setFoodLevel(packetIn.getFoodLevel());
        this.bot.getFoodStats().setFoodSaturationLevel(packetIn.getSaturationLevel());
    }

    public void handleSetExperience(SPacketSetExperience packetIn)
    {
        this.bot.setXPStats(packetIn.getExperienceBar(), packetIn.getTotalExperience(), packetIn.getLevel());
    }

    public void handleRespawn(SPacketRespawn packetIn)
    {
        Bot.bots.removeIf(bot -> bot.getConnection().equals(this));

        int entityId = this.bot.getEntityId();
        String serverBrand = this.bot.getServerBrand();


        if (packetIn.getDimensionID() != this.bot.dimension) {
            this.doneLoadingTerrain = false;
            this.world = new BotWorld(this, new WorldSettings(0L, packetIn.getGameType(), false, this.world.getWorldInfo().isHardcoreModeEnabled(), packetIn.getWorldType()), packetIn.getDimensionID(), packetIn.getDifficulty());

            this.loadWorld(this.world);

            this.bot.dimension = packetIn.getDimensionID();
        }

        this.setDimensionAndSpawnPlayer(packetIn.getDimensionID());
        this.botController.setGameType(packetIn.getGameType());

        this.bot.setEntityId(entityId);
        this.bot.setServerBrand(serverBrand);

        this.world.setBot(bot);

        Bot.bots.add(new Bot(netManager, this, botController, bot, world));







    }

    /**
     * Initiates a new explosion (sound, particles, drop spawn) for the affected blocks indicated by the packet.
     */
    public void handleExplosion(SPacketExplosion packetIn)
    {
        Explosion explosion = new Explosion(this.world, null, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
        explosion.doExplosionB(true);
        this.bot.motionX += packetIn.getMotionX();
        this.bot.motionY += packetIn.getMotionY();
        this.bot.motionZ += packetIn.getMotionZ();
    }

    /**
     * Displays a GUI by ID. In order starting from id 0: Chest, Workbench, Furnace, Dispenser, Enchanting table,
     * Brewing stand, Villager merchant, Beacon, Anvil, Hopper, Dropper, Horse
     */
    public void handleOpenWindow(SPacketOpenWindow packetIn)
    {

        if ("minecraft:container".equals(packetIn.getGuiId()))
        {
            bot.displayGUIChest(new InventoryBasic(packetIn.getWindowTitle(), packetIn.getSlotCount()));
            bot.openContainer.windowId = packetIn.getWindowId();
        }
        else if ("minecraft:villager".equals(packetIn.getGuiId()))
        {
            bot.displayVillagerTradeGui(new NpcMerchant(bot, packetIn.getWindowTitle()));
            bot.openContainer.windowId = packetIn.getWindowId();
        }
        else if ("EntityHorse".equals(packetIn.getGuiId()))
        {
            Entity entity = this.world.getEntityByID(packetIn.getEntityId());

            if (entity instanceof AbstractHorse)
            {
                //bot.openHorseInventory((AbstractHorse)entity, new ContainerHorseChest(packetIn.getWindowTitle(), packetIn.getSlotCount()));
                bot.openContainer.windowId = packetIn.getWindowId();
            }
        }
        else if (!packetIn.hasSlots())
        {
            bot.displayGui(new LocalBlockIntercommunication(packetIn.getGuiId(), packetIn.getWindowTitle()));
            bot.openContainer.windowId = packetIn.getWindowId();
        }
        else
        {
            IInventory iinventory = new ContainerLocalMenu(packetIn.getGuiId(), packetIn.getWindowTitle(), packetIn.getSlotCount());
            bot.displayGUIChest(iinventory);
            bot.openContainer.windowId = packetIn.getWindowId();
        }
    }

    /**
     * Handles pickin up an ItemStack or dropping one in your inventory or an open (non-creative) container
     */
    public void handleSetSlot(SPacketSetSlot packetIn) {
        //System.out.println(packetIn.getSlot());
        //System.out.println(packetIn.getWindowId());
        EntityPlayer entityplayer = this.bot;
        ItemStack itemstack = packetIn.getStack();
        int i = packetIn.getSlot();

        if (packetIn.getWindowId() == -1) {
            entityplayer.inventory.setItemStack(itemstack);
        } else if (packetIn.getWindowId() == -2) {
            entityplayer.inventory.setInventorySlotContents(i, itemstack);
        } else {
            boolean flag = false;

            if (Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) {
                GuiContainerCreative guicontainercreative = (GuiContainerCreative) Minecraft.getMinecraft().currentScreen;
                flag = guicontainercreative.getSelectedTabIndex() != CreativeTabs.INVENTORY.getTabIndex();
            }

            if (packetIn.getWindowId() == 0 && packetIn.getSlot() >= 36 && i < 45) {

                if (!itemstack.func_190926_b())
                {
                    ItemStack itemstack1 = entityplayer.inventoryContainer.getSlot(i).getStack();

                    if (itemstack1.func_190926_b() || itemstack1.func_190916_E() < itemstack.func_190916_E())
                    {
                        itemstack.func_190915_d(5);
                    }
                }

                entityplayer.inventoryContainer.putStackInSlot(i, itemstack);
            } else if (packetIn.getWindowId() == entityplayer.openContainer.windowId && (packetIn.getWindowId() != 0 || !flag)) {
                entityplayer.openContainer.putStackInSlot(i, itemstack);
            }
        }
    }

    /**
     * Verifies that the server and client are synchronized with respect to the inventory/container opened by the bot
     * and confirms if it is the case.
     */
    public void handleConfirmTransaction(SPacketConfirmTransaction packetIn)
    {
        Container container = null;
        EntityPlayer EntityPlayer = this.bot;

        if (packetIn.getWindowId() == 0)
        {
            container = EntityPlayer.inventoryContainer;
        }
        else if (packetIn.getWindowId() == EntityPlayer.openContainer.windowId)
        {
            container = EntityPlayer.openContainer;
        }

        if (container != null && !packetIn.wasAccepted())
        {
            this.sendPacket(new CPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
        }
    }

    /**
     * Handles the placement of a specified ItemStack in a specified container/inventory slot
     */
    public void handleWindowItems(SPacketWindowItems packetIn)
    {
        EntityPlayer EntityPlayer = this.bot;

        if (packetIn.getWindowId() == 0)
        {
            EntityPlayer.inventoryContainer.func_190896_a(packetIn.getItemStacks());
        }
        else if (packetIn.getWindowId() == EntityPlayer.openContainer.windowId)
        {
            EntityPlayer.openContainer.func_190896_a(packetIn.getItemStacks());
        }
    }

    /**
     * Creates a sign in the specified location if it didn't exist and opens the GUI to edit its text
     */
    public void handleSignEditorOpen(SPacketSignEditorOpen packetIn)
    {
        TileEntity tileentity = this.world.getTileEntity(packetIn.getSignPosition());

        if (!(tileentity instanceof TileEntitySign))
        {
            tileentity = new TileEntitySign();
            tileentity.setWorldObj(this.world);
            tileentity.setPos(packetIn.getSignPosition());
        }

        this.bot.openEditSign((TileEntitySign)tileentity);
    }

    /**
     * Updates the NBTTagCompound metadata of instances of the following entitytypes: Mob spawners, command blocks,
     * beacons, skulls, flowerpot
     */
    public void handleUpdateTileEntity(SPacketUpdateTileEntity packetIn) {
        if (this.world.isBlockLoaded(packetIn.getPos())) {
            TileEntity tileentity = this.world.getTileEntity(packetIn.getPos());
            int i = packetIn.getTileEntityType();
            boolean flag = i == 2 && tileentity instanceof TileEntityCommandBlock;

            if (i == 1 && tileentity instanceof TileEntityMobSpawner || flag || i == 3 && tileentity instanceof TileEntityBeacon || i == 4 && tileentity instanceof TileEntitySkull || i == 5 && tileentity instanceof TileEntityFlowerPot || i == 6 && tileentity instanceof TileEntityBanner || i == 7 && tileentity instanceof TileEntityStructure || i == 8 && tileentity instanceof TileEntityEndGateway || i == 9 && tileentity instanceof TileEntitySign || i == 10 && tileentity instanceof TileEntityShulkerBox || i == 11 && tileentity instanceof TileEntityBed) {

                tileentity.readFromNBT(packetIn.getNbtCompound());
            }
        }
    }

    /**
     * Sets the progressbar of the opened window to the specified value
     */
    public void handleWindowProperty(SPacketWindowProperty packetIn)
    {
        EntityPlayer EntityPlayer = this.bot;

        if (EntityPlayer.openContainer != null && EntityPlayer.openContainer.windowId == packetIn.getWindowId())
        {
            EntityPlayer.openContainer.updateProgressBar(packetIn.getProperty(), packetIn.getValue());
        }
    }

    public void handleEntityEquipment(SPacketEntityEquipment packetIn)
    {
        Entity entity = this.world.getEntityByID(packetIn.getEntityID());

        if (entity != null)
        {
            entity.setItemStackToSlot(packetIn.getEquipmentSlot(), packetIn.getItemStack());
        }
    }

    /**
     * Resets the ItemStack held in hand and closes the window that is opened
     */
    public void handleCloseWindow(SPacketCloseWindow packetIn)
    {
        this.bot.closeScreenAndDropStack();
    }

    /**
     * Triggers Block.onBlockEventReceived, which is implemented in BlockPistonBase for extension/retraction, BlockNote
     * for setting the instrument (including audiovisual feedback) and in BlockContainer to set the number of bots
     * accessing a (Ender)Chest
     */
    public void handleBlockAction(SPacketBlockAction packetIn)
    {
        this.world.addBlockEvent(packetIn.getBlockPosition(), packetIn.getBlockType(), packetIn.getData1(), packetIn.getData2());
    }

    /**
     * Updates all registered IWorldAccess instances with destroyBlockInWorldPartially
     */
    public void handleBlockBreakAnim(SPacketBlockBreakAnim packetIn)
    {
        this.world.sendBlockBreakProgress(packetIn.getBreakerId(), packetIn.getPosition(), packetIn.getProgress());
    }

    public void handleChangeGameState(SPacketChangeGameState packetIn)
    {
        EntityPlayer entityplayer = this.bot;
        int i = packetIn.getGameState();
        float f = packetIn.getValue();
        int j = MathHelper.floor(f + 0.5F);

        if (i >= 0 && i < SPacketChangeGameState.MESSAGE_NAMES.length && SPacketChangeGameState.MESSAGE_NAMES[i] != null) {
            entityplayer.addChatComponentMessage(new TextComponentTranslation(SPacketChangeGameState.MESSAGE_NAMES[i]), false);
        }

        if (i == 1) {
            this.world.getWorldInfo().setRaining(true);
            this.world.setRainStrength(0.0F);
        } else if (i == 2) {
            this.world.getWorldInfo().setRaining(false);
            this.world.setRainStrength(1.0F);
        } else if (i == 3) {
            this.botController.setGameType(GameType.getByID(j));
        } else if (i == 4) {
            if (j == 0) {
                this.bot.connection.sendPacket(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
            }
        } else if (i == 7) {
            this.world.setRainStrength(f);
        } else if (i == 8) {
            this.world.setThunderStrength(f);
        }
    }

    /**
     * Updates the worlds MapStorage with the specified MapData for the specified map-identifier and invokes a
     * MapItemRenderer for it
     */
    public void handleMaps(SPacketMaps packetIn) throws IOException {
        MapData mapdata = ItemMap.loadMapData(packetIn.getMapId(), this.bot.world);


        if (mapdata == null)
        {
            String s = "map_" + packetIn.getMapId();
            mapdata = new MapData(s);

           /* if (mapitemrenderer.getMapInstanceIfExists(s) != null)
            {
                //MapData mapdata1 = mapitemrenderer.getData(mapitemrenderer.getMapInstanceIfExists(s));
                if (mapdata1 != null)
                {
                    mapdata = mapdata1;
                }
            }*/

            this.bot.world.setItemData(s, mapdata);
        }

        packetIn.setMapdataTo(mapdata);
        //mapitemrenderer.updateMapTexture(mapdata);

        final BufferedImage img = new BufferedImage(128, 128, 2);
        final byte[] data = mapdata.colors;
        for (int x = 0; x < 128; ++x) {
            for (int y = 0; y < 128; ++y) {
                final byte input = data[x + y * 128];
                final int colId = input >>> 2 & 0x1F;
                final byte shader = (byte)(input & 0x3);
                BasicColor col = BasicColor.colors.get(colId);
                if (col == null) {
                    col = BasicColor.TRANSPARENT;
                }
                img.setRGB(x, y, col.shaded(shader));
            }
        }
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", os);
            //sendPost(Base64.getEncoder().encodeToString(os.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ImageIO.write(img, "png", new File(bot.getDisplayName().getUnformattedComponentText() + ".png"));
        Minecraft.getInstance().player.connection.handleMaps(packetIn);
    }

    private void sendPost(final String capbase64img) throws Exception {
        if (!this.solved) {
            final HttpPost post = new HttpPost("http://api.captcha.guru/in.php");
            final List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("method", "base64"));
            urlParameters.add(new BasicNameValuePair("key", HClient.API));
            urlParameters.add(new BasicNameValuePair("body", capbase64img));
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            final String resp = EntityUtils.toString(response.getEntity()).replace("OK|", "");
            System.out.println(resp);
            ThreadUtils.sleep(5000L);
            final HttpGet httpGet = new HttpGet("http://api.captcha.guru/res.php?key=91a7b43960222b73309eedfa1e06d2e2&action=get&id=" + resp);
            httpClient = HttpClients.createDefault();
            response = httpClient.execute(httpGet);
            final String resp2 = EntityUtils.toString(response.getEntity()).replace("OK|", "");
            System.out.println(resp2);
            this.getBot().sendChatMessage(resp2);
            this.solved = true;
        }
    }



    public void handleEffect(SPacketEffect packetIn)
    {

        if (packetIn.isSoundServerwide())
        {
            this.world.playBroadcastSound(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
        }
        else
        {
            this.world.playEvent(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
        }
    }

    public void func_191981_a(SPacketAdvancementInfo p_191981_1_)
    {
    }

    public void func_194022_a(SPacketSelectAdvancementsTab p_194022_1_)
    {
    }

    /**
     * Updates the bots statistics or achievements
     */
    public void handleStatistics(SPacketStatistics packetIn)
    {

    }

    public void func_191980_a(SPacketRecipeBook p_191980_1_)
    {

    }

    public void handleEntityEffect(SPacketEntityEffect packetIn)
    {
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity instanceof EntityLivingBase)
        {
            Potion potion = Potion.getPotionById(packetIn.getEffectId());

            if (potion != null)
            {
                PotionEffect potioneffect = new PotionEffect(potion, packetIn.getDuration(), packetIn.getAmplifier(), packetIn.getIsAmbient(), packetIn.doesShowParticles());
                potioneffect.setPotionDurationMax(packetIn.isMaxDuration());
                ((EntityLivingBase)entity).addPotionEffect(potioneffect);
            }
        }
    }

    public void handleCombatEvent(SPacketCombatEvent packetIn)
    {
        if (packetIn.eventType == SPacketCombatEvent.Event.ENTITY_DIED) {
            Entity entity = this.world.getEntityByID(packetIn.playerId);

            if (entity == this.bot) {
                this.sendPacket(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
            }
        }
    }

    public void handleServerDifficulty(SPacketServerDifficulty packetIn)
    {
        this.world.getWorldInfo().setDifficulty(packetIn.getDifficulty());
        this.world.getWorldInfo().setDifficultyLocked(packetIn.isDifficultyLocked());
    }

    public void handleCamera(SPacketCamera packetIn)
    {
    }

    public void handleWorldBorder(SPacketWorldBorder packetIn)
    {
        packetIn.apply(this.world.getWorldBorder());
    }

    public void handleTitle(SPacketTitle packetIn) {
    }

    public void handlePlayerListHeaderFooter(SPacketPlayerListHeaderFooter packetIn) {

    }

    public void handleRemoveEntityEffect(SPacketRemoveEntityEffect packetIn)
    {
        Entity entity = packetIn.getEntity(this.world);

        if (entity instanceof EntityLivingBase)
        {
            ((EntityLivingBase)entity).removeActivePotionEffect(packetIn.getPotion());
        }
    }

    @SuppressWarnings("incomplete-switch")
    public void handlePlayerListItem(SPacketPlayerListItem packetIn) {

        for (SPacketPlayerListItem.AddPlayerData spacketplayerlistitem$addplayerdata : packetIn.getEntries())
        {
            if (packetIn.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER)
            {
                this.playerInfoMap.remove(spacketplayerlistitem$addplayerdata.getProfile().getId());
            }
            else
            {
                NetworkPlayerInfo networkplayerinfo = this.playerInfoMap.get(spacketplayerlistitem$addplayerdata.getProfile().getId());

                if (packetIn.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER)
                {
                    networkplayerinfo = new NetworkPlayerInfo(spacketplayerlistitem$addplayerdata);
                    this.playerInfoMap.put(networkplayerinfo.getGameProfile().getId(), networkplayerinfo);
                }

                if (networkplayerinfo != null)
                {
                    switch (packetIn.getAction())
                    {
                        case ADD_PLAYER:
                            networkplayerinfo.setGameType(spacketplayerlistitem$addplayerdata.getGameMode());
                            networkplayerinfo.setResponseTime(spacketplayerlistitem$addplayerdata.getPing());
                            break;

                        case UPDATE_GAME_MODE:
                            networkplayerinfo.setGameType(spacketplayerlistitem$addplayerdata.getGameMode());
                            break;

                        case UPDATE_LATENCY:
                            networkplayerinfo.setResponseTime(spacketplayerlistitem$addplayerdata.getPing());
                            break;

                        case UPDATE_DISPLAY_NAME:
                            networkplayerinfo.setDisplayName(spacketplayerlistitem$addplayerdata.getDisplayName());
                    }
                }
            }
        }
    }

    public void handleKeepAlive(SPacketKeepAlive packetIn)
    {
        this.sendPacket(new CPacketKeepAlive(packetIn.getId()));
    }

    public void handlePlayerAbilities(SPacketPlayerAbilities packetIn)
    {
        EntityPlayer EntityPlayer1 = this.bot;
        EntityPlayer1.capabilities.isFlying = packetIn.isFlying();
        EntityPlayer1.capabilities.isCreativeMode = packetIn.isCreativeMode();
        EntityPlayer1.capabilities.disableDamage = packetIn.isInvulnerable();
        EntityPlayer1.capabilities.allowFlying = packetIn.isAllowFlying();
        EntityPlayer1.capabilities.setFlySpeed(packetIn.getFlySpeed());
        EntityPlayer1.capabilities.setPlayerWalkSpeed(packetIn.getWalkSpeed());
    }

    /**
     * Displays the available command-completion options the server knows of
     */

    public void handleTabComplete(SPacketTabComplete packetIn)
    {
    }

    public void handleSoundEffect(SPacketSoundEffect packetIn)
    {}

    public void handleCustomSound(SPacketCustomSound packetIn)
    {}

    public void handleResourcePack(SPacketResourcePackSend packetIn)
    {
    }


    public void handleUpdateEntityNBT(SPacketUpdateBossInfo packetIn)
    {
    }

    public void handleCooldown(SPacketCooldown packetIn)
    {

        if (packetIn.getTicks() == 0)
        {
            this.bot.getCooldownTracker().removeCooldown(packetIn.getItem());
        }
        else
        {
            this.bot.getCooldownTracker().setCooldown(packetIn.getItem(), packetIn.getTicks());
        }
    }

    public void handleMoveVehicle(SPacketMoveVehicle packetIn)
    {
        Entity entity = this.bot.getLowestRidingEntity();

        if (entity != this.bot && entity.canPassengerSteer())
        {
            entity.setPositionAndRotation(packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getYaw(), packetIn.getPitch());
            this.netManager.sendPacket(new CPacketVehicleMove(entity));
        }
    }

    /**
     * Handles packets that have room for a channel specification. Vanilla implemented channels are "MC|TrList" to
     * acquire a MerchantRecipeList trades for a villager merchant, "MC|Brand" which sets the server brand? on the
     * bot instance and finally "MC|RPack" which the server uses to communicate the identifier of the default server
     * resourcepack for the client to load.
     */
    public void handleCustomPayload(SPacketCustomPayload packetIn) {
        if ("MC|Brand".equals(packetIn.getChannelName())) {
            this.bot.setServerBrand(packetIn.getBufferData().readStringFromBuffer(32767));
        }
    }


    /**
     * May create a scoreboard objective, remove an objective from the scoreboard or update an objectives' displayname
     */
    public void handleScoreboardObjective(SPacketScoreboardObjective packetIn)
    {

    }

    /**
     * Either updates the score with a specified value or removes the score for an objective
     */
    public void handleUpdateScore(SPacketUpdateScore packetIn)
    {

    }

    /**
     * Removes or sets the ScoreObjective to be displayed at a particular scoreboard position (list, sidebar, below
     * name)
     */
    public void handleDisplayObjective(SPacketDisplayObjective packetIn)
    {
    }

    /**
     * Updates a team managed by the scoreboard: Create/Remove the team registration, Register/Remove the bot-team-
     * memberships, Set team displayname/prefix/suffix and/or whether friendly fire is enabled
     */
    public void handleTeams(SPacketTeams packetIn)
    {
    }

    /**
     * Spawns a specified number of particles at the specified location with a randomized displacement according to
     * specified bounds
     */
    public void handleParticles(SPacketParticles packetIn)
    {

    }

    /**
     * Updates en entity's attributes and their respective modifiers, which are used for speed bonusses (bot
     * sprinting, animals fleeing, baby speed), weapon/tool attackDamage, hostiles followRange randomization, zombie
     * maxHealth and knockback resistance as well as reinforcement spawning chance.
     */
    public void handleEntityProperties(SPacketEntityProperties packetIn)
    {
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity != null)
        {
            if (!(entity instanceof EntityLivingBase))
            {
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
            }
            else
            {
                AbstractAttributeMap abstractattributemap = ((EntityLivingBase)entity).getAttributeMap();

                for (SPacketEntityProperties.Snapshot spacketentityproperties$snapshot : packetIn.getSnapshots())
                {
                    IAttributeInstance iattributeinstance = abstractattributemap.getAttributeInstanceByName(spacketentityproperties$snapshot.getName());

                    if (iattributeinstance == null)
                    {
                        iattributeinstance = abstractattributemap.registerAttribute(new RangedAttribute(null, spacketentityproperties$snapshot.getName(), 0.0D, 2.2250738585072014E-308D, Double.MAX_VALUE));
                    }

                    iattributeinstance.setBaseValue(spacketentityproperties$snapshot.getBaseValue());
                    iattributeinstance.removeAllModifiers();

                    for (AttributeModifier attributemodifier : spacketentityproperties$snapshot.getModifiers())
                    {
                        iattributeinstance.applyModifier(attributemodifier);
                    }
                }
            }
        }
    }

    public void func_194307_a(SPacketPlaceGhostRecipe p_194307_1_)
    {
    }

    public BotNetwork getNetworkManager() {
        return this.netManager;
    }

    public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
        return this.playerInfoMap.values();
    }


    public NetworkPlayerInfo getPlayerInfo(UUID uniqueId) {
        return this.playerInfoMap.get(uniqueId);
    }
    /**
     * Returns this the NetworkManager instance registered with this NetworkHandlerPlayClient
     */

    public NetworkPlayerInfo getPlayerInfo(String name) {
        for (NetworkPlayerInfo networkplayerinfo : this.playerInfoMap.values()) {
            if (networkplayerinfo.getGameProfile().getName().equals(name)) {
                return networkplayerinfo;
            }
        }

        return null;
    }


    public GameProfile getGameProfile() {
        return this.profile;
    }

    public BotController getController() {
        return botController;
    }

    public BotWorld getWorld() {
        return world;
    }

    public BotPlayer getBot() {
        return bot;
    }
}