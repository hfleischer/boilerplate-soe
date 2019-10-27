package com.mysynergis.soe.operation.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.esri.arcgis.system.ServerUtilities;
import com.esri.arcgis.systemUI.IOperation;
import com.mysynergis.soe.failure.OperationFailure;
import com.mysynergis.soe.operation.IRestOperation;
import com.mysynergis.soe.operation.IRestOperationInput;
import com.mysynergis.soe.operation.IRestOperationOutput;

/**
 * implementation of {@link IOperation} meant for sending an error message<br>
 *
 * @author h.fleischer
 * @since 03.09.2019
 *
 */
class OperationImplFailure implements IRestOperation {

    private final OperationFailure failure;

    OperationImplFailure(String message, Exception failure) {
        this.failure = new OperationFailure(message, failure);
    }

    @Override
    public IRestOperationOutput handleRequest(IRestOperationInput input) throws IOException {
        return () -> ServerUtilities.sendError(this.failure.getCode().getCode(), this.failure.getMessage(), this.failure.getDetails()).getBytes();
    }

    @Override
    public String getResourceName() {
        return "failure_no_actual_resource";
    }

    @Override
    public String getOperationName() {
        return "failure_no_actual_operation";
    }

    @Override
    public List<String> getParameterNames() {
        return Collections.emptyList();
    }

}
