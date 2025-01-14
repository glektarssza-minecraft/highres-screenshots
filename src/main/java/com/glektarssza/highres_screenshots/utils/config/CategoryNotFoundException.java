package com.glektarssza.highres_screenshots.utils.config;

/**
 * A simple exception for when a Forge configuration category could not be
 * found.
 */
public class CategoryNotFoundException extends Exception {
    /**
     * The name of the category that could not be found.
     */
    public final String categoryName;

    /**
     * Create a new instance.
     *
     * @param categoryName The name of the category that could not be found.
     */
    public CategoryNotFoundException(String categoryName) {
        this.categoryName = categoryName;
    }
}
