package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface GiftCertificateService.
 */
public interface GiftCertificateService {
    /**
     * Save gift certificate.
     *
     * @param giftCertificateDto the gift certificate
     * @return the gift certificate
     */
    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);

    /**
     * Find gift certificate dto.
     *
     * @param id the id
     * @return the gift certificate
     * @throws EntityNotFoundException if certificate not found
     */
    GiftCertificateDto find(Long id) throws EntityNotFoundException;

    /**
     * Find page.
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @param sortField   the sort field
     * @param sort        the sort
     * @param pageable    the pageable
     * @return the page
     */
    Page<GiftCertificateDto> find(String name, String description, List<String> tagNames, String sortField, String sort, Pageable pageable);

    /**
     * Update gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the updated gift certificate
     * @throws EntityNotFoundException if certificate not found
     */
    GiftCertificateDto update(GiftCertificateDto giftCertificate) throws EntityNotFoundException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ForbiddenActionException if impossible to delete certificate
     */
    void delete(Long id) throws ForbiddenActionException, EntityNotFoundException;

    /**
     * Gets all certificates.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificateDto> getAll(Pageable pageable);

    /**
     * Is possible to delete certificate.
     *
     * @param id the id
     * @return true if possible, false if not
     */
    boolean isPossibleToDelete(Long id);
}
