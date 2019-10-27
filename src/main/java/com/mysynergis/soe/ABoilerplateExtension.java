package com.mysynergis.soe;

import java.io.IOException;

import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.interop.extn.ServerObjectExtProperties;
import com.esri.arcgis.server.IServerObjectExtension;
import com.esri.arcgis.server.IServerObjectHelper;
import com.esri.arcgis.system.IPropertySet;
import com.esri.arcgis.system.IRESTRequestHandler;
import com.esri.arcgis.system.IRequestHandler2;
import com.esri.arcgis.system.IWebRequestHandler;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.InitFailure;
import com.mysynergis.soe.failure.ShutdownFailure;
import com.mysynergis.soe.helper.impl.ServerHelper;
import com.mysynergis.soe.lifecycle.ILifecycle;
import com.mysynergis.soe.lifecycle.impl.Lifecycle;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.request.ESoType;

/**
 * common superclass for SOE and SOI, ArcObjectsSDK and EnterpriseSDK<br>
 *
 * @author h.fleischer
 * @since 13.10.2019
 */
public abstract class ABoilerplateExtension implements ILifecycle, IServerObjectExtension, IRESTRequestHandler, IWebRequestHandler, IRequestHandler2 {

    public static final String SOE_NAME = "Boilerplate SOE";
    public static final String SOE_DESC = "Boilerplate SOE";
    public static final String SOI_NAME = "Boilerplate SOI";
    public static final String SOI_DESC = "Boilerplate SOI";

    private static final long serialVersionUID = -4151673763774675850L;

    /**
     * {@link ILifecycle}
     */
    @Override
    public void init(IServerObjectHelper serverObjectHelper1) {
        try {
            ServerHelper.getInstance().init(serverObjectHelper1);
            Lifecycle.getInstance().init(this);
        } catch (InitFailure | RuntimeException ex) {
            Log.getInstance().severe(new InitFailure("failed to initialize server-object-helper", ex));
        }
    }

    /**
     * get a handle on this instance's properties
     */
    @Override
    public void construct(IPropertySet propertySet) {
        try {
            ServerHelper.getInstance().construct(propertySet);
            Lifecycle.getInstance().construct(propertySet);
        } catch (ConstructFailure ex) {
            Log.getInstance().severe(ex);
            Log.getInstance().severe(new ConstructFailure("failed to construct SOI server-helper/lifecycle", ex));
        }
    }

    /**
     * shutdown() is called once when the Server Object's context is being shut down and is about to go away.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void shutdown() {
        try {
            Lifecycle.getInstance().shutdown();
            ServerHelper.getInstance().shutdown();
        } catch (ShutdownFailure ex) {
            Log.getInstance().severe(new ShutdownFailure("failed to shutdown SOI server-helper/lifecycle", ex));
        }
    }

    /**
     * determine if this type is {@link ESoType#SOE} or {@link ESoType#SOI}<br>
     * this could also be done by reflection on the {@link ServerObjectExtProperties} annotation
     * by getting the {@link ServerObjectExtProperties#interceptor()} flag
     * but has not been implemented like that for simplicity and better readability
     *
     * @return
     */
    public abstract ESoType getSoType();

    @Override
    public byte[] handleRESTRequest(String capabilities, String resource, String operation, String opInput, String outFormat, String requestProps, String[] responseProps)
            throws IOException, AutomationException {
        return getSoType().restRequestHandler().handleRESTRequest(capabilities, resource, operation, opInput, outFormat, requestProps, responseProps);
    }

    @Override
    public String getSchema() throws IOException, AutomationException {
        return getSoType().restRequestHandler().getSchema();
    }

    @Override
    public byte[] handleStringWebRequest(int httpMethod, String requestURL, String queryString, String capabilities, String requestData, String[] responseContentType, int[] respDataType)
            throws IOException, AutomationException {
        return getSoType().webRequestHandler().handleStringWebRequest(httpMethod, requestURL, queryString, capabilities, requestData, responseContentType, respDataType);
    }

    @Override
    public byte[] handleBinaryRequest2(String capabilities, byte[] request) throws IOException, AutomationException {
        return getSoType().requestHandler2().handleBinaryRequest2(capabilities, request);
    }

    @Override
    public byte[] handleBinaryRequest(byte[] request) throws IOException, AutomationException {
        return getSoType().requestHandler2().handleBinaryRequest(request);
    }

    @Override
    public String handleStringRequest(String capabilities, String request) throws IOException, AutomationException {
        return getSoType().requestHandler2().handleStringRequest(capabilities, request);
    }

}
