package com.mysynergis.soe.arcobjects;

import java.util.Optional;

import com.esri.arcgis.system.IPersistStream;
import com.mysynergis.soe.failure.ArcObjectFailure;

/**
 * definition for types that hold a (potentially serializable) reference to an arcobject
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 * @param <T>
 */
public interface IArcObjectReference<T> {

    boolean isReleaseable();

    Optional<Class<? extends IPersistStream>> optPersistableType();

    Class<T> getLiveType();

    /**
     * get the contained object<br>
     * will return a fresh deserialisation upon first call in a request, and the exact same object upon further calls within the same request<br>
     *
     * @return
     */
    T get() throws ArcObjectFailure;

    /**
     * get a copy of the object reference<br>
     *
     * @return a fresh object reference
     */
    IArcObjectReference<T> copy() throws ArcObjectFailure;

    /**
     * release the underlying live object
     */
    void release();

}
