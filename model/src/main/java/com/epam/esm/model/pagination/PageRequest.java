package com.epam.esm.model.pagination;

/**
 * Basic implementation of Pageable.
 */
public class PageRequest implements Pageable {
    private final int page;
    private final int size;

    /**
     * Creates a new PageRequest.
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     */
    public PageRequest(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than zero");
        }
        this.page = page;
        this.size = size;
    }

    /**
     * Creates a new PageRequest.
     *
     * @param page the page
     * @param size the size
     * @return the page request
     */
    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return (long) page * (long) size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PageRequest that = (PageRequest) o;
        return getPageNumber() == that.getPageNumber() &&
                getPageSize() == that.getPageSize();
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(getPageNumber());
        result = result * 31 + Integer.hashCode(getPageNumber());
        return result;
    }
}
