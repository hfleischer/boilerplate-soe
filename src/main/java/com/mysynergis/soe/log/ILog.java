package com.mysynergis.soe.log;

import com.mysynergis.soe.lifecycle.ILifecycle;

/**
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public interface ILog extends ILifecycle {

    /**
     * log a severe problem, message is assumed to be present in the throwable
     *
     * @param th
     */
    void severe(Throwable th);

    /**
     * warn, message is assumed to be present in the throwable
     *
     * @param th
     */
    void warn(Throwable th);

    /**
     * convenience method for log( {@link ELogLevel#INFO}, format, params)
     *
     * @param format
     * @param params
     */
    void info(String message);

    /**
     * convenience method for log( {@link ELogLevel#FINE}, format, params)
     *
     * @param format
     * @param params
     */
    void fine(String message);

    /**
     * convenience method for log( {@link ELogLevel#VERBOSE}, format, params)
     *
     * @param format
     * @param params
     */
    void verbose(String message);

    /**
     * convenience method for log( {@link ELogLevel#DEBUG}, format, params)
     *
     * @param format
     * @param params
     */
    void debug(String message);

    /**
     * serves primararily as as sink for exception, but may also be used to
     * verbosely log those exceptions
     *
     * @param format
     * @param params
     */
    void ignored(String what, Throwable th);

}
