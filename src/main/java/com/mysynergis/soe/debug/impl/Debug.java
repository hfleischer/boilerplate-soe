package com.mysynergis.soe.debug.impl;

import com.mysynergis.soe.debug.IDebug;
import com.mysynergis.soe.lifecycle.ILifecycle;

/**
 * accessor util to {@link IDebug} instances<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class Debug {

    private static final IDebug INSTANCE = new DebugImpl();

    private Debug() {
        // no public instance
    }

    /**
     * create an instance of {@link ILifecycle}
     *
     * @return
     */
    public static IDebug getInstance() {
        return INSTANCE;
    }

}
