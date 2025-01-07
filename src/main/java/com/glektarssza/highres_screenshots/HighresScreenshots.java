package com.glektarssza.highres_screenshots;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * The root mod class.
 */
@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.MOD_VERSION, dependencies = Tags.MOD_DEPENDENCIES)
public class HighresScreenshots {
    /**
     * The logger to use for the mod.
     */
    public static Logger LOGGER;

    /**
     * The mod singleton instance.
     */
    @Mod.Instance
    public static HighresScreenshots instance;

    /**
     * Handle the Forge Mod Loader pre-initialization event.
     *
     * @param event The event to handle.
     */
    @Mod.EventHandler
    public void OnPreInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        LOGGER.info("Pre-initializing HighresScreenshot...");
        // TODO
    }

    /**
     * Handle the Forge Mod Loader post-initialization event.
     *
     * @param event The event to handle.
     */
    @Mod.EventHandler
    public void OnPreInit(FMLPostInitializationEvent event) {
        LOGGER.info("Post-initializing HighresScreenshot...");
        // TODO
    }
}
