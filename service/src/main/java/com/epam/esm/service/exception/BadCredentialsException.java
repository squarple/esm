package com.epam.esm.service.exception;

/**
 * The WrongCredentialsException.
 */
public class BadCredentialsException extends Exception {
    /**
     * Instantiates a new WrongCredentialsException.
     */
    public BadCredentialsException() {
    }

    /**
     * Instantiates a new WrongCredentialsException.
     *
     * @param message the message
     */
    public BadCredentialsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new WrongCredentialsException.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new WrongCredentialsException.
     *
     * @param cause the cause
     */
    public BadCredentialsException(Throwable cause) {
        super(cause);
    }
}
