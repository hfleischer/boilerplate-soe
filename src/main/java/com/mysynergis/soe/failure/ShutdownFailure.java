package com.mysynergis.soe.failure;

import com.mysynergis.soe.lifecycle.ILifecycle;
import com.mysynergis.soe.log.ECode;

/**
 * exception type specific to errors during the {@link ILifecycle#shutdown()}
 * phase<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class ShutdownFailure extends ACodedFailure {

    private static final long serialVersionUID = -2166782279201174398L;

    public ShutdownFailure(String message, Throwable cause) {
        super(message, cause, ECode.ERROR_SHUTDOWN);
    }

}
