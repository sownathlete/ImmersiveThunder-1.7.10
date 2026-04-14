package com.netcatgirl.immersivethunder;

import com.netcatgirl.immersivethunder.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = Constants.MOD_ID,
    name = Constants.MOD_NAME,
    version = Constants.MOD_VERSION,
    acceptedMinecraftVersions = "[1.7.10]"
)
public class ImmersiveThunderMod {
    @SidedProxy(
        clientSide = "com.netcatgirl.immersivethunder.proxy.ClientProxy",
        serverSide = "com.netcatgirl.immersivethunder.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
