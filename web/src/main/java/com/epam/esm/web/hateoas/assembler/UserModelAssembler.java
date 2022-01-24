package com.epam.esm.web.hateoas.assembler;

import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.controller.UserController;
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
public class UserModelAssembler {
    public EntityModel<UserDto> assembleModel(UserDto userDto) throws EntityNotFoundException {
        LinkUtil.setUserLinks(userDto);
        return EntityModel.of(userDto);
    }

    public EntityModel<Page<UserDto>> assemblePagedModel(Page<UserDto> page) throws EntityNotFoundException {
        for (UserDto user : page.getContent()) {
            LinkUtil.setUserLinks(user);
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(UserController.class).getUsers(page.getNumber(), page.getSize())).withSelfRel());
        links.add(linkTo(methodOn(UserController.class).getUsers(0, 10)).withRel("usersPage"));
        links.add(linkTo(methodOn(UserController.class).getUsers(0, page.getSize())).withRel("first"));
        if (page.getTotalPages() > 0) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page.getTotalPages() - 1, page.getSize())).withRel("last"));
        }
        if (page.getNumber() + 1 < page.getTotalPages()) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page.getNumber() + 1, page.getSize())).withRel("next"));
        }
        if (page.getNumber() > 0) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page.getNumber() - 1, page.getSize())).withRel("previous"));
        }
        return EntityModel.of(page, links);
    }
}
