package com.epam.esm.persistence.repository;

import com.epam.esm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface UserRepository.
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by username.
     *
     * @param username the username
     * @return the user
     */
    User findByUsername(String username);
}
