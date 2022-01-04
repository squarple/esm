package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;

/**
 * The interface TagDao.
 */
public interface TagDao {
    /**
     * Create tag.
     *
     * @param entity the entity
     * @return the tag
     * @throws EntityAlreadyExistsDaoException if entity already exists
     */
    Tag create(Tag entity) throws EntityAlreadyExistsDaoException;

    /**
     * Find tag.
     *
     * @param id the id
     * @return the tag
     * @throws EntityNotFoundDaoException if entity not found
     */
    Tag find(Long id) throws EntityNotFoundDaoException;

    /**
     * Find all tags.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<Tag> findAll(Pageable pageable);

    /**
     * Delete.
     *
     * @param id the id
     * @throws EntityNotFoundDaoException if entity not found
     */
    void delete(Long id) throws EntityNotFoundDaoException;

    /**
     * Find tags by name.
     *
     * @param tagName  the tag name
     * @param pageable the pageable
     * @return the page
     */
    Page<Tag> findByName(String tagName, Pageable pageable);

    /**
     * Find most used tag of user with the highest cost of all orders tag.
     *
     * @return the tag
     */
    Tag findMostUsedTagOfUserWithHighestCostOfAllOrders();
}
