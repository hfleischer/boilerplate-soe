package com.mysynergis.soe.failure;

import com.mysynergis.soe.log.ECode;

/**
 * exception specific to problems during {@link IWriter#write(Object, Object))} calls<br>
 *
 * @author h.fleischer
 * @since 04.09.2019
 *
 */
public class WriteFailure extends ACodedFailure {

    private static final long serialVersionUID = 668855205927658874L;

    public WriteFailure(String type, Throwable cause) {
        super("failed to write " + type, cause, ECode.ERROR_WRITE);
    }

}
