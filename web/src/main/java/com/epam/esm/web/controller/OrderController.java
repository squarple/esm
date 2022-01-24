package com.epam.esm.web.controller;

import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import com.epam.esm.web.hateoas.assembler.OrderModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The OrderController.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderModelAssembler assembler;

    /**
     * Instantiates a new OrderController.
     *
     * @param orderService the order service
     * @param orderModelAssembler order model assembler
     */
    @Autowired
    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler) {
        this.orderService = orderService;
        this.assembler = orderModelAssembler;
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
    @PreAuthorize("hasAuthority('SCOPE_order:write') || hasAuthority('USER_ID:' + #userId)")
    @PostMapping
    public ResponseEntity<EntityModel<OrderDto>> createOrder(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "certId") Long giftCertificateId
    ) throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        OrderDto orderDto = orderService.save(userId, giftCertificateId);
        EntityModel<OrderDto> model = assembler.assembleModel(orderDto);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
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
    @PostAuthorize("hasAuthority('SCOPE_order:read') || hasAuthority('USER_ID:' + returnObject.body.content.userDto.id)")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDto>> getOrder(@PathVariable Long id)
            throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        OrderDto orderDto = orderService.find(id);
        EntityModel<OrderDto> model = assembler.assembleModel(orderDto);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Gets orders.
     *
     * @param page   the page
     * @param size   the size
     * @param userId the user id
     * @return the orders
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     * @throws ForbiddenActionException     if forbidden action
     */
    @PreAuthorize("hasAuthority('SCOPE_order:read') || hasAuthority('USER_ID:' + #userId)")
    @GetMapping
    public ResponseEntity<EntityModel<Page<OrderDto>>> getOrders(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "userId", required = false) Long userId
    ) throws EntityAlreadyExistsException, EntityNotFoundException, ForbiddenActionException {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDto> ordersPage;
        if (userId == null) {
            ordersPage = orderService.findAll(pageable);
        } else {
            ordersPage = orderService.findByUserId(userId, pageable);
        }
        EntityModel<Page<OrderDto>> model = assembler.assemblePagedModel(ordersPage, userId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
