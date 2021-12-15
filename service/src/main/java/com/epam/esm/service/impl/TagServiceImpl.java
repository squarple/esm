package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.dao.TagDao;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Tag service.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    /**
     * Instantiates a new Tag service.
     *
     * @param tagDao the tag dao
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag save(Tag tag) {
        return tagDao.create(tag);
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
    public List<Tag> getAll() {
        return tagDao.findAll();
    }

    @Override
    public void delete(Long id) {
        tagDao.delete(id);
    }

    @Override
    public List<Tag> getByName(String tagName) {
        return tagDao.findByName(tagName);
    }

    @Override
    public List<Tag> getByCertId(Long certId) {
        return tagDao.findByCertId(certId);
    }
}
