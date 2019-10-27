package com.mysynergis.soe.failure;

import com.mysynergis.soe.log.ECode;

/**
 * exception specific to arcobjects related problems<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class ArcObjectFailure extends ACodedFailure {

    private static final long serialVersionUID = 2969322376468183559L;

    public ArcObjectFailure(String message, Throwable cause) {
        super(message, cause, ECode.ERROR_ARCOBJECTS);
    }

    protected ArcObjectFailure(String message, Throwable cause, ECode code) {
        super(message, cause, code);
    }

}
