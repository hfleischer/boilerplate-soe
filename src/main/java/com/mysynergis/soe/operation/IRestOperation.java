package com.mysynergis.soe.operation;

import java.io.IOException;
import java.util.List;

import com.esri.arcgis.systemUI.IOperation;

/**
 * definition for types that associate {@link IOperation} behaviour with a defined name<br>
 *
 * @author h.fleischer
 * @since 02.09.2019
 *
 */
public interface IRestOperation {

    /**
     * get the resource name that this instance is associated with<br>
     *
     * @return
     */
    String getResourceName();

    /**
     * get the operationName that this instance is associated with
     *
     * @return
     */
    String getOperationName();

    /**
     * get a list of string expected as input to this operation<br>
     *
     * @return
     */
    List<String> getParameterNames();

    /**
     * let the operation decide if it is applicable to to given resourceName and operationname
     *
     * @param resourceName
     * @param operationName
     * @return
     */
    default boolean isApplicable(String resourceName, String operationName) {
        return getResourceName().equals(resourceName) && getOperationName().equals(operationName);
    }

    /**
     * String capabilities, String resourceName, String operationName, String operationInputRaw, String outputFormat, String requestProperties,
     * String[] responseProperties
     *
     * @return
     * @throws IOException
     */
    public IRestOperationOutput handleRequest(IRestOperationInput input) throws IOException;

}
