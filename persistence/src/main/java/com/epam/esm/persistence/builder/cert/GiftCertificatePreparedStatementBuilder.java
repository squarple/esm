package com.epam.esm.persistence.builder.cert;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.*;

/**
 * The type Gift certificate prepared statement builder.
 */
public class GiftCertificatePreparedStatementBuilder implements PreparedStatementCreator {
    private final GiftCertificate giftCertificate;
    private final String sqlQuery;

    /**
     * Instantiates a new Gift certificate prepared statement builder.
     *
     * @param giftCertificate the gift certificate
     * @param sqlQuery        the sql query
     */
    public GiftCertificatePreparedStatementBuilder(GiftCertificate giftCertificate, String sqlQuery) {
        this.giftCertificate = giftCertificate;
        this.sqlQuery = sqlQuery;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
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
