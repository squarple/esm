package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.builder.cert.criteria.Criteria;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;

import java.util.List;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends EntityDao<GiftCertificate> {
    @Override
    GiftCertificate create(GiftCertificate entity);

    @Override
    GiftCertificate find(Long id) throws EntityNotFoundDaoException;

    /**
     * Find list of tags with specified criteria.
     *
     * @param criteria the criteria
     * @return the list
     */
    List<GiftCertificate> find(Criteria criteria);

    @Override
    List<GiftCertificate> findAll();

    /**
     * Update gift certificate.
     *
     * @param entity the entity
     * @return the gift certificate
     * @throws EntityNotFoundDaoException the entity not found dao exception
     */
    GiftCertificate update(GiftCertificate entity) throws EntityNotFoundDaoException;

    @Override
    void delete(Long id);
}
