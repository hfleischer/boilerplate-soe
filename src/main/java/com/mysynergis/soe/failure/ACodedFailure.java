package com.mysynergis.soe.failure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mysynergis.soe.log.ECode;
import com.mysynergis.soe.log.ICoded;

/**
 * extension of {@link IOException} holding a specific error code that can be used for logging<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public abstract class ACodedFailure extends IOException implements ICoded {

    private static final long serialVersionUID = -393679694895369543L;

    private static final int MAX_CAUSE_COUNT = 10;

    private final String message;
    private final ECode code;

    public ACodedFailure(String message, Throwable cause, ECode defaultCode) {
        super(message, cause);
        this.message = message;
        if (cause instanceof ICoded) {
            this.code = ((ICoded) cause).getCode();
        } else {
            this.code = defaultCode;
        }
    }

    public String[] getDetails() {

        List<String> details = new ArrayList<>();
        Throwable cause = this;
        int causeCount = 0;
        while (cause.getCause() != null && !cause.getCause().equals(cause) && causeCount < MAX_CAUSE_COUNT) {
            causeCount++;
            cause = cause.getCause();
            if (cause instanceof ACodedFailure) {
                ACodedFailure codedCause = (ACodedFailure) cause;
                details.add(String.format("%s (code: %s)", codedCause.message, codedCause.code.getCode()));
            } else {
                details.add(cause.getMessage());
            }
        }
        return details.stream().toArray(String[]::new);

    }

    @Override
    public ECode getCode() {
        return this.code;
    }

}
