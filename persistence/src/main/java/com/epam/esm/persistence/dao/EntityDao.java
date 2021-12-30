package com.epam.esm.persistence.dao;

import com.epam.esm.model.pagination.Pageable;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

/**
 * The interface EntityDao.
 *
 * @param <T>
 */
public interface EntityDao<T> {
    /**
     * Create TypedQuery.
     *
     * @param cq       the criteria query
     * @param pageable the pageable
     * @return the typed query
     */
    TypedQuery<T> createTypedQuery(CriteriaQuery<T> cq, Pageable pageable);

    /**
     * Gets total count of entities.
     *
     *
     * @param predicates@return the total count
     */
    long getTotalCount(Predicate... predicates);
}
