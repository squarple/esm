package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.mapper.TagMapper;
import com.epam.esm.persistence.dao.TagDao;
import com.epam.esm.persistence.builder.tag.TagPreparedStatementBuilder;
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
public class TagDaoImpl extends JdbcDaoSupport implements TagDao {
    private static final TagMapper ROW_MAPPER = new TagMapper();

    @Autowired
    public TagDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new TagPreparedStatementBuilder(tag, SQL_INSERT_TAG), keyHolder);
        tag.setId(keyHolder.getKey().longValue());
        return tag;
    }

    @Override
    public Tag find(Long tagId) throws EntityNotFoundException {
        Optional<Tag> tagOptional = getJdbcTemplate().query(SQL_FIND_TAG_BY_ID, ROW_MAPPER, tagId).stream().findAny();
        if (!tagOptional.isPresent()) {
            throw new EntityNotFoundException();
        }
        return tagOptional.get();
    }

    @Override
    public List<Tag> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL_TAGS, ROW_MAPPER);
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update(SQL_DELETE_TAG_BY_ID, id);
    }

    @Override
    public List<Tag> findByName(String tagName) {
        tagName = String.join("", "%", tagName, "%");
        return getJdbcTemplate().query(SQL_FIND_TAGS_BY_NAME, ROW_MAPPER, tagName);
    }

    @Override
    public List<Tag> findByCertId(Long certId) {
        return getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, ROW_MAPPER, certId);
    }
}
