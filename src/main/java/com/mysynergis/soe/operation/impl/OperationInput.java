package com.mysynergis.soe.operation.impl;

import com.mysynergis.soe.operation.IRestOperationInput;

/**
 * accessor util to {@link IRestOperationInput} instances<b>
 *
 * @author h.fleischer
 * @since 02.09.2019
 *
 */
public class OperationInput {

    private OperationInput() {
        //no public instance
    }

    /**
     * create a new instance of {@link IRestOperationInput} from the given values
     *
     * @param capabilities
     * @param operationInput
     * @param requestProperties
     * @return
     */
    public static IRestOperationInput create(String capabilities, String operationInput, String requestProperties, String outputFormat) {
        return new OperationInputImpl(capabilities, operationInput, requestProperties, outputFormat);
    }

}
