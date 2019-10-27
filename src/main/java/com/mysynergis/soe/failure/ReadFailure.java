package com.mysynergis.soe.failure;

import com.mysynergis.soe.log.ECode;

/**
 * exception specific to problems during {@link IReader#read(Object)} calls<br>
 *
 * @author h.fleischer
 * @since 03.09.2019
 *
 */
public class ReadFailure extends ACodedFailure {

    private static final long serialVersionUID = -1946745741529182034L;

    public ReadFailure(String type, Throwable cause) {
        super("failed to read " + type, cause, ECode.ERROR_READ);
    }

}
