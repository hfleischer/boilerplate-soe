package com.mysynergis.soe.properties;

/**
 * definition of a property defined in the SOI property page<br>
 * a type is defined to describe the desired type
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 * @param <T> the type of value held by this property
 */
public interface IProperty<T> {

    /**
     * get the key of this property
     *
     * @return
     */
    IPropertyKey<T> getKey();

    /**
     * check if this property has a non-void value
     *
     * @return
     */
    boolean hasValue();

    /**
     * get the value of this property
     *
     * @return
     */
    T getValue();

    /**
     * check is this is the default property
     *
     * @return
     */
    boolean isDefaultValue();

}
