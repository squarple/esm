package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;

import java.util.List;

public interface TagDao extends EntityDao<Tag> {
    @Override
    Tag create(Tag entity);

    @Override
    Tag find(Long id) throws EntityNotFoundDaoException;

    @Override
    List<Tag> findAll();

    @Override
    void delete(Long id);

    List<Tag> findByName(String tagName);

    List<Tag> findByCertId(Long certId);
}
