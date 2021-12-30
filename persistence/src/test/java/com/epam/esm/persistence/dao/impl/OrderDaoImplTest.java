package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.*;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.dao.OrderDao;
import com.epam.esm.persistence.dao.UserDao;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@EntityScan("com.epam.esm")
@SpringBootTest(classes = TestPersistenceConfig.class)
@Transactional
class OrderDaoImplTest {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    void create_Successful() throws EntityAlreadyExistsDaoException, EntityNotFoundDaoException, ForbiddenActionException {
        User user = userDao.create(TestUtil.getUserList(1).get(0));
        GiftCertificate giftCertificate = giftCertificateDao.create(TestUtil.getGiftCertificateList(1).get(0));
        Order order = orderDao.create(user.getId(), giftCertificate.getId());
        assertNotNull(order);
        assertNotNull(order.getId());
        deleteOrder(order.getId());
        deleteUser(user.getId());
        giftCertificateDao.delete(giftCertificate.getId());
    }

    @Test
    void findOrder_ReturnFoundedOrder() throws EntityAlreadyExistsDaoException, EntityNotFoundDaoException, ForbiddenActionException {
        User user = userDao.create(TestUtil.getUserList(1).get(0));
        GiftCertificate giftCertificate = giftCertificateDao.create(TestUtil.getGiftCertificateList(1).get(0));
        Order order = orderDao.create(user.getId(), giftCertificate.getId());
        Order actualOrder = orderDao.find(order.getId());
        assertEquals(order, actualOrder);
        deleteOrder(order.getId());
        deleteUser(user.getId());
        giftCertificateDao.delete(giftCertificate.getId());
    }

    @Test
    void findAll_ReturnAllOrders() throws EntityAlreadyExistsDaoException, EntityNotFoundDaoException, ForbiddenActionException {
        Pageable pageable = TestUtil.getPageable();
        User user = userDao.create(TestUtil.getUserList(1).get(0));
        GiftCertificate giftCertificate = giftCertificateDao.create(TestUtil.getGiftCertificateList(1).get(0));
        Order order = orderDao.create(user.getId(), giftCertificate.getId());
        List<Order> actualOrders = orderDao.findAll(pageable).getContent();
        assertEquals(Stream.of(order).collect(Collectors.toSet()),
                actualOrders.stream().collect(Collectors.toSet()));
        deleteOrder(order.getId());
        deleteUser(user.getId());
        giftCertificateDao.delete(giftCertificate.getId());
    }

    @Test
    void findByUserId_ReturnFoundedOrders() throws EntityAlreadyExistsDaoException, EntityNotFoundDaoException, ForbiddenActionException {
        Pageable pageable = TestUtil.getPageable();
        User user = userDao.create(TestUtil.getUserList(1).get(0));
        GiftCertificate giftCertificate = giftCertificateDao.create(TestUtil.getGiftCertificateList(1).get(0));
        Order order = orderDao.create(user.getId(), giftCertificate.getId());
        List<Order> actualOrders = orderDao.findByUserId(user.getId(), pageable).getContent();
        assertEquals(Stream.of(order).collect(Collectors.toSet()),
                actualOrders.stream().collect(Collectors.toSet()));
        deleteOrder(order.getId());
        deleteUser(user.getId());
        giftCertificateDao.delete(giftCertificate.getId());
    }

    private void deleteOrder(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Order> cd = cb.createCriteriaDelete(Order.class);
        Root<Order> fromOrder = cd.from(Order.class);
        cd.where(cb.equal(fromOrder.get(Order_.id), id));
        entityManager.createQuery(cd).executeUpdate();
    }

    void deleteUser(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<User> cd = cb.createCriteriaDelete(User.class);
        Root<User> fromUser = cd.from(User.class);
        cd.where(cb.equal(fromUser.get(User_.id), id));
        entityManager.createQuery(cd).executeUpdate();
    }
}
