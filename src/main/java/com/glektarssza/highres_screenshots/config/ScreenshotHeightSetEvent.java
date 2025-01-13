package com.glektarssza.highres_screenshots.config;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * An event indicating the height of screenshot to take was updated.
 */
public class ScreenshotHeightSetEvent extends Event {
    /**
     * The old screenshot height.
     */
    private final int oldHeight;

    /**
     * The updated screenshot height.
     */
    private final int updatedHeight;

    /**
     * Create a new instance.
     *
     * @param oldHeight The old screenshot height.
     * @param updatedHeight The updated screenshot height.
     */
    public ScreenshotHeightSetEvent(int oldHeight, int updatedHeight) {
        this.oldHeight = oldHeight;
        this.updatedHeight = updatedHeight;
    }

    /**
     * Get the old screenshot height.
     *
     * @return The old screenshot height.
     */
    public int getOldHeight() {
        return this.oldHeight;
    }

    /**
     * Get the updated screenshot height.
     *
     * @return The updated screenshot height.
     */
    public int getUpdatedHeight() {
        return this.updatedHeight;
    }
}
