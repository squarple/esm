package com.epam.esm.service.exception;

/**
 * The EntityNotFoundException class.
 */
public class EntityNotFoundException extends Exception {
    private final Long entityId;

    /**
     * Instantiates a new EntityNotFoundException.
     *
     * @param entityId the entity id
     */
    public EntityNotFoundException(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Instantiates a new EntityNotFoundException.
     *
     * @param message  the message
     * @param entityId the entity id
     */
    public EntityNotFoundException(String message, Long entityId) {
        super(message);
        this.entityId = entityId;
    }

    /**
     * Instantiates a new EntityNotFoundException.
     *
     * @param message  the message
     * @param cause    the cause
     * @param entityId the entity id
     */
    public EntityNotFoundException(String message, Throwable cause, Long entityId) {
        super(message, cause);
        this.entityId = entityId;
    }

    /**
     * Instantiates a new EntityNotFoundException.
     *
     * @param cause    the cause
     * @param entityId the entity id
     */
    public EntityNotFoundException(Throwable cause, Long entityId) {
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
