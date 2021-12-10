package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.exception.EntityNotFoundException;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificate save(GiftCertificate giftCertificateDto);

    GiftCertificate get(Long id) throws EntityNotFoundException;

    List<GiftCertificate> get(String name, String description, String tagName, String sortField, String sort);

    GiftCertificate update(GiftCertificate giftCertificate) throws EntityNotFoundException;

    void delete(Long id);

    List<GiftCertificate> getAll();
}
