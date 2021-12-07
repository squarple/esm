package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.builder.cert.criteria.UpdateCriteria;
import com.epam.esm.persistence.exception.EntityNotFoundException;
import com.epam.esm.persistence.builder.cert.criteria.SelectCriteria;

import java.util.List;

public interface GiftCertificateDao extends EntityDao<GiftCertificate> {
    @Override
    GiftCertificate create(GiftCertificate entity);

    @Override
    GiftCertificate find(Long id) throws EntityNotFoundException;

    List<GiftCertificate> find(SelectCriteria criteria);

    @Override
    List<GiftCertificate> findAll();

    GiftCertificate update(GiftCertificate entity);

    @Override
    void delete(Long id);

    GiftCertificate update(GiftCertificate giftCertificate, UpdateCriteria updateCriteria) throws EntityNotFoundException;
}
