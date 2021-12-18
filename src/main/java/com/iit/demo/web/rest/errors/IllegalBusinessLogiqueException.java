package com.iit.demo.web.rest.errors;

 
public final class IllegalBusinessLogiqueException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IllegalBusinessLogiqueException() {
        super();
    }

    public IllegalBusinessLogiqueException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IllegalBusinessLogiqueException(final String message) {
        super(message);
    }

    public IllegalBusinessLogiqueException(final Throwable cause) {
        super(cause);
    }

    public IllegalBusinessLogiqueException(final String message, final Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
         super(message, cause,enableSuppression,writableStackTrace);
    }
}
