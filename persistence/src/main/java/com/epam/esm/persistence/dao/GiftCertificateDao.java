package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.criteria.cert.Criteria;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.persistence.exception.ForbiddenActionException;

/**
 * The interface GiftCertificateDao.
 */
public interface GiftCertificateDao {
    /**
     * Create gift certificate.
     *
     * @param entity the entity
     * @return the gift certificate
     */
    GiftCertificate create(GiftCertificate entity);

    /**
     * Find gift certificate.
     *
     * @param id the id
     * @return the gift certificate
     * @throws EntityNotFoundDaoException if entity not found
     */
    GiftCertificate find(Long id) throws EntityNotFoundDaoException;

    /**
     * Find list of tags with specified criteria.
     *
     * @param criteria the criteria
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificate> find(Criteria criteria, Pageable pageable);

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<GiftCertificate> findAll(Pageable pageable);

    /**
     * Update gift certificate.
     *
     * @param entity the entity
     * @return the gift certificate
     * @throws EntityNotFoundDaoException if entity not found
     */
    GiftCertificate update(GiftCertificate entity) throws EntityNotFoundDaoException;

    /**
     * Delete gift certificate.
     *
     * @param id the id
     * @throws ForbiddenActionException   if action is forbidden
     * @throws EntityNotFoundDaoException if entity not found
     */
    void delete(Long id) throws ForbiddenActionException, EntityNotFoundDaoException;

    /**
     * Is possible to delete gift certificate.
     *
     * @param id the id
     * @return true if possible, false if not
     */
    boolean isPossibleToDelete(Long id);
}
