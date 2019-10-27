package com.mysynergis.soe.arcobjects.impl;

import java.util.Optional;

import com.esri.arcgis.system.IPersistStream;
import com.mysynergis.soe.arcobjects.IArcObjectReference;
import com.mysynergis.soe.failure.ArcObjectFailure;

public class ArcObjectReferenceImplPersistable<T> implements IArcObjectReference<T> {

    private final byte[] liveObjectBytes;
    private T liveObject;
    private final Class<T> liveType;
    private final Class<? extends IPersistStream> persistableType;

    @SuppressWarnings("unchecked")
    protected ArcObjectReferenceImplPersistable(T liveObject) throws ArcObjectFailure {

        this.liveObject = liveObject;
        if (this.liveObject instanceof IPersistStream) {
            this.persistableType = (Class<? extends IPersistStream>) this.liveObject.getClass();
            this.liveObjectBytes = ArcObjects.save((IPersistStream) this.liveObject);
        } else {
            this.persistableType = null;
            this.liveObjectBytes = null;
        }
        this.liveType = (Class<T>) this.liveObject.getClass();

        Releaser.register(this);

    }

    private ArcObjectReferenceImplPersistable(Class<T> liveObjectClass, Class<? extends IPersistStream> persistableObjectClass, byte[] liveObjectBytes) {

        this.liveObject = null;
        this.liveObjectBytes = liveObjectBytes;
        this.liveType = liveObjectClass;
        this.persistableType = persistableObjectClass;

        Releaser.register(this);

    }

    @Override
    public Optional<Class<? extends IPersistStream>> optPersistableType() {
        return Optional.ofNullable(this.persistableType);
    }

    @Override
    public Class<T> getLiveType() {
        return this.liveType;
    }

    @Override
    public boolean isReleaseable() {
        return this.liveObjectBytes != null;
    }

    @Override
    public void release() {

        if (isReleaseable()) {
            Releaser.register(this.liveObject);
            this.liveObject = null;
        } else {
            //no bytes available for later retrieval --> dont release
        }

    }

    @Override
    public IArcObjectReference<T> copy() throws ArcObjectFailure {

        if (this.liveObjectBytes != null) {
            return new ArcObjectReferenceImplPersistable<>(this.liveType, this.persistableType, this.liveObjectBytes);
        } else if (this.liveObject != null) {
            return new ArcObjectReferenceImplPersistable<>(ArcObjects.copy(this.liveObject));
        } else {
            throw new ArcObjectFailure("failed to copy object because neither original byte nor original object was available", null);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public T get() throws ArcObjectFailure {

        if (this.liveObject == null && this.persistableType != null) {

            try {
                this.liveObject = (T) ArcObjects.load(this.liveObjectBytes, this.persistableType);
                Releaser.register(this); //re-register, so this instance's live object can be released
            } catch (Exception ex) {
                throw new ArcObjectFailure("failed to get live object", ex);
            }

        }
        return this.liveObject;

    }

}
