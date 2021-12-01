package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.mapper.GiftCertificateMapper;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends JdbcDaoSupport implements GiftCertificateDao {
    private static final String SQL_INSERT_CERT =
            "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";
    private static final String SQL_FIND_CERT_BY_ID =
            "SELECT * FROM gift_certificate WHERE id = ?";
    private static final String SQL_FIND_ALL_CERTS =
            "SELECT * FROM gift_certificate";
    private static final String SQL_UPDATE_CERT =
            "UPDATE gift_certificate SET " +
                    "name = ?, " +
                    "description = ?, " +
                    "price = ?, " +
                    "duration = ?, " +
                    "create_date = ?, " +
                    "last_update_date = ? " +
                    "WHERE id = ?";
    private static final String SQL_DELETE_CERT_BY_ID =
            "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SQL_FIND_CERTS_BY_TAG_NAME =
            "SELECT * FROM gift_certificate WHERE gift_certificate.id IN " +
                    "(SELECT gift_certificate_id from gift_certificate_has_tag WHERE tag_id = " +
                    "(SELECT tag.id FROM tag WHERE tag.name = ?))";
    private static final String SQL_FIND_CERTS_BY_NAME =
            "SELECT * from gift_certificate WHERE name LIKE ?";
    private static final String SQL_FIND_CERTS_BY_DESCRIPTION =
            "SELECT * FROM gift_certificate WHERE description LIKE ?";

    private static final GiftCertificateMapper ROW_MAPPER = new GiftCertificateMapper();

    @Autowired
    public GiftCertificateDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CERT);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setInt(4, entity.getDuration());
            ps.setTimestamp(5, Timestamp.valueOf(entity.getCreateDate()));
            ps.setTimestamp(6, Timestamp.valueOf(entity.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    @Override
    public Optional<GiftCertificate> find(Long id) {
        return getJdbcTemplate().query(SQL_FIND_CERT_BY_ID, ROW_MAPPER, id).stream().findAny();
    }

    @Override
    public void update(GiftCertificate entity) {
        getJdbcTemplate().update(SQL_UPDATE_CERT,
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getDuration(),
                Timestamp.valueOf(entity.getCreateDate()),
                Timestamp.valueOf(entity.getLastUpdateDate()),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update(SQL_DELETE_CERT_BY_ID, id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL_CERTS, ROW_MAPPER);
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        return getJdbcTemplate().query(SQL_FIND_CERTS_BY_TAG_NAME, ROW_MAPPER, tagName);
    }

    @Override
    public List<GiftCertificate> findByName(String certName) {
        certName = "%" + certName + "%";
        return getJdbcTemplate().query(SQL_FIND_CERTS_BY_NAME, ROW_MAPPER, certName);
    }

    @Override
    public List<GiftCertificate> findByDescription(String certDescription) {
        certDescription = "%" + certDescription + "%";
        return getJdbcTemplate().query(SQL_FIND_CERTS_BY_DESCRIPTION, ROW_MAPPER, certDescription);
    }
}