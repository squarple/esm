package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.impl.OrderDaoImpl;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestServiceConfig.class)
class OrderServiceImplTest {
    @Mock
    private OrderDaoImpl orderDao;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderDao);
    }

    @Test
    void save_ReturnCreatedOrder() throws EntityNotFoundDaoException, EntityNotFoundException {
        User user = TestUtil.getUserList(1).get(0);
        GiftCertificate giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        expectedOrder.setGiftCertificate(giftCertificate);
        expectedOrder.setGiftCertificate(giftCertificate);
        expectedOrder.setCost(giftCertificate.getPrice());
        expectedOrder.setPurchaseDate(LocalDateTime.of(2000,12,1,1,1,1));
        when(orderDao.create(user.getId(), giftCertificate.getId())).thenReturn(expectedOrder);
        Order actualOrder = orderService.save(user.getId(), giftCertificate.getId());
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void get_ExistedOrderId_ReturnFoundedOrder() throws EntityNotFoundDaoException, EntityNotFoundException {
        GiftCertificate giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        expectedOrder.setGiftCertificate(giftCertificate);
        expectedOrder.setGiftCertificate(giftCertificate);
        expectedOrder.setCost(giftCertificate.getPrice());
        expectedOrder.setPurchaseDate(LocalDateTime.of(2000,12,1,1,1,1));
        when(orderDao.find(expectedOrder.getId())).thenReturn(expectedOrder);
        Order actualOrder = orderService.get(expectedOrder.getId());
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void getAll_ReturnAllOrders() throws EntityNotFoundDaoException, EntityNotFoundException, ResourceNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        GiftCertificate giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        expectedOrder.setGiftCertificate(giftCertificate);
        expectedOrder.setGiftCertificate(giftCertificate);
        expectedOrder.setCost(giftCertificate.getPrice());
        expectedOrder.setPurchaseDate(LocalDateTime.of(2000,12,1,1,1,1));
        Page<Order> orders = new PageImpl<>(Stream.of(expectedOrder).collect(Collectors.toList()), pageable, 1);
        when(orderDao.findAll(pageable)).thenReturn(orders);
        Page<Order> actualOrder = orderService.getAll(pageable);
        assertEquals(orders, actualOrder);
    }

    @Test
    void getByUserId_ReturnFoundedOrders() throws EntityNotFoundDaoException, EntityNotFoundException, ResourceNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        User user = TestUtil.getUserList(1).get(0);
        GiftCertificate giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        expectedOrder.setGiftCertificate(giftCertificate);
        expectedOrder.setGiftCertificate(giftCertificate);
        expectedOrder.setCost(giftCertificate.getPrice());
        expectedOrder.setPurchaseDate(LocalDateTime.of(2000,12,1,1,1,1));
        Page<Order> orders = new PageImpl<>(Stream.of(expectedOrder).collect(Collectors.toList()), pageable, 1);
        when(orderDao.findByUserId(user.getId(), pageable)).thenReturn(orders);
        Page<Order> actualOrder = orderService.getByUserId(user.getId(), pageable);
        assertEquals(orders, actualOrder);
    }
}
