package com.iit.demo.util;


import com.iit.demo.web.rest.errors.IllegalBusinessLogiqueException;
import com.iit.demo.web.rest.errors.MyResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Simple static methods to be called at the start of your own methods to verify
 * correct arguments and state. If the Precondition fails, an {@link HttpStatus}
 * code is thrown
 */
public final class Preconditions {

    private final static Logger LOG = LoggerFactory.getLogger(Preconditions.class);

    private Preconditions() {
        throw new AssertionError();
    }

    // API
    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression has value true if found, otherwise false
     * @param message
     * @throws MyResourceNotFoundException if expression is false, means value
     * not found.
     */
    public static void checkBusinessLogique(final boolean expression, String message) {
        if (!expression) {

            throw new IllegalBusinessLogiqueException(message);

        }
    }

    public static void checkBusinessLogique(final boolean expression, String message, String... detailMessage) {
        if (!expression) {
            String detailMsgBuilder = String.join(";", detailMessage);
            IllegalBusinessLogiqueException X = new IllegalBusinessLogiqueException(message, new Throwable(detailMsgBuilder));
            LOG.error(message, new Throwable(detailMsgBuilder));
            throw X;
        }
    }

    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param <T>
     * @param resource
     * @return
     * @throws MyResourceNotFoundException if expression is false, means value
     * not found.
     */
    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new MyResourceNotFoundException();
        }

        return resource;
    }

    public static <T> T checkFound(final T resource, String message) {
        if (resource == null) {
            throw new MyResourceNotFoundException(message);
        }

        return resource;
    }

}
