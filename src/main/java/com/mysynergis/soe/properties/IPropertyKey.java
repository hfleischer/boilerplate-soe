package com.mysynergis.soe.properties;

/**
 * definition of a soi property
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 * @param <T>
 */
public interface IPropertyKey<T> {

    /**
     * let the property type create an appropriate property instance
     *
     * @param value
     * @param isDefaultValue indicated if this is the default property (defined on the class) or an actual configured values
     * @return
     */
    IProperty<T> createProperty(String raw, boolean isDefaultValue);

    /**
     * the this property key's key
     *
     * @return
     */
    String getKey();

}
