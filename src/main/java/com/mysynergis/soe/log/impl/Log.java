package com.mysynergis.soe.log.impl;

import com.mysynergis.soe.log.ILog;

/**
 * accessor utility to {@link ILog} instances
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class Log {

    private static final ILog INSTANCE = new LogImpl();

    private Log() {
        // no public instance
    }

    /**
     * create an instance of {@link ILog}
     *
     * @return
     */
    public static ILog getInstance() {
        return INSTANCE;
    }

}
