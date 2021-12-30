package com.epam.esm.service;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Save order.
     *
     * @param userId            the user id
     * @param giftCertificateId the gift certificate id
     * @return the order
     * @throws EntityNotFoundException if entity not found
     */
    Order save(Long userId, Long giftCertificateId) throws EntityNotFoundException;

    /**
     * Get order.
     *
     * @param id the id
     * @return the order
     * @throws EntityNotFoundException if entity not found
     */
    Order get(Long id) throws EntityNotFoundException;

    /**
     * Gets all orders.
     *
     * @param pageable the pageable
     * @return the page
     * @throws ResourceNotFoundException if resource not found
     */
    Page<Order> getAll(Pageable pageable) throws ResourceNotFoundException;

    /**
     * Gets orders by user id.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the page
     * @throws ResourceNotFoundException if resource not found
     */
    Page<Order> getByUserId(Long id, Pageable pageable) throws ResourceNotFoundException;
}
