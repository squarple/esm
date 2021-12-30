package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;

/**
 * The interface UserDao.
 */
public interface UserDao {
    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     * @throws EntityAlreadyExistsDaoException if entity already exists
     */
    User create(User user) throws EntityAlreadyExistsDaoException;

    /**
     * Find user.
     *
     * @param id the id
     * @return the user
     * @throws EntityNotFoundDaoException if entity not found
     */
    User find(Long id) throws EntityNotFoundDaoException;

    /**
     * Find all users.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<User> findAll(Pageable pageable);
}
