package com.mysynergis.soe.log;

/**
 * possible log levels<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public enum ELogLevel {

    SEVERE(1), // error
    WARNING(2), // warn
    INFO(3), // info
    FINE(4), // fine
    VERBOSE(5), // debug
    DEBUG(100); // trace

    private final int esriLevel;

    ELogLevel(int esriLevel) {
        this.esriLevel = esriLevel;
    }

    public int getEsriLevel() {
        return this.esriLevel;
    }

}
