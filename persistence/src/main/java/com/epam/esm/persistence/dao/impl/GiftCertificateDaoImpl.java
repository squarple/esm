package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.*;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.dao.criteria.cert.Criteria;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The GiftCertificateDao repository.
 */
@Repository
@Transactional
public class GiftCertificateDaoImpl extends AbstractEntityDao<GiftCertificate> implements GiftCertificateDao {

    /**
     * Instantiates a new GiftCertificateDaoImpl.
     *
     * @param entityManager the entity manager
     */
    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        super(GiftCertificate.class, entityManager);
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        if (giftCertificate.getTags() == null) {
            giftCertificate.setTags(new ArrayList<>());
        }
        List<Tag> tags = giftCertificate.getTags();
        for (int i = 0; i < tags.size(); i++) {
            if (ifTagExists(tags.get(i).getName())) {
                tags.set(i, getTagByName(tags.get(i).getName()));
            } else {
                entityManager.persist(tags.get(i));
            }
        }
        return giftCertificate;
    }

    @Override
    public GiftCertificate find(Long id) throws EntityNotFoundDaoException {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate == null) {
            throw new EntityNotFoundDaoException(id);
        }
        return giftCertificate;
    }

    @Override
    public void delete(Long id) throws ForbiddenActionException, EntityNotFoundDaoException {
        if (countOrdersByUserId(id) > 0L) {
            throw new ForbiddenActionException(id);
        }
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate == null) {
            throw new EntityNotFoundDaoException(id);
        }
        giftCertificate.setTags(new ArrayList<>());
        entityManager.remove(giftCertificate);
    }

    @Override
    public Page<GiftCertificate> findAll(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = cq.from(GiftCertificate.class);
        CriteriaQuery<GiftCertificate> all = cq.select(root);
        TypedQuery<GiftCertificate> tq = createTypedQuery(all, pageable);
        long total = getTotalCount();
        List<GiftCertificate> content = tq.getResultList();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<GiftCertificate> find(Criteria criteria, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> fromGiftCertificates = cq.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getName() != null) {
            predicates.add(cb.like(fromGiftCertificates.get(GiftCertificate_.name), String.join("", "%", criteria.getName(), "%")));
        }
        if (criteria.getDescription() != null) {
            predicates.add(cb.like(fromGiftCertificates.get(GiftCertificate_.description), String.join("", "%", criteria.getDescription(), "%")));
        }
        if (criteria.getTagNames() != null && !criteria.getTagNames().isEmpty()) {
            CriteriaQuery<Tag> tagCq = cb.createQuery(Tag.class);
            Root<Tag> fromTag = tagCq.from(Tag.class);
            CriteriaBuilder.In<String> inClause = cb.in(fromTag.get(Tag_.name));
            for (String tagName : criteria.getTagNames()) {
                inClause.value(tagName);
            }
            tagCq.select(fromTag).where(inClause);
            List<Tag> tagList = entityManager.createQuery(tagCq).getResultList();

            for (Tag tag : tagList) {
                Predicate p = cb.isMember(tag, fromGiftCertificates.get(GiftCertificate_.tags));
                predicates.add(p);
            }
        }
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        cq.select(fromGiftCertificates).where(predicatesArray);
        long total = getTotalCount(/*predicatesArray*/);
        if (criteria.getSort() != null && criteria.getSortField() != null &&
                !criteria.getSort().equals(Criteria.Sort.NONE) &&
                !criteria.getSortField().equals(Criteria.SortField.NONE)) {
            SingularAttribute<GiftCertificate, String> orderBy =
                    criteria.getSortField() == Criteria.SortField.DESCRIPTION
                            ? GiftCertificate_.description
                            : GiftCertificate_.name;
            if (criteria.getSort().equals(Criteria.Sort.ASC)) {
                cq.orderBy(cb.asc(fromGiftCertificates.get(orderBy)));
            } else {
                cq.orderBy(cb.desc(fromGiftCertificates.get(orderBy)));
            }
        }
        TypedQuery<GiftCertificate> tq = createTypedQuery(cq, pageable);
        List<GiftCertificate> content = tq.getResultList();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws EntityNotFoundDaoException {
        GiftCertificate realGiftCertificate = entityManager.find(GiftCertificate.class, giftCertificate.getId());
        if (realGiftCertificate == null) {
            throw new EntityNotFoundDaoException(giftCertificate.getId());
        }
        if (giftCertificate.getName() != null && !giftCertificate.getName().isEmpty()) {
            realGiftCertificate.setName(giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null && !giftCertificate.getDescription().isEmpty()) {
            realGiftCertificate.setDescription(giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null && giftCertificate.getPrice().compareTo(BigDecimal.valueOf(0)) > 0) {
            realGiftCertificate.setPrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != null && giftCertificate.getDuration() > 0 && giftCertificate.getDuration() < 365) {
            realGiftCertificate.setDuration(giftCertificate.getDuration());
        }
        realGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        if (giftCertificate.getTags() != null) {
            realGiftCertificate.getTags()
                    .removeIf(tag -> giftCertificate.getTags()
                            .stream()
                            .map(Tag::getName)
                            .noneMatch(a -> a.equals(tag.getName())));
            for (Tag tag : giftCertificate.getTags()) {
                if (realGiftCertificate.getTags().stream()
                        .map(Tag::getName)
                        .noneMatch(t -> t.equals(tag.getName()))) {
                    if (ifTagExists(tag.getName())) {
                        realGiftCertificate.getTags().add(getTagByName(tag.getName()));
                    } else {
                        entityManager.persist(tag);
                        realGiftCertificate.getTags().add(tag);
                    }
                }
            }
        }
        return realGiftCertificate;
    }

    @Override
    public boolean isPossibleToDelete(Long id) {
        return countOrdersByUserId(id) == 0;
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

    private Tag getTagByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> root = cq.from(Tag.class);
        cq.select(root).where(cb.like(root.get(Tag_.name), name));
        return entityManager.createQuery(cq).getSingleResult();
    }

    private long countOrdersByUserId(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Order> fromOrder = cq.from(Order.class);
        Path<GiftCertificate> user = fromOrder.get(Order_.giftCertificate);
        Path<Long> userId = user.get(GiftCertificate_.id);
        cq.select(cb.count(fromOrder))
                .where(cb.equal(userId, id));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
