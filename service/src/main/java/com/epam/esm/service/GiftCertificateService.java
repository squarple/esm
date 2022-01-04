package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;

import java.util.List;

/**
 * The interface Gift certificateService.
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
     * @throws EntityNotFoundException if entity not found
     */
    GiftCertificate get(Long id) throws EntityNotFoundException;

    /**
     * Get certificates.
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag name
     * @param sortField   the sort field
     * @param sort        the sort
     * @param pageable    the pageable
     * @return the list
     * @throws ResourceNotFoundException if resource not found
     */
    Page<GiftCertificate> get(String name, String description, List<String> tagNames, String sortField, String sort, Pageable pageable) throws ResourceNotFoundException;

    /**
     * Update gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     * @throws EntityNotFoundException if entity not found
     */
    GiftCertificate update(GiftCertificate giftCertificate) throws EntityNotFoundException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ForbiddenActionException if forbidden action
     * @throws EntityNotFoundException  if entity not found
     */
    void delete(Long id) throws ForbiddenActionException, EntityNotFoundException;

    /**
     * Gets all certificates.
     *
     * @param pageable the pageable
     * @return the all gift certificates
     * @throws ResourceNotFoundException if resource not found
     */
    Page<GiftCertificate> getAll(Pageable pageable) throws ResourceNotFoundException;

    /**
     * Is possible to delete gift certificate.
     *
     * @param id the id
     * @return true if possible, false if not
     */
    boolean isPossibleToDelete(Long id);
}
