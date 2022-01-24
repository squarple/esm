package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Transactional
    @Override
    public OrderDto save(Long userId, Long giftCertificateId) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));
        GiftCertificate giftCertificate = giftCertificateRepository.findById(giftCertificateId)
                .orElseThrow(() -> new EntityNotFoundException(giftCertificateId));
        Order order = new Order();
        order.setGiftCertificate(giftCertificate);
        order.setUser(user);
        order.setPurchaseDate(LocalDateTime.now());
        order.setCost(giftCertificate.getPrice());
        order = orderRepository.save(order);
        return OrderDto.fromOrder(order);
    }

    @Override
    public OrderDto find(Long id) throws EntityNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return OrderDto.fromOrder(order);
    }

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(OrderDto::fromOrder);
    }

    @Override
    public Page<OrderDto> findByUserId(Long id, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByUserId(id, pageable);
        return orderPage.map(OrderDto::fromOrder);
    }
}
