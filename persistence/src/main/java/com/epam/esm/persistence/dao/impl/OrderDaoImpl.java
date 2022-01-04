package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.*;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.OrderDao;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The OrderDao repository.
 */
@Repository
@Transactional
public class OrderDaoImpl extends AbstractEntityDao<Order> implements OrderDao {

    /**
     * Instantiates a new OrderDaoImpl.
     *
     * @param entityManager the entity manager
     */
    @Autowired
    public OrderDaoImpl(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    @Override
    public Order create(Long userId, Long giftCertificateId) throws EntityNotFoundDaoException {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new EntityNotFoundDaoException(userId);
        }
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, giftCertificateId);
        if (giftCertificate == null) {
            throw new EntityNotFoundDaoException(giftCertificateId);
        }
        Order order = new Order();
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setPurchaseDate(LocalDateTime.now());
        order.setCost(giftCertificate.getPrice());
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order find(Long id) throws EntityNotFoundDaoException {
        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            throw new EntityNotFoundDaoException(id);
        }
        return order;
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> fromOrder = cq.from(Order.class);
        cq.select(fromOrder);
        TypedQuery<Order> tq = createTypedQuery(cq, pageable);
        long total = getTotalCount();
        List<Order> content = tq.getResultList();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Order> findByUserId(Long id, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> fromOrder = cq.from(Order.class);
        Path<User> user = fromOrder.get(Order_.user);
        Path<Long> userId = user.get(User_.id);
        cq.select(fromOrder)
                .where(cb.equal(userId, id));
        TypedQuery<Order> tq = createTypedQuery(cq, pageable);
        long total = getTotalCount(cb.equal(userId, id));
        List<Order> content = tq.getResultList();
        return new PageImpl<>(content, pageable, total);
    }
}
