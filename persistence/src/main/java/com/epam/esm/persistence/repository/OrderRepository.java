package com.epam.esm.persistence.repository;

import com.epam.esm.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface OrderRepository.
 */
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find order of user.
     *
     * @param id       the user id
     * @param pageable the pageable
     * @return the page
     */
    Page<Order> findByUserId(Long id, Pageable pageable);

    /**
     * Count orders by gift certificate.
     *
     * @param id the gift certificate id
     * @return the count of certificates
     */
    Long countByGiftCertificateId(Long id);
}
