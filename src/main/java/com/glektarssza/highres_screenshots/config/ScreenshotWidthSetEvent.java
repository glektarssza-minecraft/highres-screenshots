package com.glektarssza.highres_screenshots.config;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ScreenshotWidthSetEvent extends Event {
    /**
     * The old screenshot width.
     */
    private final int oldWidth;

    /**
     * The updated screenshot width.
     */
    private final int updatedWidth;

    /**
     * Create a new instance.
     *
     * @param oldWidth The old screenshot width.
     * @param updatedWidth The updated screenshot width.
     */
    public ScreenshotWidthSetEvent(int oldWidth, int updatedWidth) {
        this.oldWidth = oldWidth;
        this.updatedWidth = updatedWidth;
    }

    /**
     * Get the old screenshot width.
     *
     * @return The old screenshot width.
     */
    public int getOldWidth() {
        return this.oldWidth;
    }

    /**
     * Get the updated screenshot width.
     *
     * @return The updated screenshot width.
     */
    public int getUpdatedWidth() {
        return this.updatedWidth;
    }
}
