package com.mysynergis.soe.operation.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.esri.arcgis.system.IRESTRequestHandler;
import com.esri.arcgis.system.ServerUtilities;
import com.esri.arcgis.systemUI.IOperation;
import com.mysynergis.soe.helper.impl.ServerHelper;
import com.mysynergis.soe.log.ECode;
import com.mysynergis.soe.operation.IRestOperation;
import com.mysynergis.soe.operation.IRestOperationInput;
import com.mysynergis.soe.operation.IRestOperationOutput;

/**
 * implementation of {@link IOperation} for the default soi delgation behaviour<br>
 *
 * @author h.fleischer
 * @since 02.09.2019
 *
 */
class RestOperationImplSoiDelegate implements IRestOperation {

    private final String resourceName;
    private final String operationName;

    RestOperationImplSoiDelegate(String resourceName, String operationName) {
        this.resourceName = resourceName;
        this.operationName = operationName;
    }

    @Override
    public String getResourceName() {
        return this.resourceName;
    }

    @Override
    public String getOperationName() {
        return this.operationName;
    }

    @Override
    public List<String> getParameterNames() {
        return Collections.emptyList();
    }

    @Override
    public IRestOperationOutput handleRequest(IRestOperationInput input) throws IOException {

        //now delegate to the actual request handler (which may already get an altered request)
        IRESTRequestHandler restRequestHandler = ServerHelper.getInterceptorHelper().findRestRequestHandlerDelegate(ServerHelper.getServerObject());
        String[] responseProperties = new String[] { "" };
        if (restRequestHandler != null) {
            byte[] jsonResponseBytes = restRequestHandler.handleRESTRequest(input.getCapabilities(), getResourceName(), getOperationName(), input.getOperationInput(), input.getOutputFormat(),
                    input.getRequestProperties(), responseProperties);
            return () -> jsonResponseBytes;
        } else {
            return () -> ServerUtilities.sendError(ECode.HTTP_INTERNAL_SERVER_ERROR.getCode(), "failed to find delegate handler", responseProperties).getBytes();
        }

    }

}
