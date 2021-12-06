package com.epam.esm.model.mapper;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        BigDecimal price = rs.getBigDecimal("price");
        Integer duration = rs.getInt("duration");
        LocalDateTime createDate = rs.getTimestamp("create_date").toLocalDateTime();
        LocalDateTime lastUpdateDate = rs.getTimestamp("last_update_date").toLocalDateTime();
        return GiftCertificate.builder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setDuration(duration)
                .setCreateDate(createDate)
                .setLastUpdateDate(lastUpdateDate)
                .build();
    }
}
