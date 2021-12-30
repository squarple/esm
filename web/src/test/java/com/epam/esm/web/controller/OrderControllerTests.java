package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestWebAppContextConfig.class)
class OrderControllerTests {
    @Mock
    private OrderService orderService;
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderService);
    }

    @Test
    void createOrder_ReturnCreatedOrder() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        User user = TestUtil.getUserList(1).get(0);
        GiftCertificate giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        Order order = TestUtil.createOrder(user, giftCertificate);
        when(orderService.save(user.getId(), giftCertificate.getId())).thenReturn(order);
        ResponseEntity<EntityModel<Order>> expected = new ResponseEntity<>(EntityModel.of(order), HttpStatus.CREATED);
        ResponseEntity<EntityModel<Order>> actual = orderController.createOrder(user.getId(), giftCertificate.getId());
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getOrder_ReturnOrder() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        User user = TestUtil.getUserList(1).get(0);
        GiftCertificate giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        Order order = TestUtil.createOrder(user, giftCertificate);
        when(orderService.get(order.getId())).thenReturn(order);
        ResponseEntity<EntityModel<Order>> expected = new ResponseEntity<>(EntityModel.of(order), HttpStatus.OK);
        ResponseEntity<EntityModel<Order>> actual = orderController.getOrder(order.getId());
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getOrders_ReturnListOfOrder() throws EntityNotFoundException, ResourceNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        Pageable pageable = TestUtil.getPageable();
        User user = TestUtil.getUserList(1).get(0);
        GiftCertificate giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        Order order = TestUtil.createOrder(user, giftCertificate);
        Page<Order> page = new PageImpl<>(Stream.of(order).collect(Collectors.toList()), pageable, 1);
        when(orderService.getAll(pageable)).thenReturn(page);
        ResponseEntity<EntityModel<Page<Order>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<Order>>> actual = orderController.getOrders(0, 10, null);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }
}
