package com.epam.esm.model.pagination;

/**
 * Abstract interface for pagination information.
 */
public interface Pageable {
    /**
     * Gets page number.
     *
     * @return the page number
     */
    int getPageNumber();

    /**
     * Gets page size.
     *
     * @return the page size
     */
    int getPageSize();

    /**
     * Gets offset.
     *
     * @return the offset
     */
    long getOffset();
}
