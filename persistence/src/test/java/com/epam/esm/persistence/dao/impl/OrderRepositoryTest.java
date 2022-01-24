package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@EntityScan("com.epam.esm")
@EnableJpaRepositories("com.epam.esm.persistence.repository")
@SpringBootTest(classes = TestPersistenceConfig.class)
@Transactional
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserId_ReturnFoundedOrders() {
        Pageable pageable = TestUtil.getPageable();
        User user = userRepository.save(TestUtil.getUserList(1).get(0));
        GiftCertificate giftCertificate = giftCertificateRepository.save(TestUtil.getGiftCertificateList(1).get(0));
        Order order = new Order();
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setCost(giftCertificate.getPrice());
        order.setPurchaseDate(LocalDateTime.now());
        order = orderRepository.save(order);
        Order actualOrder = orderRepository.findByUserId(user.getId(), pageable).getContent().get(0);
        assertEquals(order, actualOrder);
        giftCertificateRepository.deleteById(giftCertificate.getId());
        userRepository.deleteById(user.getId());
        orderRepository.deleteById(order.getId());
    }

    @Test
    void findByUserId_ReturnEmptyPage() {
        Pageable pageable = TestUtil.getPageable();
        Page<Order> orderPage = orderRepository.findByUserId(100L, pageable);
        assertFalse(orderPage.hasContent());
    }

    @Test
    void countByGiftCertificateId_ReturnCount() {
        Long count = orderRepository.countByGiftCertificateId(100L);
        assertEquals(0, count);
    }
}
