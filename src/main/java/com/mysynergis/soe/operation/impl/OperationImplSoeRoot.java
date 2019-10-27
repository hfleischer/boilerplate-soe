package com.mysynergis.soe.operation.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.esri.arcgis.server.json.JSONArray;
import com.esri.arcgis.server.json.JSONObject;
import com.esri.arcgis.systemUI.IOperation;
import com.mysynergis.soe.operation.IRestOperation;
import com.mysynergis.soe.operation.IRestOperationInput;
import com.mysynergis.soe.operation.IRestOperationOutput;
import com.mysynergis.soe.util.VoidUtil;

/**
 * implementation of {@link IOperation} for the soe root resource / operation<br>
 *
 * @author h.fleischer
 * @since 02.09.2019
 *
 */
class OperationImplSoeRoot implements IRestOperation {

    private final List<IRestOperation> operations;

    OperationImplSoeRoot(List<IRestOperation> operations) {
        this.operations = operations;
    }

    @Override
    public String getResourceName() {
        return "";
    }

    @Override
    public String getOperationName() {
        return "";
    }

    @Override
    public List<String> getParameterNames() {
        return Collections.emptyList();
    }

    @Override
    public boolean isApplicable(String resourceName, String operationName) {
        return VoidUtil.isVoid(resourceName) && VoidUtil.isVoid(operationName);
    }

    @Override
    public IRestOperationOutput handleRequest(IRestOperationInput input) throws IOException {

        JSONObject resultJo = new JSONObject();
        JSONArray operationsJa = new JSONArray();

        this.operations.stream().map(Operation::getOperationJo).forEach(operationsJa::put);
        resultJo.put("operation", operationsJa);

        //ignoring output format
        return () -> resultJo.toString().getBytes();

    }

}
