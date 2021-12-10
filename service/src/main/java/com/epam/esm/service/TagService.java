package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.exception.EntityNotFoundException;

import java.util.List;

public interface TagService {
    Tag save(Tag tag);

    Tag get(Long id) throws EntityNotFoundException;

    List<Tag> getAll();

    void delete(Long id);

    List<Tag> getByName(String tagName);

    List<Tag> getByCertId(Long certId);
}
