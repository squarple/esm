package com.epam.esm.service;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;

/**
 * The interface UserService.
 */
public interface UserService {
    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     * @throws EntityAlreadyExistsException if entity already exists
     */
    User save(User user) throws EntityAlreadyExistsException;

    /**
     * Get user.
     *
     * @param id the id
     * @return the user
     * @throws EntityNotFoundException if entity not found
     */
    User get(Long id) throws EntityNotFoundException;

    /**
     * Gets all users.
     *
     * @param pageable the pageable
     * @return the page
     * @throws ResourceNotFoundException if resource not found
     */
    Page<User> getAll(Pageable pageable) throws ResourceNotFoundException;
}
