package com.mysynergis.soe.properties.impl;

import java.util.HashMap;
import java.util.Map;

import com.mysynergis.soe.properties.IPropertyKey;

/**
 * accessor util to {@link IPropertyKey} instances
 *
 * @author h.fleischer
 * @since 02.01.2019
 *
 */
@SuppressWarnings("rawtypes")
public class PropertyKey {

    private static final Map<String, IPropertyKey> PROPERTY_ID_BY_NAME;
    static {
        PROPERTY_ID_BY_NAME = new HashMap<>();
    }

    /**
     * property referencing a path to the configuration of the SOI
     */
    public static final IPropertyKey CONFIG = new PropertyKeyString("config");

    private PropertyKey() {
        // no public instance
    }

    /**
     * register this property id for later retrieval
     *
     * @param propertyId
     */
    static void registerPropertyKey(IPropertyKey propertyId) {
        PROPERTY_ID_BY_NAME.put(propertyId.getKey(), propertyId);
    }

    /**
     * get a property stored with this id
     *
     * @param id the given id
     * @return the propoerty with the given name or null if no such property is
     * available
     */
    public static IPropertyKey fromId(String id) {
        return PROPERTY_ID_BY_NAME.get(id);
    }

}
