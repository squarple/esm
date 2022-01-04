package com.epam.esm.web.controller;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.service.UserService;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestWebAppContextConfig.class)
class UserControllerTests {
    @Mock
    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void createUser_ReturnCreatedUser() throws EntityAlreadyExistsException, EntityNotFoundException {
        User user = TestUtil.getUserList(1).get(0);
        when(userService.save(user)).thenReturn(user);
        ResponseEntity<EntityModel<User>> expected = new ResponseEntity<>(EntityModel.of(user), HttpStatus.CREATED);
        ResponseEntity<EntityModel<User>> actual = userController.createUser(user);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getUser_ReturnUser() throws EntityNotFoundException {
        User user = TestUtil.getUserList(1).get(0);
        when(userService.get(user.getId())).thenReturn(user);
        ResponseEntity<EntityModel<User>> expected = new ResponseEntity<>(EntityModel.of(user), HttpStatus.OK);
        ResponseEntity<EntityModel<User>> actual = userController.getUser(user.getId());
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getUsers_ReturnListOfUsers() throws ResourceNotFoundException, EntityNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<User> users = TestUtil.getUserList(3);
        Page<User> page = new PageImpl<>(users, pageable, 3);
        when(userService.getAll(pageable)).thenReturn(page);
        ResponseEntity<EntityModel<Page<User>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<User>>> actual = userController.getUsers(0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }
}
