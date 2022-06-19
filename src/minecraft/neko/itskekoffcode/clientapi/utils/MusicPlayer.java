package neko.itskekoffcode.clientapi.utils;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    // beta
    
    public final List<ResourceLocation> sounds = new ArrayList<>();
    public boolean playing = false;

    public void initSounds() {
        this.sounds.clear();
        this.sounds.add(new ResourceLocation("hclient/sounds/1.mp3"));
        this.sounds.add(new ResourceLocation("hclient/sounds/2.mp3"));
        this.sounds.add(new ResourceLocation("hclient/sounds/3.mp3"));
        this.sounds.add(new ResourceLocation("hclient/sounds/4.mp3"));
        this.sounds.add(new ResourceLocation("hclient/sounds/5.mp3"));
        this.sounds.add(new ResourceLocation("hclient/sounds/6.mp3"));
        this.sounds.add(new ResourceLocation("hclient/sounds/7.mp3"));
        this.sounds.add(new ResourceLocation("hclient/sounds/8.mp3"));
    }
    public void playSounds() {
        this.playing = true;

    }
    public void stopPlay() {
        this.playing = false;
    }
}
