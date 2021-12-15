package com.epam.esm.persistence.builder.tag;

import com.epam.esm.model.entity.Tag;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The type Tag prepared statement builder.
 */
public class TagPreparedStatementBuilder implements PreparedStatementCreator {
    private final Tag tag;
    private final String sqlQuery;

    /**
     * Instantiates a new Tag prepared statement builder.
     *
     * @param tag      the tag
     * @param sqlQuery the sql query
     */
    public TagPreparedStatementBuilder(Tag tag, String sqlQuery) {
        this.tag = tag;
        this.sqlQuery = sqlQuery;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, tag.getName());
        if (tag.getId() != null) {
            ps.setLong(2, tag.getId());
        }
        return ps;
    }
}
