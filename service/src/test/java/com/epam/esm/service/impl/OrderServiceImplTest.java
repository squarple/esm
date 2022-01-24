package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestServiceConfig.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderRepository, userRepository, giftCertificateRepository);
    }

    @Test
    void find_ExistedOrderId_ReturnFoundedOrder() throws EntityNotFoundException {
        GiftCertificateDto giftCertificateDto = TestUtil.getGiftCertificateDtoList(1).get(0);
        UserDto userDto = TestUtil.getUserDtoList(1).get(0);
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        expectedOrder.setGiftCertificate(giftCertificateDto.toGiftCertificate());
        expectedOrder.setUser(userDto.toUser());
        expectedOrder.setCost(giftCertificateDto.getPrice());
        expectedOrder.setPurchaseDate(LocalDateTime.of(2000,12,1,1,1,1));
        when(orderRepository.findById(expectedOrder.getId())).thenReturn(Optional.of(expectedOrder));
        OrderDto actualOrderDto = orderService.find(expectedOrder.getId());
        assertEquals(OrderDto.fromOrder(expectedOrder), actualOrderDto);
    }

    @Test
    void findAll_ReturnAllOrders() {
        Pageable pageable = TestUtil.getPageable();
        UserDto userDto = TestUtil.getUserDtoList(1).get(0);
        GiftCertificateDto giftCertificate = TestUtil.getGiftCertificateDtoList(1).get(0);
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        expectedOrder.setGiftCertificate(giftCertificate.toGiftCertificate());
        expectedOrder.setUser(userDto.toUser());
        expectedOrder.setCost(giftCertificate.getPrice());
        expectedOrder.setPurchaseDate(LocalDateTime.of(2000,12,1,1,1,1));
        Page<Order> orders = new PageImpl<>(Stream.of(expectedOrder).collect(Collectors.toList()), pageable, 1);
        when(orderRepository.findAll(pageable)).thenReturn(orders);
        Page<OrderDto> actualOrder = orderService.findAll(pageable);
        assertEquals(orders.map(OrderDto::fromOrder), actualOrder);
    }

    @Test
    void findByUserId_ReturnFoundedOrders() {
        Pageable pageable = TestUtil.getPageable();
        UserDto user = TestUtil.getUserDtoList(1).get(0);
        GiftCertificateDto giftCertificate = TestUtil.getGiftCertificateDtoList(1).get(0);
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        expectedOrder.setGiftCertificate(giftCertificate.toGiftCertificate());
        expectedOrder.setUser(user.toUser());
        expectedOrder.setCost(giftCertificate.getPrice());
        expectedOrder.setPurchaseDate(LocalDateTime.of(2000,12,1,1,1,1));
        Page<Order> orders = new PageImpl<>(Stream.of(expectedOrder).collect(Collectors.toList()), pageable, 1);
        when(orderRepository.findByUserId(user.getId(), pageable)).thenReturn(orders);
        Page<OrderDto> actualOrder = orderService.findByUserId(user.getId(), pageable);
        assertEquals(orders.map(OrderDto::fromOrder), actualOrder);
    }
}
