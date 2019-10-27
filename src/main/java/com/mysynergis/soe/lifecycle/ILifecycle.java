package com.mysynergis.soe.lifecycle;

import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.ShutdownFailure;

/**
 * to be implemented by classes "living" with the SOI creation, activation, ... cycle<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public interface ILifecycle {

    void construct(IPropertySet propertySet) throws ConstructFailure;

    void shutdown() throws ShutdownFailure;

}
