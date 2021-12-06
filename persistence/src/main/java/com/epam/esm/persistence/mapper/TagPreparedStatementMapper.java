package com.epam.esm.persistence.mapper;

import com.epam.esm.model.entity.Tag;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagPreparedStatementMapper implements PreparedStatementCreator {
    private final Tag tag;
    private final String sqlQuery;

    public TagPreparedStatementMapper(Tag tag, String sqlQuery) {
        this.tag = tag;
        this.sqlQuery = sqlQuery;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sqlQuery);
        ps.setString(1, tag.getName());
        if (tag.getId() != null) {
            ps.setLong(2, tag.getId());
        }
        return ps;
    }
}
