package com.mysynergis.soe.failure;

import com.esri.arcgis.systemUI.IOperation;
import com.mysynergis.soe.log.ECode;

/**
 * exceptions specific to problems while handling an {@link IOperation}<br>
 *
 * @author h.fleischer
 * @since 03.09.2019
 *
 */
public class OperationFailure extends ACodedFailure {

    private static final long serialVersionUID = -1946745741529182034L;

    public OperationFailure(String message, Throwable cause) {
        super(message, cause, ECode.ERROR_OPERATION);
    }

}
