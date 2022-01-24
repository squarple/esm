package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@EntityScan("com.epam.esm")
@SpringBootTest(classes = TestPersistenceConfig.class)
@EnableJpaRepositories("com.epam.esm.persistence.repository")
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_ExistedUsername_ReturnUser() {
        User expectedUser = TestUtil.getUserList(1).get(0);
        expectedUser = userRepository.save(expectedUser);
        User actualUser = userRepository.findByUsername(expectedUser.getUsername());
        assertEquals(expectedUser, actualUser);
        userRepository.deleteById(expectedUser.getId());
    }

    @Test
    void findByUsername_NonExistedUsername_ReturnNull() {
        User actualUser = userRepository.findByUsername("username");
        assertNull(actualUser);
    }
}
