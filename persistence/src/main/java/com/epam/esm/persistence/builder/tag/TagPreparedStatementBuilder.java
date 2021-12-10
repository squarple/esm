package com.epam.esm.persistence.builder.tag;

import com.epam.esm.model.entity.Tag;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TagPreparedStatementBuilder implements PreparedStatementCreator {
    private final Tag tag;
    private final String sqlQuery;

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
