package com.mysynergis.soe.properties.impl;

import com.mysynergis.soe.properties.IProperty;
import com.mysynergis.soe.util.VoidUtil;

/**
 * string property implementation
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
class PropertyStringImpl implements IProperty<String> {

    private final PropertyKeyString key;
    private final String value;
    private final boolean isDefaultValue;

    /**
     * construct instance with a given key, value and a flag indicating if this is
     * the default value instance
     *
     * @param key
     * @param value
     * @param isDefaultValue
     */
    PropertyStringImpl(PropertyKeyString key, String value, boolean isDefaultValue) {
        this.key = key;
        this.value = value;
        this.isDefaultValue = isDefaultValue;
    }

    @Override
    public PropertyKeyString getKey() {
        return this.key;
    }

    @Override
    public boolean hasValue() {
        return !VoidUtil.isVoid(this.value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean isDefaultValue() {
        return this.isDefaultValue;
    }

    @Override
    public String toString() {
        return String.format("%s [key: %s, value:%s]", getClass().getSimpleName(), this.key.getKey(), this.value);
    }

}
