package com.mysynergis.soe.failure;

import com.mysynergis.soe.log.ECode;

/**
 * log message code<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class InitFailure extends ACodedFailure {

    private static final long serialVersionUID = 4316549279447939840L;

    public InitFailure(String message, Throwable cause) {
        super(message, cause, ECode.ERROR_INIT);
    }

}
