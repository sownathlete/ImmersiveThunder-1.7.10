package com.netcatgirl.immersivethunder.client;

import com.netcatgirl.immersivethunder.Constants;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;

public class ThunderSoundHandler {
    private static final String VANILLA_THUNDER_SOUND = "ambient.weather.thunder";

    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent17 event) {
        if (event.result == null || event.name == null || !VANILLA_THUNDER_SOUND.equals(event.name)) {
            return;
        }

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }

        ISound vanillaThunder = event.result;
        float x = vanillaThunder.getXPosF();
        float y = vanillaThunder.getYPosF();
        float z = vanillaThunder.getZPosF();

        double distanceToThunder = player.getDistance(x, y, z);
        String replacementSound;
        float replacementVolume;
        boolean useDistanceDelay;

        if (distanceToThunder <= Constants.CLOSE_DISTANCE) {
            replacementSound = Constants.SOUND_THUNDER_CLOSE;
            replacementVolume = Constants.THUNDER_CLOSE_VOLUME;
            useDistanceDelay = false;
        } else if (distanceToThunder <= Constants.MEDIUM_DISTANCE) {
            replacementSound = Constants.SOUND_THUNDER_MEDIUM;
            replacementVolume = Constants.THUNDER_MEDIUM_VOLUME;
            useDistanceDelay = true;
        } else {
            replacementSound = Constants.SOUND_THUNDER_FAR;
            replacementVolume = Constants.THUNDER_FAR_VOLUME;
            useDistanceDelay = true;
        }

        ResourceLocation location = new ResourceLocation(Constants.MOD_ID, replacementSound);
        ISound replacementThunder = new PositionedSoundRecord(
            location,
            replacementVolume,
            Constants.THUNDER_PITCH,
            x,
            y,
            z
        );

        if (!useDistanceDelay) {
            event.result = replacementThunder;
            return;
        }

        int delayTicks = 0;
        double distanceSq = player.getDistanceSq(x, y, z);

        if (distanceSq > 100.0D) {
            delayTicks = (int) (Math.sqrt(distanceSq) / 40.0D * 20.0D);
        }

        if (delayTicks > 0) {
            Minecraft.getMinecraft().getSoundHandler().playDelayedSound(replacementThunder, delayTicks);
        } else {
            Minecraft.getMinecraft().getSoundHandler().playSound(replacementThunder);
        }

        event.result = null;
    }
}
