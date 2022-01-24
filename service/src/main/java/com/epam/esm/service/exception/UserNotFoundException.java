package com.epam.esm.service.exception;

/**
 * The UserNotFoundException class.
 */
public class UserNotFoundException extends Exception {
    private final String username;

    /**
     * Instantiates a new UserNotFoundException.
     *
     * @param username the username
     */
    public UserNotFoundException(String username) {
        this.username = username;
    }

    /**
     * Instantiates a new UserNotFoundException.
     *
     * @param message  the message
     * @param username the username
     */
    public UserNotFoundException(String message, String username) {
        super(message);
        this.username = username;
    }

    /**
     * Instantiates a new UserNotFoundException.
     *
     * @param message  the message
     * @param cause    the cause
     * @param username the username
     */
    public UserNotFoundException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }

    /**
     * Instantiates a new UserNotFoundException.
     *
     * @param cause    the cause
     * @param username the username
     */
    public UserNotFoundException(Throwable cause, String username) {
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
