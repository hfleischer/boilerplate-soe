package com.mysynergis.soe.properties;

import com.mysynergis.soe.lifecycle.ILifecycleEx;

/**
 * a set of properties set in the SOE/SOI property page
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public interface IProperties extends ILifecycleEx {

    /**
     * get the property identified by the given key
     *
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    IProperty getProperty(IPropertyKey key);

}
