package com.epam.esm.persistence.dao;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;

/**
 * The interface OrderDao.
 */
public interface OrderDao {
    /**
     * Create order.
     *
     * @param userId            the user id
     * @param giftCertificateId the gift certificate id
     * @return the order
     * @throws EntityNotFoundDaoException if entity not found
     */
    Order create(Long userId, Long giftCertificateId) throws EntityNotFoundDaoException;

    /**
     * Find order.
     *
     * @param id the id
     * @return the order
     * @throws EntityNotFoundDaoException if entity not found
     */
    Order find(Long id) throws EntityNotFoundDaoException;

    /**
     * Find all orders.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<Order> findAll(Pageable pageable);

    /**
     * Find by user id.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the page
     */
    Page<Order> findByUserId(Long id, Pageable pageable);
}
