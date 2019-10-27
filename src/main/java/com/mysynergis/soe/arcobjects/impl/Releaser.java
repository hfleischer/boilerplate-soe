package com.mysynergis.soe.arcobjects.impl;

import java.util.function.Supplier;

import com.mysynergis.soe.arcobjects.IArcObjectReference;
import com.mysynergis.soe.arcobjects.IReleaser;

/**
 * utility handling cleanup of arcobject instances
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class Releaser {

    /**
     * the class can be altered by a test wishing to alter the release behaviour
     */
    protected static Supplier<? extends IReleaser> supplierOfReleaseInstance = ReleaserImpl::new;

    /**
     * one releaser per thread
     */
    protected static final ThreadLocal<IReleaser> THREAD_RELEASER = ThreadLocal.withInitial(() -> {
        return supplierOfReleaseInstance.get();
    });

    private Releaser() {
        // UTIL CLASS
    }

    /**
     * for testing
     *
     * @return
     */
    public static int getReleasableCount() {
        return THREAD_RELEASER.get().getReleasableCount();
    }

    /**
     * immedialy release the object
     *
     * @param releasable
     */
    public static boolean releaseNow(final Object releasable) {
        return THREAD_RELEASER.get().releaseNow(releasable);
    }

    /**
     * @param soeContext Logging
     *
     */
    public static void trackObjectsInCurrentThread() {
        THREAD_RELEASER.get().trackObjectsInCurrentThread();
    }

    /**
     * @param soeContext Logging
     *
     */
    public static boolean releaseAllInCurrentThread() {
        return THREAD_RELEASER.get().releaseAllInCurrentThread();
    }

    /**
     * register an {@link IArcObjectReference} for release
     *
     * @param objectReference
     */
    public static void register(IArcObjectReference<?> objectReference) {
        THREAD_RELEASER.get().register(objectReference);
    }

    /**
     * registers the object for release, then returns the same instance<br>
     * if the input is null, nothing will be registered and null will be returned
     *
     * @param releasable
     * @return
     */
    public static <T> T register(final T releasable) {
        return THREAD_RELEASER.get().register(releasable);
    }

}
