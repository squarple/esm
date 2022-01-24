package com.epam.esm.service.exception;

/**
 * The ForbiddenActionException class.
 */
public class ForbiddenActionException extends Exception {
    private final Long id;

    /**
     * Instantiates a new ForbiddenActionException.
     *
     * @param id the id
     */
    public ForbiddenActionException(Long id) {
        this.id = id;
    }

    /**
     * Instantiates a new ForbiddenActionException.
     *
     * @param message the message
     * @param id      the id
     */
    public ForbiddenActionException(String message, Long id) {
        super(message);
        this.id = id;
    }

    /**
     * Instantiates a new ForbiddenActionException.
     *
     * @param message the message
     * @param cause   the cause
     * @param id      the id
     */
    public ForbiddenActionException(String message, Throwable cause, Long id) {
        super(message, cause);
        this.id = id;
    }

    /**
     * Instantiates a new ForbiddenActionException.
     *
     * @param cause the cause
     * @param id    the id
     */
    public ForbiddenActionException(Throwable cause, Long id) {
        super(cause);
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
}
