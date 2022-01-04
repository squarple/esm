package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;

/**
 * The interface TagService.
 */
public interface TagService {
    /**
     * Save tag.
     *
     * @param tag the tag
     * @return the tag
     * @throws EntityAlreadyExistsException if entity already exists
     */
    Tag save(Tag tag) throws EntityAlreadyExistsException;

    /**
     * Get tag.
     *
     * @param id the id
     * @return the tag
     * @throws EntityNotFoundException if entity not found
     */
    Tag get(Long id) throws EntityNotFoundException;

    /**
     * Gets all tags.
     *
     * @param pageable the pageable
     * @return the all
     * @throws ResourceNotFoundException if resource not found
     */
    Page<Tag> getAll(Pageable pageable) throws ResourceNotFoundException;

    /**
     * Delete tag.
     *
     * @param id the id
     * @throws EntityNotFoundException if entity not found
     */
    void delete(Long id) throws EntityNotFoundException;

    /**
     * Gets tags by name.
     *
     * @param tagName  the tag name
     * @param pageable the pageable
     * @return the by name
     * @throws ResourceNotFoundException if resource not found exception
     */
    Page<Tag> getByName(String tagName, Pageable pageable) throws ResourceNotFoundException;

    /**
     * Gets most used tag of user with the highest cost of all orders.
     *
     * @return the most used tag of user with the highest cost of all orders
     */
    Tag getMostUsedTagOfUserWithHighestCostOfAllOrders();
}
