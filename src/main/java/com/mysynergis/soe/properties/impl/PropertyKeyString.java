package com.mysynergis.soe.properties.impl;

import com.mysynergis.soe.properties.IProperty;
import com.mysynergis.soe.properties.IPropertyKey;

/**
 * a String SOI proporty<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
class PropertyKeyString implements IPropertyKey<String> {

    private final String key;

    /**
     * instance with a given id
     *
     * @param key
     */
    PropertyKeyString(String key) {
        this.key = key;
        PropertyKey.registerPropertyKey(this);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    /**
     * create a string property instance
     */
    @Override
    public IProperty<String> createProperty(String raw, boolean isDefaultValue) {
        return new PropertyStringImpl(this, raw, isDefaultValue);
    }

    @Override
    public String toString() {
        return String.format("%s [key: %s]", getClass().getSimpleName(), this.key);
    }

}
