package com.mysynergis.soe.request;

import java.io.IOException;

import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.IRESTRequestHandler;
import com.mysynergis.soe.operation.IRestOperation;
import com.mysynergis.soe.operation.IRestOperationInput;
import com.mysynergis.soe.operation.IRestOperations;
import com.mysynergis.soe.operation.impl.Operation;
import com.mysynergis.soe.operation.impl.OperationInput;

/**
 * generic implementation of {@link IRestOperation} for both SOE and SOI<br>
 * the difference in SOE and SOI handling is the {@link IRestOperations} implementation provided by the {@link ESoType} constructor argument<br>
 *
 * @author h.fleischer
 * @since 13.10.2019
 *
 */
public class RestRequestHandler implements IRESTRequestHandler {

    private static final long serialVersionUID = 642640549012440152L;

    /**
     * the so-type of this handler
     */
    private final ESoType soType;

    public RestRequestHandler(ESoType soType) {
        this.soType = soType;
    }

    @Override
    public byte[] handleRESTRequest(String capabilities, String resource, String operation, String opInput, String outFormat, String requestProps, String[] responseProps) throws IOException {

        //get an operation suitable for this so-type (SOE may not have an operation, SOI will always try to find at least a delegate operation)
        IRestOperation restOperation = this.soType.getRestOperations().optOperation(resource, operation).orElseGet(Operation::noOpError);
        IRestOperationInput operationInput = null;
        try {
            operationInput = OperationInput.create(capabilities, opInput, requestProps, outFormat);
            return restOperation.handleRequest(operationInput).getResponseBytes();
        } catch (Exception ex) {
            return Operation.error("failed to handle " + this.soType.name() + "-rest-request", ex).handleRequest(operationInput).getResponseBytes();
        }

    }

    @Override
    public String getSchema() throws IOException, AutomationException {
        return this.soType.getRestOperations().getSchema();
    }

}
