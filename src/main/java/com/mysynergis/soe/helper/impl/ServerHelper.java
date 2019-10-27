package com.mysynergis.soe.helper.impl;

import java.io.IOException;

import com.esri.arcgis.server.IServerObject;
import com.esri.arcgis.server.SOIHelper;
import com.mysynergis.soe.helper.IServerHelper;

/**
 *
 * @author h.fleischer
 * @since 08.01.2019
 *
 */
public class ServerHelper {

    protected static final IServerHelper INSTANCE = new ServerHelperImpl();

    private ServerHelper() {
        // no public instance
    }

    /**
     * create an instance of {@link IServerHelper}
     *
     * @return
     */
    public static IServerHelper getInstance() {
        return INSTANCE;
    }

    /**
     * get the {@link IServerObject} from the singleton {@link IServerHelper}
     * instance
     *
     * @return
     * @throws IOException
     */
    public static IServerObject getServerObject() throws IOException {
        return getInstance().getServerObject();
    }

    /**
     * get the {@link SOIHelper} from the singleton {@link IServerHelper} instance
     *
     * @return
     * @throws IOException
     */
    public static SOIHelper getInterceptorHelper() throws IOException {
        return getInstance().getInterceptorHelper();
    }

}
