package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends EntityDao<GiftCertificate> {
    @Override
    GiftCertificate create(GiftCertificate entity);

    @Override
    Optional<GiftCertificate> find(Long id);

    @Override
    GiftCertificate update(GiftCertificate entity);

    @Override
    void delete(Long id);

    @Override
    List<GiftCertificate> findAll();

    List<GiftCertificate> findByTagName(String tagName);

    List<GiftCertificate> findByName(String certName);

    List<GiftCertificate> findByDescription(String certDescription);
}
