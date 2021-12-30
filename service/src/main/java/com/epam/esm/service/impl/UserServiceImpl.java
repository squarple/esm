package com.epam.esm.service.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.UserDao;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The User service.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    /**
     * Instantiates a new UserService.
     *
     * @param userDao the user dao
     */
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User save(User user) throws EntityAlreadyExistsException {
        try {
            return userDao.create(user);
        } catch (EntityAlreadyExistsDaoException e) {
            throw new EntityAlreadyExistsException();
        }
    }

    @Override
    public User get(Long id) throws EntityNotFoundException {
        try {
            return userDao.find(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e, id);
        }
    }

    @Override
    public Page<User> getAll(Pageable pageable) throws ResourceNotFoundException {
        Page<User> page = userDao.findAll(pageable);
        if (pageable.getPageNumber() > page.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return page;
    }
}
