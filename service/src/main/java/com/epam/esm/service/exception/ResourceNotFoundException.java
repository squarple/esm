package com.epam.esm.service.exception;

/**
 * The ResourceNotFoundException class.
 */
public class ResourceNotFoundException extends Exception {
    /**
     * Instantiates a new ResourceNotFoundException.
     */
    public ResourceNotFoundException() {
    }

    /**
     * Instantiates a new ResourceNotFoundException.
     *
     * @param message the message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new ResourceNotFoundException.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new ResourceNotFoundException.
     *
     * @param cause the cause
     */
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
