package com.glektarssza.highres_screenshots.config;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.glektarssza.highres_screenshots.ScreenshotType;

/**
 * An event indicating the type of screenshot to take was updated.
 */
public class ScreenshotTypeSetEvent extends Event {
    /**
     * The old screenshot type.
     */
    private final ScreenshotType oldType;

    /**
     * The updated screenshot type.
     */
    private final ScreenshotType updatedType;

    /**
     * Create a new instance.
     *
     * @param oldType The old screenshot type.
     * @param updatedType The updated screenshot type.
     */
    public ScreenshotTypeSetEvent(ScreenshotType oldType,
        ScreenshotType updatedType) {
        this.oldType = oldType;
        this.updatedType = updatedType;
    }

    /**
     * Get the old screenshot type.
     *
     * @return The old screenshot type.
     */
    public ScreenshotType getOldType() {
        return this.oldType;
    }

    /**
     * Get the updated screenshot type.
     *
     * @return The updated screenshot type.
     */
    public ScreenshotType getUpdatedType() {
        return this.updatedType;
    }
}
