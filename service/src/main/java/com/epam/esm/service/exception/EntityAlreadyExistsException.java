package com.epam.esm.service.exception;

/**
 * The EntityAlreadyExistsException class.
 */
public class EntityAlreadyExistsException extends Exception {
    /**
     * Instantiates a new EntityAlreadyExistsException.
     */
    public EntityAlreadyExistsException() {
    }

    /**
     * Instantiates a new EntityAlreadyExistsException.
     *
     * @param message the message
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new EntityAlreadyExistsException.
     *
     * @param message the message
     * @param cause   the cause
     */
    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new EntityAlreadyExistsException.
     *
     * @param cause the cause
     */
    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
