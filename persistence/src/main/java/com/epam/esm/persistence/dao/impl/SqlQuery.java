package com.epam.esm.persistence.dao.impl;

public final class SqlQuery {
    private SqlQuery() {}

    static final String SQL_INSERT_CERT =
            "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";
    static final String SQL_FIND_CERT_BY_ID =
            "SELECT * FROM gift_certificate WHERE id = ?";
    static final String SQL_FIND_ALL_CERTS =
            "SELECT * FROM gift_certificate";
    static final String SQL_UPDATE_CERT =
            "UPDATE gift_certificate SET " +
                    "name = ?, " +
                    "description = ?, " +
                    "price = ?, " +
                    "duration = ?, " +
                    "create_date = ?, " +
                    "last_update_date = ? " +
                    "WHERE id = ?";
    static final String SQL_DELETE_CERT_BY_ID =
            "DELETE FROM gift_certificate WHERE id = ?";

    static final String SQL_INSERT_TAG =
            "INSERT INTO tag(name) VALUES (?) ON DUPLICATE KEY UPDATE name = name";
    static final String SQL_FIND_TAG_BY_ID =
            "SELECT * FROM tag WHERE id = ?";
    static final String SQL_FIND_ALL_TAGS =
            "SELECT * FROM tag";
    static final String SQL_DELETE_TAG_BY_ID =
            "DELETE FROM tag WHERE id = ?";
    static final String SQL_FIND_TAGS_BY_NAME =
            "SELECT * FROM tag WHERE name LIKE ?";
    static final String SQL_FIND_TAGS_BY_CERT_ID =
            "SELECT * FROM tag WHERE tag.id IN" +
                    "(SELECT tag_id FROM gift_certificate_has_tag WHERE gift_certificate_id = ?)";
    static final String SQL_ADD_CONNECTION =
            "INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id) VALUES (?,?) " +
                    "ON DUPLICATE KEY UPDATE gift_certificate_id = gift_certificate_id";
}
