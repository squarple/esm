package com.epam.esm.persistence.dao;

import com.epam.esm.persistence.exception.EntityNotFoundDaoException;

import java.util.List;

/**
 * The interface Entity dao.
 *
 * @param <E> the type parameter
 */
public interface EntityDao<E> {
    /**
     * Create e.
     *
     * @param entity the entity
     * @return the e
     */
    E create(E entity);

    /**
     * Find e.
     *
     * @param id the id
     * @return the e
     * @throws EntityNotFoundDaoException the entity not found dao exception
     */
    E find(Long id) throws EntityNotFoundDaoException;

    /**
     * Find all list.
     *
     * @return the list
     */
    List<E> findAll();

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);
}
