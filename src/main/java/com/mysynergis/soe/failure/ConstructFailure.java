package com.mysynergis.soe.failure;

import com.mysynergis.soe.lifecycle.ILifecycle;
import com.mysynergis.soe.log.ECode;

/**
 * exceptions specific to problems during the {@link ILifecycle#construct(com.esri.arcgis.system.IPropertySet)} phase<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class ConstructFailure extends ACodedFailure {

    private static final long serialVersionUID = -1946745741529182034L;

    public ConstructFailure(String message, Throwable cause) {
        super(message, cause, ECode.ERROR_CONSTRUCT);
    }

}
