package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.persistence.builder.cert.criteria.Criteria;

import java.util.List;

public interface GiftCertificateDao extends EntityDao<GiftCertificate> {
    @Override
    GiftCertificate create(GiftCertificate entity);

    @Override
    GiftCertificate find(Long id) throws EntityNotFoundDaoException;

    List<GiftCertificate> find(Criteria criteria);

    @Override
    List<GiftCertificate> findAll();

    GiftCertificate update(GiftCertificate entity) throws EntityNotFoundDaoException;

    @Override
    void delete(Long id);
}
