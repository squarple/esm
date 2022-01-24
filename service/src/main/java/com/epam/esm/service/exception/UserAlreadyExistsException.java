package com.epam.esm.service.exception;

/**
 * The UserAlreadyExistsException class.
 */
public class UserAlreadyExistsException extends Exception {
    private final String username;

    /**
     * Instantiates a new UserAlreadyExistsException.
     *
     * @param username the username
     */
    public UserAlreadyExistsException(String username) {
        this.username = username;
    }

    /**
     * Instantiates a new UserAlreadyExistsException.
     *
     * @param message  the message
     * @param username the username
     */
    public UserAlreadyExistsException(String message, String username) {
        super(message);
        this.username = username;
    }

    /**
     * Instantiates a new UserAlreadyExistsException.
     *
     * @param message  the message
     * @param cause    the cause
     * @param username the username
     */
    public UserAlreadyExistsException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }

    /**
     * Instantiates a new UserAlreadyExistsException.
     *
     * @param cause    the cause
     * @param username the username
     */
    public UserAlreadyExistsException(Throwable cause, String username) {
        super(cause);
        this.username = username;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
