package com.epam.esm.model.pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Basic Page implementation.
 *
 * @param <T> the type of which the page consists
 */
public class PageImpl<T> implements Page<T> {
    private final List<T> content = new ArrayList<>();
    private final Pageable pageable;
    private final long total;

    /**
     * Constructor of PageImpl.
     *
     * @param content  the content
     * @param pageable the pageable
     * @param total    the total
     */
    public PageImpl(List<T> content, Pageable pageable, long total) {
        this.content.addAll(content);
        this.pageable = pageable;
        this.total = total;
    }

    @Override
    public int getNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public int getSize() {
        return pageable.getPageSize();
    }

    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public int getTotalPages() {
        return (int) Math.ceil((double) total / (double) getSize());
    }
}
