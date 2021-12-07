package com.epam.esm.persistence.builder.cert;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.builder.cert.criteria.SelectCriteria;
import com.epam.esm.persistence.builder.cert.criteria.UpdateCriteria;

import java.util.ArrayList;
import java.util.List;

public class GiftCertificateQueryBuilder {
    private final List<Object> params = new ArrayList<>();

    public static GiftCertificateQueryBuilder builder() {
        return new GiftCertificateQueryBuilder();
    }

    public List<Object> getParams() {
        return params;
    }

    public String configureSelectQuery(SelectCriteria selectCriteria) {
        String query = "SELECT * FROM gift_certificate";
        String conjunction = " WHERE";
        if (selectCriteria.getName() != null) {
            String name = String.join("", "%", selectCriteria.getName(), "%");
            params.add(name);
            query = String.join("", query, conjunction, " name LIKE ?");
            conjunction = " AND";
        }
        if (selectCriteria.getDescription() != null) {
            String description = String.join("", "%", selectCriteria.getDescription(), "%");
            query = String.join("", query, conjunction, " description LIKE ?");
            params.add(description);
            conjunction = " AND";
        }
        if (selectCriteria.getTagName() != null) {
            query = String.join("", query, conjunction, " id IN (" +
                    "SELECT gift_certificate_id " +
                    "FROM gift_certificate_has_tag " +
                    "WHERE tag_id = " +
                    "(SELECT id FROM tag WHERE name = ?))");
            params.add(selectCriteria.getTagName());
        }
        if (selectCriteria.getSort() != null && selectCriteria.getSortField() != null) {
            String subQuery = String.join("", " ORDER BY ",
                    selectCriteria.getSortField().toString().toLowerCase(),
                    selectCriteria.getSort().equals(SelectCriteria.Sort.ASC) ? " ASC" : " DESC");
            query = String.join("", query, subQuery);
        }
        return query;
    }

    public String configureUpdateCriteria(GiftCertificate cert, UpdateCriteria criteria) {
        String query = "UPDATE gift_certificate SET ";
        String conjunction = "";
        if (criteria.isName()) {
            query = String.join("", query, conjunction, "name = ?");
            params.add(cert.getName());
            conjunction = ", ";
        }
        if (criteria.isDescription()) {
            query = String.join("", query, conjunction, "description = ?");
            params.add(cert.getDescription());
            conjunction = ", ";
        }
        if (criteria.isPrice()) {
            query = String.join("", query, conjunction, "price = ?");
            params.add(cert.getPrice());
            conjunction = ", ";
        }
        if (criteria.isDuration()) {
            query = String.join("", query, conjunction, "duration = ?");
            params.add(cert.getDuration());
            conjunction = ", ";
        }
        if (criteria.isCreateDate()) {
            query = String.join("", query, conjunction, "create_date = ?");
            params.add(cert.getCreateDate());
            conjunction = ", ";
        }
        if (criteria.isLastUpdateDate()) {
            query = String.join("", query, conjunction, "last_update_date = ?");
            params.add(cert.getCreateDate());
        }
        query = String.join("", query, " WHERE id = ?");
        params.add(cert.getId());
        return query;
    }
}
