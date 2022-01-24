package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface OrderService.
 */
public interface OrderService {
    /**
     * Save order.
     *
     * @param userId            the user id
     * @param giftCertificateId the gift certificate id
     * @return the order
     * @throws EntityNotFoundException if user or certificate not found
     */
    OrderDto save(Long userId, Long giftCertificateId) throws EntityNotFoundException;

    /**
     * Find order.
     *
     * @param id the id
     * @return the order
     * @throws EntityNotFoundException if order not found
     */
    OrderDto find(Long id) throws EntityNotFoundException;

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<OrderDto> findAll(Pageable pageable);

    /**
     * Find orders by user id.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the page
     */
    Page<OrderDto> findByUserId(Long id, Pageable pageable);
}