package com.mysynergis.soe.operation.impl;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.operation.IRestOperationInput;

class OperationInputImpl implements IRestOperationInput {

    private final String capabilities;
    private final String requestProperties;
    private final JSONObject operationInputJo;
    private final String outputFormat;

    OperationInputImpl(String capabilities, String operationInput, String requestProperties, String outputFormat) {
        this.capabilities = capabilities;
        this.operationInputJo = new JSONObject(operationInput);
        this.requestProperties = requestProperties;
        this.outputFormat = outputFormat;
    }

    @Override
    public String getCapabilities() {
        return this.capabilities;
    }

    @Override
    public String getOperationInput() {
        return this.operationInputJo.toString();
    }

    @Override
    public String getRequestProperties() {
        return this.requestProperties;
    }

    @Override
    public String getOutputFormat() {
        return this.outputFormat;
    }

}
