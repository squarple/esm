package com.epam.esm.web.controller;

import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.hateoas.assembler.UserModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The UserController.
 */
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler assembler;

    /**
     * Instantiates a new UserController.
     *
     * @param userService the user service
     * @param userModelAssembler user model assembler
     */
    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.assembler = userModelAssembler;
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     * @throws EntityNotFoundException if entity not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_user:read') || hasAuthority('USER_ID:' + #id)")
    public ResponseEntity<EntityModel<UserDto>> getUser(@PathVariable Long id) throws EntityNotFoundException {
        UserDto userDto = userService.find(id);
        EntityModel<UserDto> model = assembler.assembleModel(userDto);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Gets users.
     *
     * @param page the page
     * @param size the size
     * @return the users
     * @throws EntityNotFoundException   if entity not found
     */
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    public ResponseEntity<EntityModel<Page<UserDto>>> getUsers(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) throws EntityNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> usersPage = userService.findAll(pageable);
        EntityModel<Page<UserDto>> model = assembler.assemblePagedModel(usersPage);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
