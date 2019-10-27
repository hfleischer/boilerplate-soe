package com.mysynergis.soe.operation.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.IPropertySet;
import com.esri.arcgis.system.IRESTRequestHandler;
import com.esri.arcgis.systemUI.IOperation;
import com.mysynergis.soe.config.IConfiguration;
import com.mysynergis.soe.config.IOperationConfig;
import com.mysynergis.soe.config.impl.Configuration;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.ShutdownFailure;
import com.mysynergis.soe.helper.impl.ServerHelper;
import com.mysynergis.soe.lifecycle.ILifecycle;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.operation.IRestOperation;
import com.mysynergis.soe.operation.IRestOperations;

/**
 * implementation of {@link IRestOperations}, holding an internal list of {@link IOperation} instances which are constructed during the {@link ILifecycle#construct(IPropertySet)} phase<br>
 *
 * @author h.fleischer
 * @since 02.09.2019
 *
 */
public class RestOperationsImplSoi implements IRestOperations {

    private final List<IRestOperation> operations;

    public RestOperationsImplSoi() {
        this.operations = new ArrayList<>();
    }

    /**
     * try to load instances of {@link IRestOperation} from the types provided by {@link IConfiguration#getOperationList()}
     */
    @Override
    public void construct(IPropertySet propertySet) throws ConstructFailure {
        for (IOperationConfig operationConfig : Configuration.getInstance().getOperationConfigList()) {
            Operation.optRestOperation(operationConfig).ifPresent(this.operations::add);
        }
    }

    /**
     * TODO improvement, create possibility to have single {@link IRestOperation} handlers respond to multiple resources and operations
     * solution could be to configure factories, then let those factories decide for applicability, and let the factory construct the actual operations
     */
    @Override
    public Optional<IRestOperation> optOperation(String resourceName, String operationName) {
        IRestOperation operation = getOperations().stream().filter(op -> op.isApplicable(resourceName, operationName)).findFirst()
                .orElseGet(() -> new RestOperationImplSoiDelegate(resourceName, operationName));
        return Optional.of(operation);
    }

    @Override
    public void shutdown() throws ShutdownFailure {
        //maybe take care of operation cleanup at this time
    }

    @Override
    public List<IRestOperation> getOperations() {
        return Collections.unmodifiableList(this.operations);
    }

    /**
     * This method is called to handle schema requests for custom SOE's.
     *
     * @return the schema as String
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws AutomationException the automation exception
     */
    @Override
    public String getSchema() throws IOException {
        Log.getInstance().info("request received in SynSoiRules for getSchema");
        IRESTRequestHandler restRequestHandler = ServerHelper.getInterceptorHelper().findRestRequestHandlerDelegate(ServerHelper.getServerObject());
        if (restRequestHandler != null) {
            return restRequestHandler.getSchema();
        }
        return null;
    }

}
