package com.epam.esm.web.hateoas.assembler;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import com.epam.esm.web.controller.OrderController;
import com.epam.esm.web.hateoas.util.LinkUtil;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler {
    public EntityModel<OrderDto> assembleModel(OrderDto orderDto) throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        LinkUtil.setOrderLinks(orderDto);
        return EntityModel.of(orderDto);
    }

    public EntityModel<Page<OrderDto>> assemblePagedModel(Page<OrderDto> page, Long userId)
            throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        for (OrderDto order : page.getContent()) {
            LinkUtil.setOrderLinks(order);
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(OrderController.class).getOrders(page.getNumber(), page.getSize(), userId)).withSelfRel());
        links.add(linkTo(methodOn(OrderController.class).getOrders(0, 10, null)).withRel("ordersPage"));
        links.add(linkTo(methodOn(OrderController.class).getOrders(0, page.getSize(), userId)).withRel("first"));
        if (page.getTotalPages() > 0) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(page.getTotalPages() - 1, page.getSize(), userId)).withRel("last"));
        }
        if (page.getNumber() + 1 < page.getTotalPages()) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(page.getNumber() + 1, page.getSize(), userId)).withRel("next"));
        }
        if (page.getNumber() > 0) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(page.getNumber() - 1, page.getSize(), userId)).withRel("previous"));
        }
        return EntityModel.of(page, links);
    }
}
