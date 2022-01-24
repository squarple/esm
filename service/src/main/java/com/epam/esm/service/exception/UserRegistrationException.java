package com.epam.esm.service.exception;

/**
 * The type UserRegistrationException class.
 */
public class UserRegistrationException extends Exception {
    /**
     * Instantiates a new UserRegistrationException.
     */
    public UserRegistrationException() {
    }

    /**
     * Instantiates a new UserRegistrationException.
     *
     * @param message the message
     */
    public UserRegistrationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new UserRegistrationException.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new UserRegistrationException.
     *
     * @param cause the cause
     */
    public UserRegistrationException(Throwable cause) {
        super(cause);
    }
}
