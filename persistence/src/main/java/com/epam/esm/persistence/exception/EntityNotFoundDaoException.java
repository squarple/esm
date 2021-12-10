package com.epam.esm.persistence.exception;

public class EntityNotFoundDaoException extends Exception {
    public EntityNotFoundDaoException() {
        super();
    }

    public EntityNotFoundDaoException(String message) {
        super(message);
    }

    public EntityNotFoundDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundDaoException(Throwable cause) {
        super(cause);
    }
}
