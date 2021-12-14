package com.epam.esm.persistence.exception;

public class EntityNotFoundDaoException extends Exception {
    private final Long entityId;

    public EntityNotFoundDaoException(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNotFoundDaoException(String message, Long entityId) {
        super(message);
        this.entityId = entityId;
    }

    public EntityNotFoundDaoException(String message, Throwable cause, Long entityId) {
        super(message, cause);
        this.entityId = entityId;
    }

    public EntityNotFoundDaoException(Throwable cause, Long entityId) {
        super(cause);
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }
}
