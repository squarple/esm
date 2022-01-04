package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageRequest;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.web.hateoas.LinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The OrderController.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * Instantiates a new OrderController.
     *
     * @param orderService the order service
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create order.
     *
     * @param userId            the user id
     * @param giftCertificateId the gift certificate id
     * @return the order
     * @throws EntityNotFoundException      if entity not found
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws ForbiddenActionException     if forbidden action
     */
    @PostMapping
    public ResponseEntity<EntityModel<Order>> createOrder(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "certId") Long giftCertificateId
    ) throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        Order order = orderService.save(userId, giftCertificateId);
        LinkUtil.setOrderLinks(order);
        return new ResponseEntity<>(EntityModel.of(order), HttpStatus.CREATED);
    }

    /**
     * Gets order.
     *
     * @param id the id
     * @return the order
     * @throws EntityNotFoundException      if entity not found
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws ForbiddenActionException     if forbidden action
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Order>> getOrder(@PathVariable Long id)
            throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        Order order = orderService.get(id);
        LinkUtil.setOrderLinks(order);
        return new ResponseEntity<>(EntityModel.of(order), HttpStatus.OK);
    }

    /**
     * Gets orders.
     *
     * @param page   the page
     * @param size   the size
     * @param userId the user id
     * @return the orders
     * @throws ResourceNotFoundException    if resource not found
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     * @throws ForbiddenActionException     if forbidden action
     */
    @GetMapping
    public ResponseEntity<EntityModel<Page<Order>>> getOrders(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "userId", required = false) Long userId
    ) throws ResourceNotFoundException, EntityAlreadyExistsException, EntityNotFoundException, ForbiddenActionException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> ordersPage;
        if (userId == null) {
            ordersPage = orderService.getAll(pageable);
        } else {
            ordersPage = orderService.getByUserId(userId, pageable);
        }
        for (Order order : ordersPage.getContent()) {
            LinkUtil.setOrderLinks(order);
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(OrderController.class).getOrders(page, size, userId)).withSelfRel());
        links.add(linkTo(methodOn(OrderController.class).getOrders(0, 10, null)).withRel("ordersPage"));
        links.add(linkTo(methodOn(OrderController.class).getOrders(0, size, userId)).withRel("first"));
        if (ordersPage.getTotalPages() > 0) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(ordersPage.getTotalPages() - 1, size, userId)).withRel("last"));
        }
        if (ordersPage.getNumber() + 1 < ordersPage.getTotalPages()) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(ordersPage.getNumber() + 1, size, userId)).withRel("next"));
        }
        if (ordersPage.getNumber() > 0) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(ordersPage.getNumber() - 1, size, userId)).withRel("previous"));
        }
        return new ResponseEntity<>(EntityModel.of(ordersPage, links), HttpStatus.OK);
    }
}
