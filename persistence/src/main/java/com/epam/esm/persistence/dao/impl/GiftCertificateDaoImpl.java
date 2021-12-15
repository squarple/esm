package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.mapper.GiftCertificateMapper;
import com.epam.esm.model.mapper.TagMapper;
import com.epam.esm.persistence.builder.cert.GiftCertificatePreparedStatementBuilder;
import com.epam.esm.persistence.builder.cert.GiftCertificateQueryBuilder;
import com.epam.esm.persistence.builder.cert.criteria.Criteria;
import com.epam.esm.persistence.builder.tag.TagPreparedStatementBuilder;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.persistence.dao.impl.SqlQuery.*;

/**
 * The type Gift certificate dao.
 */
@Repository
public class GiftCertificateDaoImpl extends JdbcDaoSupport implements GiftCertificateDao {
    private static final GiftCertificateMapper CERT_MAPPER = new GiftCertificateMapper();
    private static final TagMapper TAG_MAPPER = new TagMapper();

    /**
     * Instantiates a new Gift certificate dao.
     *
     * @param dataSource the data source
     */
    @Autowired
    public GiftCertificateDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new GiftCertificatePreparedStatementBuilder(entity, SQL_INSERT_CERT), keyHolder);
        entity.setId(keyHolder.getKey().longValue());

        if (entity.getTags() == null) {
            entity.setTags(new ArrayList<>());
            return entity;
        }
        List<Tag> tags = entity.getTags();
        for (int i = 0; i < tags.size(); i++) {
            KeyHolder tagKeyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(new TagPreparedStatementBuilder(tags.get(i), SQL_INSERT_TAG), tagKeyHolder);
            if (tagKeyHolder.getKey() == null) {
                tags.set(i, getJdbcTemplate().query(SQL_FIND_TAGS_BY_NAME, TAG_MAPPER, tags.get(i).getName()).stream().findFirst().get());
            } else {
                tags.get(i).setId(tagKeyHolder.getKey().longValue());
            }
        }
        entity.getTags().forEach(e -> getJdbcTemplate().update(SQL_ADD_CONNECTION, entity.getId(), e.getId()));
        return entity;
    }

    @Override
    public GiftCertificate find(Long id) throws EntityNotFoundDaoException {
        Optional<GiftCertificate> certOptional = getJdbcTemplate().query(SQL_FIND_CERT_BY_ID, CERT_MAPPER, id).stream().findAny();
        if (!certOptional.isPresent()) {
            throw new EntityNotFoundDaoException(id);
        }
        certOptional.get().setTags(getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, TAG_MAPPER, certOptional.get().getId()));
        return certOptional.get();
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
    public List<GiftCertificate> find(Criteria criteria) {
        GiftCertificateQueryBuilder builder = GiftCertificateQueryBuilder.builder();
        String query = builder.configureSelectQuery(criteria);
        Object[] params = builder.getParams().toArray();
        List<GiftCertificate> certificates =  getJdbcTemplate().query(query, CERT_MAPPER, params);
        certificates.forEach(e -> e.setTags(getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, TAG_MAPPER, e.getId())));
        return certificates;
    }

    @Override
    public GiftCertificate update(GiftCertificate cert) throws EntityNotFoundDaoException {
        if (cert.getName() == null &&
                cert.getDescription() == null &&
                cert.getPrice() == null &&
                cert.getDuration() == null &&
                cert.getCreateDate() == null &&
                cert.getLastUpdateDate() == null) {
            return find(cert.getId());
        }
        GiftCertificateQueryBuilder builder = GiftCertificateQueryBuilder.builder();
        String query = builder.configureUpdateCriteria(cert);
        Object[] params = builder.getParams().toArray();
        getJdbcTemplate().update(query, params);
        if (cert.getTags() != null) {
            List<Tag> actualTags = getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, TAG_MAPPER, cert.getId());
            actualTags.forEach(e -> {
                if (!cert.getTags().contains(e)) {
                    getJdbcTemplate().update(SQL_REMOVE_CONNECTION, cert.getId(), e.getId());
                }
            });

            List<Tag> tags = cert.getTags();
            for (int i = 0; i < tags.size(); i++) {
                KeyHolder tagKeyHolder = new GeneratedKeyHolder();
                getJdbcTemplate().update(new TagPreparedStatementBuilder(tags.get(i), SQL_INSERT_TAG), tagKeyHolder);
                if (tagKeyHolder.getKey() == null) {
                    tags.set(i, getJdbcTemplate().query(SQL_FIND_TAGS_BY_NAME, TAG_MAPPER, tags.get(i).getName()).stream().findFirst().get());
                } else {
                    tags.get(i).setId(tagKeyHolder.getKey().longValue());
                }
            }

            cert.getTags().forEach(e -> getJdbcTemplate().update(SQL_ADD_CONNECTION, cert.getId(), e.getId()));
        }
        return find(cert.getId());
    }
}