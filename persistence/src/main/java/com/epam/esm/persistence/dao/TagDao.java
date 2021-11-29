package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    boolean create(String tagName);

    Optional<Tag> findById(Long tagId);
    List<Tag> findByName(String tagName);
    List<Tag> findByCertId(Long certId);
    List<Tag> findAll();

    boolean deleteById(Long tagId);
}
