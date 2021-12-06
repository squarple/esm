package com.epam.esm.persistence.mapper;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class GiftCertificatePreparedStatementMapper implements PreparedStatementCreator {
    private final GiftCertificate giftCertificate;
    private final String sqlQuery;

    public GiftCertificatePreparedStatementMapper(GiftCertificate giftCertificate, String sqlQuery) {
        this.giftCertificate = giftCertificate;
        this.sqlQuery = sqlQuery;
    }

    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sqlQuery);
        ps.setString(1, giftCertificate.getName());
        ps.setString(2, giftCertificate.getDescription());
        ps.setBigDecimal(3, giftCertificate.getPrice());
        ps.setInt(4, giftCertificate.getDuration());
        ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
        ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
        if (giftCertificate.getId() != null) {
            ps.setLong(7, giftCertificate.getId());
        }
        return ps;
    }
}
