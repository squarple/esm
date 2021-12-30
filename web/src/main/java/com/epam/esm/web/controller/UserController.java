package com.epam.esm.web.controller;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageRequest;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.web.hateoas.LinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The UserController.
 */
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    /**
     * Instantiates a new UserController.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @PostMapping
    public ResponseEntity<EntityModel<User>> createUser(
            @Validated(OnCreate.class) @RequestBody User user
    ) throws EntityAlreadyExistsException, EntityNotFoundException {
        user = userService.save(user);
        LinkUtil.setUserLinks(user);
        return new ResponseEntity<>(EntityModel.of(user), HttpStatus.CREATED);
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     * @throws EntityNotFoundException if entity not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getUser(@PathVariable Long id) throws EntityNotFoundException {
        User user = userService.get(id);
        LinkUtil.setUserLinks(user);
        return new ResponseEntity<>(EntityModel.of(user), HttpStatus.OK);
    }

    /**
     * Gets users.
     *
     * @param page the page
     * @param size the size
     * @return the users
     * @throws ResourceNotFoundException if resource not found
     * @throws EntityNotFoundException   if entity not found
     */
    @GetMapping
    public ResponseEntity<EntityModel<Page<User>>> getUsers(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) throws ResourceNotFoundException, EntityNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userService.getAll(pageable);
        for (User user : usersPage.getContent()) {
            LinkUtil.setUserLinks(user);
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(UserController.class).getUsers(page, size)).withSelfRel());
        links.add(linkTo(methodOn(UserController.class).getUsers(0, 10)).withRel("usersPage"));
        links.add(linkTo(methodOn(UserController.class).getUsers(0, size)).withRel("first"));
        if (usersPage.getTotalPages() > 0) {
            links.add(linkTo(methodOn(UserController.class).getUsers(usersPage.getTotalPages() - 1, size)).withRel("last"));
        }
        if (usersPage.getNumber() + 1 < usersPage.getTotalPages()) {
            links.add(linkTo(methodOn(UserController.class).getUsers(usersPage.getNumber() + 1, size)).withRel("next"));
        }
        if (usersPage.getNumber() > 0) {
            links.add(linkTo(methodOn(UserController.class).getUsers(usersPage.getNumber() - 1, size)).withRel("previous"));
        }
        return new ResponseEntity<>(EntityModel.of(usersPage, links), HttpStatus.OK);
    }
}
