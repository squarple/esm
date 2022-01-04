package com.epam.esm.persistence.exception;

/**
 * The EntityNotFoundDaoException class.
 */
public class EntityNotFoundDaoException extends Exception {
    private final Long entityId;

    /**
     * Instantiates a new EntityNotFoundDaoException.
     *
     * @param entityId the entity id
     */
    public EntityNotFoundDaoException(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Instantiates a new EntityNotFoundDaoException.
     *
     * @param message  the message
     * @param entityId the entity id
     */
    public EntityNotFoundDaoException(String message, Long entityId) {
        super(message);
        this.entityId = entityId;
    }

    /**
     * Instantiates a new EntityNotFoundDaoException.
     *
     * @param message  the message
     * @param cause    the cause
     * @param entityId the entity id
     */
    public EntityNotFoundDaoException(String message, Throwable cause, Long entityId) {
        super(message, cause);
        this.entityId = entityId;
    }

    /**
     * Instantiates a new EntityNotFoundDaoException.
     *
     * @param cause    the cause
     * @param entityId the entity id
     */
    public EntityNotFoundDaoException(Throwable cause, Long entityId) {
        super(cause);
        this.entityId = entityId;
    }

    /**
     * Gets entity id.
     *
     * @return the entity id
     */
    public Long getEntityId() {
        return entityId;
    }
}
