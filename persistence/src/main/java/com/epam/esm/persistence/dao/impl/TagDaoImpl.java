package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.Tag_;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.TagDao;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The TagDao repository.
 */
@Repository
@Transactional
public class TagDaoImpl extends AbstractEntityDao<Tag> implements TagDao {
    private static final String SQL_FIND_MOST_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS =
            "SELECT id, name FROM tags WHERE id = (" +
                "SELECT tagId FROM (" +
                    "SELECT gift_certificate_has_tag.tag_id AS tagId, COUNT(tag_id) tagCount " +
                    "FROM gift_certificate_has_tag " +
                    "WHERE gift_certificate_id IN ( " +
                        "SELECT gift_certificate_id AS certId FROM orders " +
                        "WHERE user_id = ( " +
                            "SELECT userId FROM ( " +
                                "SELECT users.id AS userId, SUM(orders.cost) AS sum " +
                                "FROM orders " +
                                "JOIN users ON users.id = orders.user_id " +
                                "GROUP BY userId " +
                                "ORDER BY sum DESC " +
                                "LIMIT 1 " +
                            ") AS sub (userId, sum) " +
                        ") " +
                    ") " +
                    "GROUP BY tagId " +
                    "ORDER BY tagCount DESC " +
                    "LIMIT 1 " +
                ") as sub2(tagId, tagCount) " +
            ")";

    /**
     * Instantiates a new TagDaoImpl.
     *
     * @param entityManager the entity manager
     */
    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        super(Tag.class, entityManager);
    }

    @Override
    public Tag create(Tag tag) throws EntityAlreadyExistsDaoException {
        if (ifTagExists(tag.getName())) {
            throw new EntityAlreadyExistsDaoException();
        } else {
            entityManager.persist(tag);
        }
        return tag;
    }

    @Override
    public Tag find(Long tagId) throws EntityNotFoundDaoException {
        Tag tag = entityManager.find(Tag.class, tagId);
        if (tag == null) {
            throw new EntityNotFoundDaoException(tagId);
        }
        return tag;
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> root = cq.from(Tag.class);
        cq.select(root);
        TypedQuery<Tag> tq = createTypedQuery(cq, pageable);
        List<Tag> content = tq.getResultList();
        long total = getTotalCount();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Tag> findByName(String tagName, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> root = cq.from(Tag.class);
        cq.select(root)
                .where(cb.like(root.get(Tag_.name), String.join("", "%", tagName, "%")));
        TypedQuery<Tag> tq = createTypedQuery(cq, pageable);
        long total = getTotalCount(cb.like(root.get(Tag_.name), String.join("", "%", tagName, "%")));
        List<Tag> content = tq.getResultList();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public void delete(Long id) throws EntityNotFoundDaoException {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new EntityNotFoundDaoException(id);
        }
        entityManager.remove(tag);
    }

    @Override
    public Tag findMostUsedTagOfUserWithHighestCostOfAllOrders() {
        Query query = entityManager.createNativeQuery(SQL_FIND_MOST_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS, Tag.class);
        return (Tag) query.getResultList().get(0);
    }

    private boolean ifTagExists(String tagName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Tag> root = cq.from(Tag.class);
        cq.select(cb.count(root))
                .where(cb.equal(root.get(Tag_.name), tagName));
        TypedQuery<Long> tq = entityManager.createQuery(cq);
        return tq.getSingleResult() > 0;
    }
}
