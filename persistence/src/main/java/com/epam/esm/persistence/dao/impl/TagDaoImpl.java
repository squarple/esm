package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.mapper.TagMapper;
import com.epam.esm.persistence.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends JdbcDaoSupport implements TagDao {
    private static final String SQL_INSERT_TAG =
            "INSERT INTO tag(name) VALUES (?)";
    private static final String SQL_FIND_TAG_BY_ID =
            "SELECT * FROM tag WHERE id = ?";
    private static final String SQL_FIND_ALL_TAGS =
            "SELECT * FROM tag";
    private static final String SQL_UPDATE_TAG =
            "UPDATE tag SET name = ? WHERE id = ?";
    private static final String SQL_DELETE_TAG_BY_ID =
            "DELETE FROM tag WHERE id = ?";
    private static final String SQL_FIND_TAGS_BY_NAME =
            "SELECT * FROM tag WHERE name LIKE ?";
    private static final String SQL_FIND_TAGS_BY_CERT_ID =
            "SELECT * FROM tag WHERE id IN " +
                    "(SELECT id FROM gift_certificate_has_tag WHERE gift_certificate_id = ?)";

    private static final TagMapper ROW_MAPPER = new TagMapper();

    @Autowired
    public TagDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT_TAG);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
        return tag;
    }

    @Override
    public Optional<Tag> find(Long tagId) {
        return getJdbcTemplate().query(SQL_FIND_TAG_BY_ID, ROW_MAPPER, tagId).stream().findAny();
    }

    @Override
    public List<Tag> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL_TAGS, ROW_MAPPER);
    }

    @Override
    public Tag update(Tag entity) {
        getJdbcTemplate().update(SQL_UPDATE_TAG, entity.getName(), entity.getId());
        return entity;
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update(SQL_DELETE_TAG_BY_ID, id);
    }

    @Override
    public List<Tag> findByName(String tagName) {
        tagName = "%" + tagName + "%";
        return getJdbcTemplate().query(SQL_FIND_TAGS_BY_NAME, ROW_MAPPER, tagName);
    }

    @Override
    public List<Tag> findByCertId(Long certId) {
        return getJdbcTemplate().query(SQL_FIND_TAGS_BY_CERT_ID, ROW_MAPPER, certId);
    }
}
