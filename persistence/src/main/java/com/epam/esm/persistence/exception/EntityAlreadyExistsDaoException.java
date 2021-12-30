package com.epam.esm.persistence.exception;

/**
 * The EntityAlreadyExistsDaoException class.
 */
public class EntityAlreadyExistsDaoException extends Exception {
    /**
     * Instantiates a new EntityAlreadyExistsDaoException.
     */
    public EntityAlreadyExistsDaoException() {
    }

    /**
     * Instantiates a new EntityAlreadyExistsDaoException.
     *
     * @param message the message
     */
    public EntityAlreadyExistsDaoException(String message) {
        super(message);
    }

    /**
     * Instantiates a new EntityAlreadyExistsDaoException.
     *
     * @param message the message
     * @param cause   the cause
     */
    public EntityAlreadyExistsDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new EntityAlreadyExistsDaoException.
     *
     * @param cause the cause
     */
    public EntityAlreadyExistsDaoException(Throwable cause) {
        super(cause);
    }
}
