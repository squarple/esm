package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.mapper.TagMapper;
import com.epam.esm.persistence.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {
    private static final String SQL_INSERT_TAG =
            "INSERT INTO tag(name) VALUES (?)";
    private static final String SQL_FIND_TAG_BY_ID =
            "SELECT * FROM tag WHERE id = ?";
    private static final String SQL_FIND_TAGS_BY_NAME =
            "SELECT * FROM tag WHERE name LIKE ?";
    private static final String SQL_FIND_TAGS_BY_CERT_ID =
            "SELECT * FROM tag WHERE id IN " +
                    "(SELECT id FROM gift_certificate_has_tag WHERE gift_certificate_id = ?)";
    private static final String SQL_FIND_ALL_TAGS =
            "SELECT * FROM tag";
    private static final String SQL_DELETE_TAG_BY_ID =
            "DELETE FROM tag WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(String tagName) {
        return jdbcTemplate.update(SQL_INSERT_TAG, tagName) > 0;
    }

    @Override
    public Optional<Tag> findById(Long tagId) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_ID, new TagMapper(), tagId).stream().findAny();
    }

    @Override
    public List<Tag> findByName(String tagName) {
        tagName = "%" + tagName + "%";
        return jdbcTemplate.query(SQL_FIND_TAGS_BY_NAME, new TagMapper(), tagName);
    }

    @Override
    public List<Tag> findByCertId(Long certId) {
        return jdbcTemplate.query(SQL_FIND_TAGS_BY_CERT_ID, new TagMapper(), certId);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_TAGS, new TagMapper());
    }

    @Override
    public boolean deleteById(Long tagId) {
        return jdbcTemplate.update(SQL_DELETE_TAG_BY_ID, tagId) > 0;
    }
}
