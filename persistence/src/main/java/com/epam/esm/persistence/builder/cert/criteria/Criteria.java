package com.epam.esm.persistence.builder.cert.criteria;

/**
 * The type Criteria.
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
    private final String tagName;
    private final Sort sort;
    private final SortField sortField;

    /**
     * Instantiates a new Criteria.
     *
     * @param name        the name
     * @param description the description
     * @param tagName     the tag name
     * @param sortField   the sort field
     * @param sort        the sort
     */
    public Criteria(String name, String description, String tagName, SortField sortField, Sort sort) {
        this.name = name;
        this.description = description;
        this.sort = sort;
        this.tagName = tagName;
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
    public String getTagName() {
        return tagName;
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
