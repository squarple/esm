package com.epam.esm.persistence.repository;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Gift certificateRepository.
 */
@Transactional
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    /**
     * Find certificates by name and description page.
     *
     * @param name        the name
     * @param description the description
     * @param pageable    the pageable
     * @return the page
     */
    Page<GiftCertificate> findByNameContainingAndDescriptionContaining(String name, String description, Pageable pageable);
}
