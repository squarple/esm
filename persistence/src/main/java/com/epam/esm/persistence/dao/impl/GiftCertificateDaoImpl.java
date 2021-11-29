package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.mapper.GiftCertificateMapper;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SQL_INSERT_CERT =
            "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";
    private static final String SQL_FIND_CERT_BY_ID =
            "SELECT * FROM gift_certificate WHERE id = ?";
    private static final String SQL_FIND_CERTS_BY_TAG_NAME =
            "SELECT * FROM gift_certificate WHERE gift_certificate.id IN " +
                    "(SELECT gift_certificate_id from gift_certificate_has_tag WHERE tag_id = " +
                    "(SELECT tag.id FROM tag WHERE tag.name = ?))";
    private static final String SQL_FIND_CERTS_BY_NAME =
            "SELECT * from gift_certificate WHERE name LIKE ?";
    private static final String SQL_FIND_CERTS_BY_DESCRIPTION =
            "SELECT * FROM gift_certificate WHERE description LIKE ?";
    private static final String SQL_FIND_ALL_CERTS =
            "SELECT * FROM gift_certificate";
    private static final String SQL_UPDATE_NAME_OF_CERT_BY_ID =
            "UPDATE gift_certificate SET name = ? WHERE id = ?";
    private static final String SQL_UPDATE_DESCRIPTION_OF_CERT_BY_ID =
            "UPDATE gift_certificate SET description = ? WHERE id = ?";
    private static final String SQL_UPDATE_PRICE_OF_CERT_BY_ID =
            "UPDATE gift_certificate SET price = ? WHERE id = ?";
    private static final String SQL_UPDATE_DURATION_OF_CERT_BY_ID =
            "UPDATE gift_certificate SET duration = ? WHERE id = ?";
    private static final String SQL_DELETE_CERT_BY_ID =
            "DELETE FROM gift_certificate WHERE id = ?";

    private static final GiftCertificateMapper ROW_MAPPER = new GiftCertificateMapper();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(String name, String description, BigDecimal price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        return jdbcTemplate.update(SQL_INSERT_CERT, name, description, price, duration, Timestamp.valueOf(createDate), Timestamp.valueOf(lastUpdateDate)) > 0;
    }

    @Override
    public Optional<GiftCertificate> findById(Long certId) {
        return jdbcTemplate.query(SQL_FIND_CERT_BY_ID, ROW_MAPPER, certId).stream().findAny();
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        return jdbcTemplate.query(SQL_FIND_CERTS_BY_TAG_NAME, ROW_MAPPER, tagName);
    }

    @Override
    public List<GiftCertificate> findByName(String certName) {
        certName = "%" + certName + "%";
        return jdbcTemplate.query(SQL_FIND_CERTS_BY_NAME, ROW_MAPPER, certName);
    }

    @Override
    public List<GiftCertificate> findByDescription(String certDescription) {
        certDescription = "%" + certDescription + "%";
        return jdbcTemplate.query(SQL_FIND_CERTS_BY_DESCRIPTION, ROW_MAPPER, certDescription);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_CERTS, ROW_MAPPER);
    }

    @Override
    public boolean updateNameById(Long certId, String certName) {
        return jdbcTemplate.update(SQL_UPDATE_NAME_OF_CERT_BY_ID, certName, certId) > 0;
    }

    @Override
    public boolean updateDescriptionById(Long certId, String certDescription) {
        return jdbcTemplate.update(SQL_UPDATE_DESCRIPTION_OF_CERT_BY_ID, certDescription, certId) > 0;
    }

    @Override
    public boolean updatePriceById(Long certId, BigDecimal certPrice) {
        return jdbcTemplate.update(SQL_UPDATE_PRICE_OF_CERT_BY_ID, certPrice, certId) > 0;
    }

    @Override
    public boolean updateDurationById(Long certId, Integer certDuration) {
        return jdbcTemplate.update(SQL_UPDATE_DURATION_OF_CERT_BY_ID, certDuration, certId) > 0;
    }

    @Override
    public boolean deleteById(Long certId) {
        return jdbcTemplate.update(SQL_DELETE_CERT_BY_ID, certId) > 0;
    }
}