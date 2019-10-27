package com.mysynergis.soe.operation.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.server.json.JSONArray;
import com.esri.arcgis.server.json.JSONObject;
import com.esri.arcgis.system.IPropertySet;
import com.esri.arcgis.system.ServerUtilities;
import com.esri.arcgis.systemUI.IOperation;
import com.mysynergis.soe.config.IOperationConfig;
import com.mysynergis.soe.config.impl.Configuration;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.ShutdownFailure;
import com.mysynergis.soe.lifecycle.ILifecycle;
import com.mysynergis.soe.operation.IRestOperation;
import com.mysynergis.soe.operation.IRestOperations;
import com.mysynergis.soe.request.ESoType;
import com.mysynergis.soe.util.VoidUtil;

/**
 * implementation of {@link IRestOperations}, holding an internal list of {@link IOperation} instances which are constructed during the {@link ILifecycle#construct(IPropertySet)} phase<br>
 *
 * @author h.fleischer
 * @since 02.09.2019
 *
 */
public class RestOperationsImplSoe implements IRestOperations {

    private final List<IRestOperation> operations;

    public RestOperationsImplSoe() {
        this.operations = new ArrayList<>();
    }

    @Override
    public void construct(IPropertySet propertySet) throws ConstructFailure {
        for (IOperationConfig operationConfig : Configuration.getInstance().getOperationConfigList()) {
            Operation.optRestOperation(operationConfig).ifPresent(this.operations::add);
        }
    }

    @Override
    public Optional<IRestOperation> optOperation(String resourceName, String operationName) {
        if (VoidUtil.isVoid(resourceName) && VoidUtil.isVoid(operationName)) {
            return Optional.of(new OperationImplSoeRoot(this.operations));
        } else {
            return getOperations().stream().filter(op -> op.isApplicable(resourceName, operationName)).findFirst();
        }
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

        JSONObject rootResourceJo = ServerUtilities.createResource("SynSoe", "SynSoe", false, false);

        //operations as configured
        JSONArray rootOpJa = new JSONArray();
        ESoType.SOE.getRestOperations().getOperations().stream().map(Operation::getOperationJo).forEach(rootOpJa::put);
        rootResourceJo.put("operations", rootOpJa);

        return rootResourceJo.toString();

    }

}
