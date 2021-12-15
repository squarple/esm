package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.exception.EntityNotFoundException;

import java.util.List;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {
    /**
     * Save gift certificate.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate
     */
    GiftCertificate save(GiftCertificate giftCertificateDto);

    /**
     * Get gift certificate.
     *
     * @param id the id
     * @return the gift certificate
     * @throws EntityNotFoundException the entity not found exception
     */
    GiftCertificate get(Long id) throws EntityNotFoundException;

    /**
     * Get list of certificates.
     *
     * @param name        the name
     * @param description the description
     * @param tagName     the tag name
     * @param sortField   the sort field
     * @param sort        the sort
     * @return the list
     */
    List<GiftCertificate> get(String name, String description, String tagName, String sortField, String sort);

    /**
     * Update gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     * @throws EntityNotFoundException the entity not found exception
     */
    GiftCertificate update(GiftCertificate giftCertificate) throws EntityNotFoundException;

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);

    /**
     * Gets all certificates.
     *
     * @return the all
     */
    List<GiftCertificate> getAll();
}
