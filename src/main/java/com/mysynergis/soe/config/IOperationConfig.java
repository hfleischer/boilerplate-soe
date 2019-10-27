package com.mysynergis.soe.config;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.operation.IRestOperation;

/**
 * definition for types holding configurations that may be used to construc {@link IRestOperation} instances<br>
 *
 * @author h.fleischer
 * @since 15.10.2019
 *
 */
public interface IOperationConfig {

    /**
     * get the configured type (must resolve to a class assignable from {@link IRestOperationFactory})
     *
     * @return
     */
    String getOperationType();

    /**
     * get the subconfiguration for the specific operation type
     *
     * @return
     */
    JSONObject getOperationDefs();

}
