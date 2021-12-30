package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.TagDao;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Tag service.
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    /**
     * Instantiates a new TagService.
     *
     * @param tagDao the tag dao
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public Tag save(Tag tag) throws EntityAlreadyExistsException {
        try {
            return tagDao.create(tag);
        } catch (EntityAlreadyExistsDaoException e) {
            throw new EntityAlreadyExistsException();
        }
    }

    @Override
    public Tag get(Long id) throws EntityNotFoundException {
        try {
            return tagDao.find(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e, id);
        }
    }

    @Override
    public Page<Tag> getAll(Pageable pageable) throws ResourceNotFoundException {
        Page<Tag> page = tagDao.findAll(pageable);
        if (pageable.getPageNumber() > page.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return page;
    }

    @Override
    @Transactional
    public void delete(Long id) throws EntityNotFoundException {
        try {
            tagDao.delete(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e.getEntityId());
        }
    }

    @Override
    public Page<Tag> getByName(String tagName, Pageable pageable) throws ResourceNotFoundException {
        Page<Tag> page = tagDao.findByName(tagName, pageable);
        if (pageable.getPageNumber() > page.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return page;
    }

    @Override
    public Tag getMostUsedTagOfUserWithHighestCostOfAllOrders() {
        return tagDao.findMostUsedTagOfUserWithHighestCostOfAllOrders();
    }
}
