package com.mysynergis.soe.log;

/**
 * log message code<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public enum ECode {

    OK(200),
    ERROR_UNKNOWN(2500),
    ERROR_INIT(2501),
    ERROR_CONSTRUCT(2502),
    ERROR_SHUTDOWN(2503),
    ERROR_ARCOBJECTS(2504),
    ERROR_PARSE(2505),
    ERROR_OPERATION(2506),
    ERROR_READ(2507),
    ERROR_WRITE(2508),

    HTTP_FORBIDDEN(403),
    HTTP_INTERNAL_SERVER_ERROR(500);

    private final int code;

    ECode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
