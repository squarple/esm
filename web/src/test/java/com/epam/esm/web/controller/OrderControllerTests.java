package com.epam.esm.web.controller;

import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import com.epam.esm.web.hateoas.assembler.OrderModelAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Profile("test")
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestWebAppContextConfig.class)
class OrderControllerTests {
    @Mock
    private OrderService orderService;

    @Mock
    private OrderModelAssembler orderModelAssembler;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderService, orderModelAssembler);
    }

    @Test
    void createOrder_ReturnCreatedOrder() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        UserDto user = TestUtil.getUserList(1).get(0);
        GiftCertificateDto giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        OrderDto order = TestUtil.createOrder(user, giftCertificate);
        when(orderService.save(user.getId(), giftCertificate.getId())).thenReturn(order);
        when(orderModelAssembler.assembleModel(order)).thenReturn(EntityModel.of(order));
        ResponseEntity<EntityModel<OrderDto>> expected = new ResponseEntity<>(EntityModel.of(order), HttpStatus.CREATED);
        ResponseEntity<EntityModel<OrderDto>> actual = orderController.createOrder(user.getId(), giftCertificate.getId());
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getOrder_ReturnOrder() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        UserDto user = TestUtil.getUserList(1).get(0);
        GiftCertificateDto giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        OrderDto order = TestUtil.createOrder(user, giftCertificate);
        when(orderService.find(order.getId())).thenReturn(order);
        when(orderModelAssembler.assembleModel(order)).thenReturn(EntityModel.of(order));
        ResponseEntity<EntityModel<OrderDto>> expected = new ResponseEntity<>(EntityModel.of(order), HttpStatus.OK);
        ResponseEntity<EntityModel<OrderDto>> actual = orderController.getOrder(order.getId());
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getOrders_ReturnListOfOrder() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        Pageable pageable = TestUtil.getPageable();
        UserDto userDto = TestUtil.getUserList(1).get(0);
        GiftCertificateDto giftCertificateDto = TestUtil.getGiftCertificateList(1).get(0);
        OrderDto orderDto = TestUtil.createOrder(userDto, giftCertificateDto);
        Page<OrderDto> page = new PageImpl<>(Stream.of(orderDto).collect(Collectors.toList()), pageable, 1);

        when(orderService.findAll(pageable)).thenReturn(page);
        when(orderModelAssembler.assemblePagedModel(page, null)).thenReturn(EntityModel.of(page));

        ResponseEntity<EntityModel<Page<OrderDto>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<OrderDto>>> actual = orderController.getOrders(0, 10, null);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }
}
