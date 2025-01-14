package com.glektarssza.highres_screenshots.utils.config;

/**
 * A simple exception for when a Forge configuration category already exists.
 */
public class CategoryAlreadyExistsException extends Exception {
    /**
     * The name of the category that already existed.
     */
    public final String categoryName;

    /**
     * Create a new instance.
     *
     * @param categoryName The name of the category that already existed.
     */
    public CategoryAlreadyExistsException(String categoryName) {
        this.categoryName = categoryName;
    }
}
