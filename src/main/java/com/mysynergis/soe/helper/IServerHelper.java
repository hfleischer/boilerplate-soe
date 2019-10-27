package com.mysynergis.soe.helper;

import java.io.IOException;

import com.esri.arcgis.carto.IMapServer;
import com.esri.arcgis.server.IServerObject;
import com.esri.arcgis.server.IServerObjectHelper;
import com.esri.arcgis.server.SOIHelper;
import com.mysynergis.soe.failure.InitFailure;
import com.mysynergis.soe.lifecycle.ILifecycle;

/**
 * this definition provides access to the utilities provided in the SOE/SOE<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public interface IServerHelper extends ILifecycle {

    /**
     * called during SOE/SOI init<br>
     * implementation shall use this call to set up its internal references
     *
     * @param serverObjectHelper1
     * @throws InitFailure
     */
    void init(IServerObjectHelper serverObjectHelper1) throws InitFailure;

    /**
     * get the server-object, this will return an {@link IMapServer} instance
     *
     * @return
     * @throws IOException
     */
    IServerObject getServerObject() throws IOException;

    SOIHelper getInterceptorHelper();

}
