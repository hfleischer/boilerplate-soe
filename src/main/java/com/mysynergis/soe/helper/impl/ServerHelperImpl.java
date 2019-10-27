package com.mysynergis.soe.helper.impl;

import java.io.IOException;

import com.esri.arcgis.server.IServerObject;
import com.esri.arcgis.server.IServerObjectHelper;
import com.esri.arcgis.server.SOIHelper;
import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.arcobjects.impl.Releaser;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.ShutdownFailure;
import com.mysynergis.soe.helper.IServerHelper;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.util.ServerUtil;

/**
 * implementaion of {@link IServerHelper}<br>
 *
 * @author h.fleischer
 * @since 08.01.2019
 *
 */
class ServerHelperImpl implements IServerHelper {

    private IServerObjectHelper serverObjectHelper;
    private SOIHelper interceptorHelper;

    ServerHelperImpl() {
        // lifecycle is starting later
    }

    @Override
    public IServerObject getServerObject() throws IOException {
        if (this.serverObjectHelper != null) {
            return this.serverObjectHelper.getServerObject();
        } else {
            return null;
        }
    }

    @Override
    public SOIHelper getInterceptorHelper() {
        return this.interceptorHelper;
    }

    @Override
    public void init(IServerObjectHelper serverObjectHelper1) {

        Log.getInstance().info("initializing SOI/SOE server helper ...");
        this.serverObjectHelper = serverObjectHelper1;
        this.interceptorHelper = createSoiHelper();

    }

    /**
     * not static for testability
     *
     * @return
     * @throws IOException
     */
    @SuppressWarnings("static-method")
    protected SOIHelper createSoiHelper() {
        return ServerUtil.createSoiHelper();
    }

    @Override
    public void construct(IPropertySet propertySet) throws ConstructFailure {
        Log.getInstance().info("constructing SOI/SOE server helper (no-op) ...");
    }

    @Override
    public void shutdown() throws ShutdownFailure {

        // logging is already gone when this method runs, so what

        Releaser.releaseNow(this.serverObjectHelper);
        Releaser.releaseNow(this.interceptorHelper);
        this.serverObjectHelper = null;
        this.interceptorHelper = null;

    }

}
