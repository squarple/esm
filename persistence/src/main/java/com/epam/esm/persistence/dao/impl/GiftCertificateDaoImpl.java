package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.mapper.GiftCertificateMapper;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.mapper.GiftCertificatePreparedStatementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends JdbcDaoSupport implements GiftCertificateDao {
    public static final String SQL_INSERT_CERT =
            "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";
    public static final String SQL_FIND_CERT_BY_ID =
            "SELECT * FROM gift_certificate WHERE id = ?";
    public static final String SQL_FIND_ALL_CERTS =
            "SELECT * FROM gift_certificate";
    public static final String SQL_UPDATE_CERT =
            "UPDATE gift_certificate SET " +
                    "name = ?, " +
                    "description = ?, " +
                    "price = ?, " +
                    "duration = ?, " +
                    "create_date = ?, " +
                    "last_update_date = ? " +
                    "WHERE id = ?";
    public static final String SQL_DELETE_CERT_BY_ID =
            "DELETE FROM gift_certificate WHERE id = ?";

    private static final GiftCertificateMapper ROW_MAPPER = new GiftCertificateMapper();

    @Autowired
    public GiftCertificateDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new GiftCertificatePreparedStatementMapper(entity, SQL_INSERT_CERT), keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    @Override
    public Optional<GiftCertificate> find(Long id) {
        return getJdbcTemplate().query(SQL_FIND_CERT_BY_ID, ROW_MAPPER, id).stream().findAny();
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        getJdbcTemplate().update(new GiftCertificatePreparedStatementMapper(entity, SQL_UPDATE_CERT));
        return entity;
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update(SQL_DELETE_CERT_BY_ID, id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL_CERTS, ROW_MAPPER);
    }
}