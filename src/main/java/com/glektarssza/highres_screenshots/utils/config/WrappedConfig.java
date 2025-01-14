package com.glektarssza.highres_screenshots.utils.config;

import java.io.File;
import java.util.Arrays;

import javax.annotation.Nullable;

import org.lwjgl.util.Color;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.Config.Type;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;

/**
 * A thin wrapper around the Forge configuration object.
 */
public class WrappedConfig {
    /**
     * The wrapped Forge configuration.
     */
    private Configuration forgeConfig;

    /**
     * Create a new instance.
     *
     * @param file The file to store the configuration in.
     * @param configVersion The version of the configuration.
     */
    public WrappedConfig(File file, String configVersion) {
        this.forgeConfig = new Configuration(file, configVersion);
    }

    /**
     * Create a new instance.
     *
     * @param file The file to store the configuration in.
     * @param configVersion The version of the configuration.
     * @param caseSensitiveCategories Whether to treat category names in a
     *        case-sensitive manner.
     */
    public WrappedConfig(File file, String configVersion,
        boolean caseSensitiveCategories) {
        this.forgeConfig = new Configuration(file, configVersion,
            caseSensitiveCategories);
    }

    /**
     * Check whether this configuration has a given category.
     *
     * @param name The category name to check against.
     *
     * @return {@code} true if the category exists; {@code false} otherwise.
     */
    public boolean hasCategory(String name) {
        return this.forgeConfig.hasCategory(name);
    }

    /**
     * Add a category to the configuration.
     *
     * @param name The name of the category to add.
     * @param comment The comment to give the category.
     *
     * @return The newly added category on success; {@code null} otherwise.
     */
    @Nullable
    public ConfigCategory tryAddCategory(String name,
        @Nullable String comment) {
        if (this.hasCategory(name)) {
            return null;
        }
        ConfigCategory category = this.forgeConfig.getCategory(name);
        if (comment != null) {
            category.setComment(comment);
        }
        return category;
    }

    /**
     * Add a category to the configuration.
     *
     * @param name The name of the category to add.
     * @param comment The comment to give the category.
     *
     * @return The newly added category.
     *
     * @throws CategoryAlreadyExistsException Thrown if the named category
     *         already exists.
     */
    public ConfigCategory addCategory(String name, @Nullable String comment)
        throws CategoryAlreadyExistsException {
        ConfigCategory result = this.tryAddCategory(name, comment);
        if (result == null) {
            throw new CategoryAlreadyExistsException(name);
        }
        return result;
    }

    /**
     * Try to remove a category from the configuration.
     *
     * @param name The name of the category to try to remove.
     *
     * @return {@code true} if the category was removed; {@code false}
     *         otherwise.
     */
    public boolean tryRemoveCategory(String name) {
        ConfigCategory category = this.tryGetCategory(name);
        if (category == null) {
            return false;
        }
        this.forgeConfig.removeCategory(category);
        return true;
    }

    /**
     * Remove a category from the configuration.
     *
     * @param name The name of the category to remove.
     *
     * @throws CategoryNotFoundException Thrown if there is no category with the
     *         given name.
     */
    public void removeCategory(String name) throws CategoryNotFoundException {
        boolean result = this.tryRemoveCategory(name);
        if (!result) {
            throw new CategoryNotFoundException(name);
        }
    }

    /**
     * Try to get a category from the configuration.
     *
     * @param name The name of the category to try to get.
     *
     * @return The category if it exists; {@code null} otherwise.
     */
    @Nullable
    public ConfigCategory tryGetCategory(String name) {
        if (!this.forgeConfig.hasCategory(name)) {
            return null;
        }
        return this.forgeConfig.getCategory(name);
    }

    /**
     * Get a category from the configuration.
     *
     * @param name The name of the category to get.
     *
     * @return The category.
     *
     * @throws CategoryNotFoundException Thrown if there is no category with the
     *         given name.
     */
    public ConfigCategory getCategory(String name)
        throws CategoryNotFoundException {
        ConfigCategory result = this.tryGetCategory(name);
        if (result == null) {
            throw new CategoryNotFoundException(name);
        }
        return result;
    }

    /**
     * Try to set the comment on a category.
     *
     * @param name The name of the category to try to set the comment on.
     * @param comment The comment to try to set on the category.
     *
     * @return {@code true} if the comment was set; {@code false} otherwise.
     */

    public boolean trySetCategoryComment(String name, String comment) {
        ConfigCategory category = this.tryGetCategory(name);
        if (category == null) {
            return false;
        }
        category.setComment(comment);
        return true;
    }

    /**
     * Set the comment on a category.
     *
     * @param name The name of the category to set the comment on.
     * @param comment The comment to set on the category.
     *
     * @throws CategoryNotFoundException Thrown if there is no category with the
     *         given name.
     */
    public void setCategoryComment(String name, String comment)
        throws CategoryNotFoundException {
        boolean result = this.trySetCategoryComment(name, comment);
        if (!result) {
            throw new CategoryNotFoundException(name);
        }
    }

    /**
     * Check whether this configuration has a given property within a given
     * category.
     *
     * @param categoryName The category name to check against.
     * @param name The property name to check against.
     *
     * @return {@code} true if the property exists within the category;
     *         {@code false} otherwise.
     */
    public boolean hasProperty(String categoryName, String name) {
        ConfigCategory category = this.tryGetCategory(categoryName);
        if (category == null) {
            return false;
        }
        return category.containsKey(name);
    }

    /**
     * Try to add a boolean property to the configuration in the given category.
     *
     * @param categoryName The name of the category to try to add the new
     *        property to.
     * @param name The name of the property to try to add to the category.
     * @param defaultValue The default value to give the new property.
     * @param comment An optional comment to assign to the new property.
     *
     * @return The newly created property on success; {@code null} otherwise.
     */
    @Nullable
    public Property tryAddBooleanProperty(String categoryName, String name,
        boolean defaultValue, @Nullable String comment) {
        if (this.hasProperty(categoryName, name)) {
            return null;
        }
        ConfigCategory category = this.tryGetCategory(categoryName);
        if (category == null) {
            return null;
        }
        Property property = new Property(name,
            Boolean.valueOf(defaultValue).toString(),
            Property.Type.BOOLEAN);
        property.setDefaultValue(defaultValue);
        category.put(name, property);
        if (comment != null) {
            property.setComment(comment);
        }
        return property;
    }

    /**
     * Try to add a boolean property to the configuration in the given category.
     *
     * @param categoryName The name of the category to try to add the new
     *        property to.
     * @param name The name of the property to try to add to the category.
     * @param defaultValue The default value to give the new property.
     * @param langKey The language key to associate the new property with.
     * @param comment An optional comment to assign to the new property.
     *
     * @return The newly created property on success; {@code null} otherwise.
     */
    @Nullable
    public Property tryAddBooleanProperty(String categoryName, String name,
        boolean defaultValue, String langKey, @Nullable String comment) {
        if (this.hasProperty(categoryName, name)) {
            return null;
        }
        ConfigCategory category = this.tryGetCategory(categoryName);
        if (category == null) {
            return null;
        }
        Property property = new Property(name,
            Boolean.valueOf(defaultValue).toString(),
            Property.Type.BOOLEAN, langKey);
        property.setDefaultValue(defaultValue);
        category.put(name, property);
        if (comment != null) {
            property.setComment(comment);
        }
        return property;
    }

    /**
     * Try to add a boolean property to the configuration in the given category.
     *
     * @param categoryName The name of the category to try to add the new
     *        property to.
     * @param name The name of the property to try to add to the category.
     * @param defaultValues The default values to give the new property.
     * @param comment An optional comment to assign to the new property.
     *
     * @return The newly created property on success; {@code null} otherwise.
     */
    @Nullable
    public Property tryAddBooleanListProperty(String categoryName, String name,
        boolean[] defaultValues, @Nullable String comment) {
        if (this.hasProperty(categoryName, name)) {
            return null;
        }
        ConfigCategory category = this.tryGetCategory(categoryName);
        if (category == null) {
            return null;
        }
        Property property = new Property(name,
            (String[]) BooleanArrayList.wrap(defaultValues).stream()
                .map((e) -> e.toString())
                .toArray(),
            Property.Type.BOOLEAN);
        property.setDefaultValues(defaultValues);
        category.put(name, property);
        if (comment != null) {
            property.setComment(comment);
        }
        return property;
    }

    /**
     * Try to add a boolean property to the configuration in the given category.
     *
     * @param categoryName The name of the category to try to add the new
     *        property to.
     * @param name The name of the property to try to add to the category.
     * @param defaultValues The default values to give the new property.
     * @param langKey The language key to associate the new property with.
     * @param comment An optional comment to assign to the new property.
     *
     * @return The newly created property on success; {@code null} otherwise.
     */
    @Nullable
    public Property tryAddBooleanListProperty(String categoryName, String name,
        boolean[] defaultValues, String langKey, @Nullable String comment) {
        if (this.hasProperty(categoryName, name)) {
            return null;
        }
        ConfigCategory category = this.tryGetCategory(categoryName);
        if (category == null) {
            return null;
        }
        Property property = new Property(name,
            (String[]) BooleanArrayList.wrap(defaultValues).stream()
                .map((e) -> e.toString())
                .toArray(),
            Property.Type.BOOLEAN, langKey);
        property.setDefaultValues(defaultValues);
        category.put(name, property);
        if (comment != null) {
            property.setComment(comment);
        }
        return property;
    }
}
