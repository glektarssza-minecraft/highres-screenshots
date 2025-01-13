package com.glektarssza.highres_screenshots.config;

import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.glektarssza.highres_screenshots.ScreenshotType;

/**
 * The main configuration for the mod.
 */
public final class Config {
    /**
     * The main Forge configuration object.
     */
    @Nullable
    private static Configuration forgeConfig;

    /**
     * The data values for the different configuration elements.
     */
    private static ConfigItems configItems;

    static {
        configItems = new ConfigItems();
    }

    /**
     * Create a new instance.
     */
    private Config() {
        // -- Does nothing.
    }

    /**
     * Get the width of the screenshot to take.
     *
     * @return The width of the screenshot to take.
     */
    public static int getScreenshotWidth() {
        return configItems.screenshotWidth;
    }

    /**
     * Get the height of the screenshot to take.
     *
     * @return The height of the screenshot to take.
     */
    public static int getScreenshotHeight() {
        return configItems.screenshotHeight;
    }

    /**
     * Get the type of the screenshot to take.
     *
     * @return The type of the screenshot to take.
     */
    public static ScreenshotType getScreenshotType() {
        return configItems.screenshotType;
    }

    /**
     * Set the width of the screenshot to take.
     *
     * @param width The new width of the screenshot to take.
     */
    public static void setScreenshotWidth(int width) {
        int oldWidth = configItems.screenshotWidth;
        if (forgeConfig != null) {
            Property property = forgeConfig.get("", "screenshotWidth", 1280,
                "The width of the screenshot to take.", 640, 15360);
            property.set(width);
            if (property.hasChanged() && forgeConfig != null) {
                forgeConfig.save();
            }
        }
        MinecraftForge.EVENT_BUS
            .post(new ScreenshotWidthSetEvent(oldWidth, width));
    }

    /**
     * Set the height of the screenshot to take.
     *
     * @param height The new height of the screenshot to take.
     */
    public static void setScreenshotHeight(int height) {
        int oldHeight = configItems.screenshotHeight;
        if (forgeConfig != null) {
            Property property = forgeConfig.get("", "screenshotHeight", 720,
                "The height of the screenshot to take.", 480, 8640);
            property.set(height);
            if (property.hasChanged() && forgeConfig != null) {
                forgeConfig.save();
            }
        }
        MinecraftForge.EVENT_BUS
            .post(new ScreenshotHeightSetEvent(oldHeight, height));
    }

    /**
     * Set the type of the screenshot to take.
     *
     * @param type The new type of the screenshot to take.
     */
    public static void setScreenshotType(ScreenshotType type) {
        ScreenshotType oldType = configItems.screenshotType;
        if (forgeConfig != null) {
            Property property = forgeConfig.get("", "screenshotType", "PNG",
                "The type of the screenshot to take.",
                (String[]) Arrays.stream(ScreenshotType.values())
                    .map((e) -> e.toString()).toArray(),
                (String[]) Arrays.stream(ScreenshotType.values())
                    .map((e) -> e.toString()).toArray());
            property.set(type.toString());
            if (property.hasChanged() && forgeConfig != null) {
                forgeConfig.save();
            }
        }
        MinecraftForge.EVENT_BUS
            .post(new ScreenshotTypeSetEvent(oldType, type));
    }
}
