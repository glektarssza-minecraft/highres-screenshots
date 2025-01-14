package com.glektarssza.highres_screenshots.config;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.nio.file.WatchEvent.Kind;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.glektarssza.highres_screenshots.ScreenshotType;
import com.glektarssza.highres_screenshots.Tags;

/**
 * The main configuration for the mod.
 */
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class Config {
    /**
     * The main Forge configuration file watch service.
     */
    @Nullable
    private static WatchService forgeWatchService;

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

    /**
     * Initialize the file system watcher.
     *
     * @return {@code true} if the operation completed successfully;
     *         {@code false} otherwise.
     *
     * @throws IOException Thrown if a new file system watch service cannot be
     *         created or a file system watcher for the main Forge configuration
     *         file could not be registered.
     */
    public static boolean initFileWatcher() throws IOException {
        if (forgeConfig == null) {
            return false;
        }
        if (forgeWatchService == null) {
            forgeWatchService = FileSystems.getDefault().newWatchService();
        }
        @SuppressWarnings("null")
        Path configPath = forgeConfig.getConfigFile().toPath().getParent();
        configPath.register(forgeWatchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.ENTRY_DELETE);
        return true;
    }

    /**
     * Handle some logic every game tick.
     *
     * @param ev The tick event.
     */
    @SuppressWarnings("null")
    @SubscribeEvent
    public static void onTick(TickEvent ev) {
        if (forgeWatchService != null && forgeConfig != null) {
            WatchKey key = forgeWatchService.poll();
            if (key != null) {
                Watchable w = key.watchable();
                if (w instanceof Path) {
                    Path p = (Path) w;
                    if (p.equals(forgeConfig.getConfigFile().toPath())) {
                        List<WatchEvent<?>> evts = key
                            .pollEvents();
                        for (WatchEvent<?> e : evts) {
                            Kind<?> k = e.kind();
                            if (k
                                .equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                                forgeConfig.load();
                            } else if (k
                                .equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                                forgeConfig.save();
                            } else if (k
                                .equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                                forgeConfig.load();
                            }
                        }
                    }
                }
            }
        }
    }
}
