package com.epam.esm.persistence.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface TagRepository.
 */
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * Find tags by name.
     *
     * @param name     the name
     * @param pageable the pageable
     * @return the page
     */
    Page<Tag> findByNameContaining(String name, Pageable pageable);

    /**
     * Check is tag exists.
     *
     * @param name the name
     * @return true if exists, false if not
     */
    boolean existsByName(String name);

    /**
     * Find tag
     * @param name tag name
     * @return tag
     */
    Tag getByName(String name);

    /**
     * Find most used tag of user with highest cost of all orders tag.
     *
     * @return the tag
     */
    @Query(value = "SELECT id, name FROM tags WHERE id = (" +
                "SELECT tagId " +
                "FROM (" +
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
            ")", nativeQuery = true)
    Tag findMostUsedTagOfUserWithHighestCostOfAllOrders();
}