package com.glektarssza.highres_screenshots.config;

import com.glektarssza.highres_screenshots.ScreenshotType;

/**
 * A class which contains the data values for the various configuration fields.
 */
public class ConfigItems {
    /**
     * The width of the screenshot to take.
     */
    public int screenshotWidth;

    /**
     * The height of the screenshot to take.
     */
    public int screenshotHeight;

    /**
     * The type of file to save the screenshot as.
     */
    public ScreenshotType screenshotType;

    /**
     * Create a new instance.
     */
    public ConfigItems() {
        this.screenshotWidth = 1280;
        this.screenshotHeight = 720;
        this.screenshotType = ScreenshotType.PNG;
    }
}
