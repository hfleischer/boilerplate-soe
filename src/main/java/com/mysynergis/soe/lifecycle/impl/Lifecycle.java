package com.mysynergis.soe.lifecycle.impl;

import com.mysynergis.soe.lifecycle.ILifecycle;
import com.mysynergis.soe.lifecycle.ILifecycleEx;

/**
 * the central access point to {@link ILifecycle}<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class Lifecycle {

    private static LifecycleImpl instance;

    private Lifecycle() {
        // no public instance
    }

    /**
     * create an instance of {@link ILifecycle}
     *
     * @return
     */
    public static ILifecycleEx getInstance() {
        if (instance == null) {
            instance = new LifecycleImpl();
        }
        return instance;
    }

}
