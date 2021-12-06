package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends EntityDao<Tag> {
    @Override
    Tag create(Tag entity);

    @Override
    Optional<Tag> find(Long id);

    @Override
    List<Tag> findAll();

    @Override
    Tag update(Tag entity);

    @Override
    void delete(Long id);

    List<Tag> findByName(String tagName);

    List<Tag> findByCertId(Long certId);

    void addConnections(Long certId, List<Long> tagsId);

    void removeConnections(Long certId, List<Long> tagsId);
}
