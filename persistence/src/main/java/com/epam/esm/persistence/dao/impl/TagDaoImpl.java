package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.mapper.TagMapper;
import com.epam.esm.persistence.dao.TagDao;
import com.epam.esm.persistence.builder.tag.TagPreparedStatementBuilder;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
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
public class TagDaoImpl extends JdbcDaoSupport implements TagDao {
    private static final TagMapper TAG_MAPPER = new TagMapper();

    @Autowired
    public TagDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new TagPreparedStatementBuilder(tag, SQL_INSERT_TAG), keyHolder);
        if (keyHolder.getKey() == null) {
            return getJdbcTemplate().query(SQL_FIND_TAGS_BY_NAME, TAG_MAPPER, tag.getName()).stream().findFirst().get();
        }
        tag.setId(keyHolder.getKey().longValue());
        return tag;
    }

    @Override
    public Tag find(Long tagId) throws EntityNotFoundDaoException {
        Optional<Tag> tagOptional = getJdbcTemplate().query(SQL_FIND_TAG_BY_ID, TAG_MAPPER, tagId).stream().findAny();
        if (!tagOptional.isPresent()) {
            throw new EntityNotFoundDaoException(tagId);
        }
        return tagOptional.get();
    }

    @Override
    public List<Tag> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL_TAGS, TAG_MAPPER);
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update(SQL_DELETE_TAG_BY_ID, id);
    }

    @Override
    public List<Tag> findByName(String tagName) {
        tagName = String.join("", "%", tagName, "%");
        return getJdbcTemplate().query(SQL_FIND_TAGS_BY_NAME_LIKE, TAG_MAPPER, tagName);
    }

    @Override
    public List<Tag> findByCertId(Long certId) {
        return getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, TAG_MAPPER, certId);
    }
}
