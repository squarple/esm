package com.epam.esm.model.pagination;

import java.util.List;

/**
 * A page is a sublist of a list of objects. It allows gain information about the position of it in the containing
 * entire list.
 *
 * @param <T>
 */
public interface Page<T> {
    /**
     * Gets number.
     *
     * @return the number
     */
    int getNumber();

    /**
     * Gets size.
     *
     * @return the size
     */
    int getSize();

    /**
     * Gets content.
     *
     * @return the content
     */
    List<T> getContent();

    /**
     * Gets total pages.
     *
     * @return the total pages
     */
    int getTotalPages();
}
