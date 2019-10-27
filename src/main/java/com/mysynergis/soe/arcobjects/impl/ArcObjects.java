package com.mysynergis.soe.arcobjects.impl;

import java.lang.reflect.Constructor;

import com.esri.arcgis.system.IMemoryBlobStream;
import com.esri.arcgis.system.IMemoryBlobStreamVariant;
import com.esri.arcgis.system.IObjectCopy;
import com.esri.arcgis.system.IPersistStream;
import com.esri.arcgis.system.MemoryBlobStream;
import com.esri.arcgis.system.ObjectCopy;
import com.mysynergis.soe.arcobjects.IArcObjectReference;
import com.mysynergis.soe.failure.ArcObjectFailure;

public class ArcObjects {

    private ArcObjects() {
        //no public instance
    }

    public static <P extends IPersistStream> byte[] save(P instance) throws ArcObjectFailure {

        try {

            IMemoryBlobStream memoryBlobStream = ArcObjects.createAndRegister(MemoryBlobStream.class);
            instance.save(memoryBlobStream, 0);
            IMemoryBlobStreamVariant memoryBlobStreamVariant = (IMemoryBlobStreamVariant) memoryBlobStream;
            Object[] target = new Object[1];
            memoryBlobStreamVariant.exportToVariant(target);

            Releaser.register(instance);

            return (byte[]) target[0];

        } catch (Exception ex) {
            throw new ArcObjectFailure("failed to save arcobject", ex);
        }

    }

    public static <P extends IPersistStream> P load(byte[] bytes, Class<P> instanceClass) throws ArcObjectFailure {

        try {

            P instance = ArcObjects.createAndRegister(instanceClass);
            IMemoryBlobStream memoryBlobStream = ArcObjects.createAndRegister(MemoryBlobStream.class);
            IMemoryBlobStreamVariant memoryBlobStreamVariant = (IMemoryBlobStreamVariant) memoryBlobStream;
            memoryBlobStreamVariant.importFromVariant(bytes);
            instance.load(memoryBlobStream);

            Releaser.register(instance);

            return instance;

        } catch (Exception ex) {
            throw new ArcObjectFailure("failed to load arcobject", ex);
        }

    }

    @SuppressWarnings("unchecked")
    public static <P> P copy(P original) throws ArcObjectFailure {

        try {

            IObjectCopy objectCopy = ArcObjects.createAndRegister(ObjectCopy.class);
            P copy = (P) objectCopy.copy(original);

            Releaser.register(original);
            Releaser.register(copy);

            return copy;

        } catch (Exception ex) {
            throw new ArcObjectFailure("failed to copy arcobject", ex);
        }

    }

    /**
     * creates a new instance of the given class and registers the object for
     * release, then returns the created instance<br>
     * if the input is null, nothing will be registered and null will be returned
     *
     * @param releasableClass
     * @return
     * @throws ArcObjectFailure
     */
    public static <T> T createAndRegister(final Class<T> type) throws ArcObjectFailure {

        try {
            T instance = type.getConstructor().newInstance();
            Releaser.register(instance);
            return instance;
        } catch (Exception ex) {
            throw new ArcObjectFailure("failed to create arcobject (" + type + ")", ex);
        }

    }

    public static <T> T proxied(final Class<T> type, final Object proxyable) throws ArcObjectFailure {

        try {
            Constructor<T> proxyClassConstructor = type.getConstructor(Object.class);
            T instance = proxyClassConstructor.newInstance(proxyable);
            return Releaser.register(instance);
        } catch (Exception ex) {
            throw new ArcObjectFailure("failed to create arcobject (" + type + ")", ex);
        }

    }

    public static <T> IArcObjectReference<T> persistableReference(final T instance) throws ArcObjectFailure {

        try {
            return new ArcObjectReferenceImplPersistable<>(instance);
        } catch (Exception ex) {
            throw new ArcObjectFailure("failed to create arcobject reference (instance: " + instance + ")", ex);
        }

    }

}
