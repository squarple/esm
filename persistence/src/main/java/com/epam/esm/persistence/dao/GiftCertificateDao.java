package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {
    boolean create(String name, String description, BigDecimal price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate);

    Optional<GiftCertificate> findById(Long certId);
    List<GiftCertificate> findByTagName(String tagName);
    List<GiftCertificate> findByName(String certName);
    List<GiftCertificate> findByDescription(String certDescription);
    List<GiftCertificate> findAll();

    boolean updateNameById(Long certId, String certName);
    boolean updateDescriptionById(Long certId, String certDescription);
    boolean updatePriceById(Long certId, BigDecimal certPrice);
    boolean updateDurationById(Long certId, Integer certDuration);

    boolean deleteById(Long certId);
}
