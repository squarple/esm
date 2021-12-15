package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.exception.EntityNotFoundException;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * Save tag.
     *
     * @param tag the tag
     * @return the tag
     */
    Tag save(Tag tag);

    /**
     * Get tag.
     *
     * @param id the id
     * @return the tag
     * @throws EntityNotFoundException the entity not found exception
     */
    Tag get(Long id) throws EntityNotFoundException;

    /**
     * Gets all.
     *
     * @return the all
     */
    List<Tag> getAll();

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);

    /**
     * Gets by name.
     *
     * @param tagName the tag name
     * @return the by name
     */
    List<Tag> getByName(String tagName);

    /**
     * Gets by cert id.
     *
     * @param certId the cert id
     * @return the by cert id
     */
    List<Tag> getByCertId(Long certId);
}
