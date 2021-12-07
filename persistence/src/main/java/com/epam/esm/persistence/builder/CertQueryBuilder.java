package com.epam.esm.persistence.builder;

import java.util.ArrayList;
import java.util.List;

public class CertQueryBuilder {
    private final List<Object> params;
    private final Criteria criteria;
    private String query;

    private String CONJUNCTION;

    public CertQueryBuilder(Criteria criteria) {
        params = new ArrayList<>();
        this.criteria = criteria;
        query = "";
        CONJUNCTION = " WHERE";
        configureQuery();
    }

    public List<Object> getParams() {
        return params;
    }

    public String getQuery() {
        return query;
    }

    private void configureQuery() {
        query = "SELECT * FROM gift_certificate";
        if (criteria.getName() != null) {
            String name = String.join("", "%", criteria.getName(), "%");
            params.add(name);
            query = String.join("", query, CONJUNCTION, " name LIKE ?");
            CONJUNCTION = " AND";
        }
        if (criteria.getDescription() != null) {
            String description = String.join("", "%", criteria.getDescription(), "%");
            query = String.join("", query, CONJUNCTION, " description LIKE ?");
            params.add(description);
            CONJUNCTION = " AND";
        }
        if (criteria.getTagName() != null) {
            query = String.join("", query, CONJUNCTION, " id IN (" +
                    "SELECT gift_certificate_id " +
                    "FROM gift_certificate_has_tag " +
                    "WHERE tag_id = " +
                    "(SELECT id FROM tag WHERE name = ?))");
            params.add(criteria.getTagName());
            CONJUNCTION = " AND";
        }
        if (criteria.getSort() != null && criteria.getSortField() != null) {
            String subQuery = String.join("", " ORDER BY ",
                    criteria.getSortField().toString().toLowerCase(),
                    criteria.getSort().equals(Criteria.Sort.ASC) ? " ASC" : " DESC");
            query = String.join("", query, subQuery);
        }
    }
}
