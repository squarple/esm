package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;

import java.util.List;

/**
 * The interface Tag dao.
 */
public interface TagDao extends EntityDao<Tag> {
    @Override
    Tag create(Tag entity);

    @Override
    Tag find(Long id) throws EntityNotFoundDaoException;

    @Override
    List<Tag> findAll();

    @Override
    void delete(Long id);

    /**
     * Find by name list of tags.
     *
     * @param tagName the tag name
     * @return the list
     */
    List<Tag> findByName(String tagName);

    /**
     * Find by cert id list of tags.
     *
     * @param certId the cert id
     * @return the list
     */
    List<Tag> findByCertId(Long certId);
}
