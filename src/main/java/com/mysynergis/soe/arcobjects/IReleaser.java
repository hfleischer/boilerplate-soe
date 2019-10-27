package com.mysynergis.soe.arcobjects;

/**
 * arcobjects releasing util<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public interface IReleaser {

    void trackObjectsInCurrentThread();

    /**
     * relase all references and objects in this instance
     *
     * @return
     */
    boolean releaseAllInCurrentThread();

    /**
     * immedialy release the object
     *
     * @param releasable
     */
    boolean releaseNow(Object releasable);

    /**
     * for testing
     *
     * @return
     */
    int getReleasableCount();

    /**
     * register an {@link IArcObjectReference} for release
     *
     * @param objectReference
     */
    void register(IArcObjectReference<?> objectReference);

    //    /**
    //     * register an arcobjects instance for release
    //     *
    //     * @param releasable
    //     */
    //    void registerInstanceForRelease(Object releasable);

    /**
     * registers the object for release, then returns the same instance<br>
     * if the input is null, nothing will be registered and null will be returned
     *
     * @param releasable
     * @return
     */
    <T> T register(final T releasable);

    //    /**
    //     * creates a new instance of the given class and registers the object for
    //     * release, then returns the created instance<br>
    //     * if the input is null, nothing will be registered and null will be returned
    //     *
    //     * @param supplierOfInstance
    //     * @return
    //     * @throws ArcObjectFailure
    //     */
    //    <T> T releasableInstance(Class<T> type) throws ArcObjectFailure;

    //    /**
    //     * creates a new instance of the given class and registers the object for
    //     * release, then returns the created instance<br>
    //     * if the input is null, nothing will be registered and null will be returned
    //     *
    //     * @param releasableClass
    //     * @return
    //     * @throws ArcObjectFailure
    //     */
    //    <P> P releasableProxy(Class<P> proxyClass, Object instanceToBeProxied) throws ArcObjectFailure;

}
