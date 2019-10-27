package com.mysynergis.soe.arcobjects.impl;

import java.util.ArrayList;
import java.util.List;

import com.esri.arcgis.system.Cleaner;
import com.mysynergis.soe.arcobjects.IArcObjectReference;
import com.mysynergis.soe.arcobjects.IReleaser;
import com.mysynergis.soe.log.impl.Log;

/**
 * simple implementaion of {@link IReleaser}<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
class ReleaserImpl implements IReleaser {

    private final List<Object> releaseableInstances;
    private final List<IArcObjectReference<?>> releaseableReferences;

    ReleaserImpl() {
        this.releaseableInstances = new ArrayList<>();
        this.releaseableReferences = new ArrayList<>();
    }

    @Override
    public boolean releaseNow(Object releasable) {
        try {
            Cleaner.release(releasable);
            return true;
        } catch (Exception ex) {
            Log.getInstance().ignored("release object", ex);
        }
        return false;
    }

    @Override
    public void trackObjectsInCurrentThread() {
        this.releaseableInstances.clear();
        try {
            Cleaner.trackObjectsInCurrentThread();
        } catch (Exception ex) {
            Log.getInstance().ignored("track all in thread", ex);
        }
    }

    @Override
    public boolean releaseAllInCurrentThread() {

        for (IArcObjectReference<?> objectReference : this.releaseableReferences) {
            objectReference.release();
        }
        this.releaseableReferences.clear();

        for (Object registered : this.releaseableInstances) {
            Releaser.releaseNow(registered);
        }
        this.releaseableInstances.clear();

        try {
            Cleaner.releaseAllInCurrentThread();
            return true;
        } catch (Exception ex) {
            Log.getInstance().ignored("release all in thread", ex);
        }
        return false;

    }

    @Override
    public int getReleasableCount() {
        return this.releaseableInstances.size() + this.releaseableReferences.size();
    }

    @Override
    public void register(IArcObjectReference<?> objectReference) {
        this.releaseableReferences.add(objectReference);
    }

    @Override
    public <T> T register(T releasable) {
        if (releasable != null) {
            this.releaseableInstances.add(releasable);
        }
        return releasable;
    }

}
