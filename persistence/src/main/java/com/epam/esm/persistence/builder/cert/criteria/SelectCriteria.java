package com.epam.esm.persistence.builder.cert.criteria;

public final class SelectCriteria {
    public enum Sort {
        ASC, DESC
    }

    public enum SortField {
        NAME, DESCRIPTION
    }

    private final String name;
    private final String description;
    private final String tagName;
    private final Sort sort;
    private final SortField sortField;

    public SelectCriteria(String name, String description, String tagName, SortField sortField, Sort sort) {
        this.name = name;
        this.description = description;
        this.sort = sort;
        this.tagName = tagName;
        this.sortField = sortField;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Sort getSort() {
        return sort;
    }

    public String getTagName() {
        return tagName;
    }

    public SortField getSortField() {
        return sortField;
    }
}
