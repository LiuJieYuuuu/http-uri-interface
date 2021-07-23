package com.httpuri.iagent.exception;

/**
 * <p>HttpUri iagent args Exception</p>
 */
public class HttpUriArgumentException extends RuntimeException {
    public HttpUriArgumentException() {
        super();
    }

    public HttpUriArgumentException(String message) {
        super(message);
    }

    public HttpUriArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpUriArgumentException(Throwable cause) {
        super(cause);
    }

    protected HttpUriArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
