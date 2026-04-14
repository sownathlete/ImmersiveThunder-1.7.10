package com.netcatgirl.immersivethunder.proxy;

import com.netcatgirl.immersivethunder.client.ThunderSoundHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ThunderSoundHandler());
    }
}
