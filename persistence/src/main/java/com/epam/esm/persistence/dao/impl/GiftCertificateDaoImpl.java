package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.mapper.GiftCertificateMapper;
import com.epam.esm.model.mapper.TagMapper;
import com.epam.esm.persistence.builder.cert.GiftCertificatePreparedStatementBuilder;
import com.epam.esm.persistence.builder.cert.GiftCertificateQueryBuilder;
import com.epam.esm.persistence.builder.cert.criteria.SelectCriteria;
import com.epam.esm.persistence.builder.cert.criteria.UpdateCriteria;
import com.epam.esm.persistence.builder.tag.TagPreparedStatementBuilder;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.persistence.dao.impl.SqlQuery.*;

@Repository
public class GiftCertificateDaoImpl extends JdbcDaoSupport implements GiftCertificateDao {
    private static final GiftCertificateMapper CERT_MAPPER = new GiftCertificateMapper();
    private static final TagMapper TAG_MAPPER = new TagMapper();

    @Autowired
    public GiftCertificateDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new GiftCertificatePreparedStatementBuilder(entity, SQL_INSERT_CERT), keyHolder);
        entity.setId(keyHolder.getKey().longValue());

        entity.getTags().forEach(e -> {
            if (e.getId() == null) {
                KeyHolder tagKeyHolder = new GeneratedKeyHolder();
                getJdbcTemplate().update(new TagPreparedStatementBuilder(e, SQL_INSERT_CERT), tagKeyHolder);
                e.setId(keyHolder.getKey().longValue());
            }
        });
        entity.getTags().forEach(e -> getJdbcTemplate().update(SQL_ADD_CONNECTION, entity.getId(), e.getId()));
        return entity;
    }

    @Override
    public GiftCertificate find(Long id) throws EntityNotFoundException {
        Optional<GiftCertificate> certOptional = getJdbcTemplate().query(SQL_FIND_CERT_BY_ID, CERT_MAPPER, id).stream().findAny();
        if (!certOptional.isPresent()) {
            throw new EntityNotFoundException();
        }
        certOptional.get().setTags(getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, TAG_MAPPER, certOptional.get().getId()));
        return certOptional.get();
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        getJdbcTemplate().update(new GiftCertificatePreparedStatementBuilder(entity, SQL_UPDATE_CERT));
        entity.getTags().forEach(e -> {
            if (e.getId() == null) {
                KeyHolder tagKeyHolder = new GeneratedKeyHolder();
                getJdbcTemplate().update(new TagPreparedStatementBuilder(e, SQL_INSERT_TAG), tagKeyHolder);
                e.setId(tagKeyHolder.getKey().longValue());
            }
        });
        entity.getTags().forEach(e -> getJdbcTemplate().update(SQL_ADD_CONNECTION, entity.getId(), e.getId()));
        return entity;
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update(SQL_DELETE_CERT_BY_ID, id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> certificates =  getJdbcTemplate().query(SQL_FIND_ALL_CERTS, CERT_MAPPER);
        certificates.forEach(e -> e.setTags(getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, TAG_MAPPER, e.getId())));
        return certificates;
    }

    @Override
    public List<GiftCertificate> find(SelectCriteria criteria) {
        GiftCertificateQueryBuilder builder = GiftCertificateQueryBuilder.builder();
        String query = builder.configureSelectQuery(criteria);
        Object[] params = builder.getParams().toArray();
        List<GiftCertificate> certificates =  getJdbcTemplate().query(query, CERT_MAPPER, params);
        certificates.forEach(e -> e.setTags(getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, TAG_MAPPER, e.getId())));
        return certificates;
    }

    @Override
    public GiftCertificate update(GiftCertificate demoGiftCertificate, UpdateCriteria criteria) throws EntityNotFoundException {
        GiftCertificateQueryBuilder builder = GiftCertificateQueryBuilder.builder();
        String query = builder.configureUpdateCriteria(demoGiftCertificate, criteria);
        Object[] params = builder.getParams().toArray();
        getJdbcTemplate().update(query, params);

        demoGiftCertificate.getTags().forEach(e -> {
            if (e.getId() == null) {
                KeyHolder tagKeyHolder = new GeneratedKeyHolder();
                getJdbcTemplate().update(new TagPreparedStatementBuilder(e, SQL_INSERT_TAG), tagKeyHolder);
                e.setId(tagKeyHolder.getKey().longValue());
            }
        });
        demoGiftCertificate.getTags().forEach(e -> getJdbcTemplate().update(SQL_ADD_CONNECTION, demoGiftCertificate.getId(), e.getId()));

        return find(demoGiftCertificate.getId());
    }
}