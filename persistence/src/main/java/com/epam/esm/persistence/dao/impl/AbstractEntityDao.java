package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.EntityDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * The AbstractEntityDao.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractEntityDao<T> implements EntityDao<T> {
    private final Class<T> entityClass;
    /**
     * The Entity manager.
     */
    protected final EntityManager entityManager;

    /**
     * Instantiates a new AbstractEntityDao.
     *
     * @param entityClass the entity class
     * @param em          the entity manager
     */
    protected AbstractEntityDao(Class<T> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.entityManager = em;
    }

    @Override
    public TypedQuery<T> createTypedQuery(CriteriaQuery<T> cq, Pageable pageable) {
        return entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());
    }

    @Override
    public long getTotalCount(Predicate... predicates) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> fromEntity = cq.from(entityClass);
        cq.select(cb.count(fromEntity));
        if (predicates.length > 0) {
            cq.where(predicates);
        }
        return entityManager.createQuery(cq).getSingleResult();
    }
}
