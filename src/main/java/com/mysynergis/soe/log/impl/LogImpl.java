package com.mysynergis.soe.log.impl;

import java.io.IOException;

import com.esri.arcgis.system.IPropertySet;
import com.esri.arcgis.system.ServerUtilities;
import com.mysynergis.soe.arcobjects.impl.Releaser;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.log.ECode;
import com.mysynergis.soe.log.ELogLevel;
import com.mysynergis.soe.log.ICoded;
import com.mysynergis.soe.log.ILog;

/**
 * simple logging utility
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
class LogImpl implements ILog {

    /**
     * reference to the actual arcobject {@link ILog} instance
     */
    private com.esri.arcgis.system.ILog serverLog;

    LogImpl() {
        // server log is initialized later
    }

    /**
     * called when the SOE/SOI is constructed
     */
    @Override
    public void construct(IPropertySet propertySet) throws ConstructFailure {
        Log.getInstance().info("constructing SOI/SOE log ...");
        this.serverLog = ServerUtilities.getServerLogger();
    }

    /**
     * called when SOE/SOI shuts down
     */
    @Override
    public void shutdown() {
        Log.getInstance().info("shutting down SOI/SOE log ...");
        Releaser.releaseNow(this.serverLog);
        this.serverLog = null;
    }

    /**
     * write to the underlying arcobjects server log
     *
     * @param logLevel
     * @param esriCode
     * @param th
     * @param format
     * @param params
     * @throws IOException
     */
    public void log(ELogLevel logLevel, ECode esriCode, String message) {
        try {
            this.serverLog.addMessage(logLevel.getEsriLevel(), esriCode.getCode(), message);
        } catch (Exception ex) {
            ignored("because currently only the server log is used an nothing else can be done about this", ex);
            // not much that can be done about this..., ignore for now, maybe dump to file
        }
    }

    @Override
    public void severe(Throwable th) {
        if (th instanceof ICoded) {
            log(ELogLevel.SEVERE, ((ICoded) th).getCode(), th.getMessage());
        } else {
            log(ELogLevel.SEVERE, ECode.ERROR_UNKNOWN, th.getMessage());
        }
    }

    @Override
    public void warn(Throwable th) {
        if (th instanceof ICoded) {
            log(ELogLevel.WARNING, ((ICoded) th).getCode(), th.getMessage());
        } else {
            log(ELogLevel.WARNING, ECode.ERROR_UNKNOWN, th.getMessage());
        }
    }

    @Override
    public void info(String message) {
        log(ELogLevel.INFO, ECode.OK, message);
    }

    @Override
    public void fine(String message) {
        log(ELogLevel.FINE, ECode.OK, message);
    }

    @Override
    public void verbose(String message) {
        log(ELogLevel.VERBOSE, ECode.OK, message);
    }

    @Override
    public void debug(String message) {
        log(ELogLevel.DEBUG, ECode.OK, message);
    }

    @Override
    public void ignored(String what, Throwable th) {
        // no implementation for now
        // this method is currently called by log, so calling log from here would lead
        // to stack overflow!!
    }

}
