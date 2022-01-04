package com.epam.esm.persistence.dao.criteria.cert;

import java.util.List;

/**
 * The Criteria for GiftCertificate filtration.
 */
public final class Criteria {
    /**
     * The enum Sort.
     */
    public enum Sort {
        /**
         * Asc sort.
         */
        ASC,
        /**
         * Desc sort.
         */
        DESC,
        /**
         * None sort.
         */
        NONE
    }

    /**
     * The enum Sort field.
     */
    public enum SortField {
        /**
         * Name sort field.
         */
        NAME,
        /**
         * Description sort field.
         */
        DESCRIPTION,
        /**
         * None sort field.
         */
        NONE
    }

    private final String name;
    private final String description;
    private final List<String> tagNames;
    private final Sort sort;
    private final SortField sortField;

    /**
     * Instantiates a new Criteria.
     *
     * @param name        the name
     * @param description the description
     * @param tagNames     the tag name
     * @param sortField   the sort field
     * @param sort        the sort
     */
    public Criteria(String name, String description, List<String> tagNames, SortField sortField, Sort sort) {
        this.name = name;
        this.description = description;
        this.sort = sort;
        this.tagNames = tagNames;
        this.sortField = sortField;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets sort.
     *
     * @return the sort
     */
    public Sort getSort() {
        return sort;
    }

    /**
     * Gets tag name.
     *
     * @return the tag name
     */
    public List<String> getTagNames() {
        return tagNames;
    }

    /**
     * Gets sort field.
     *
     * @return the sort field
     */
    public SortField getSortField() {
        return sortField;
    }
}
