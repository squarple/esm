package com.epam.esm.web.controller;

import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import com.epam.esm.web.hateoas.assembler.UserModelAssembler;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestWebAppContextConfig.class)
class UserControllerTests {
    @Mock
    private UserService userService;

    @Mock
    private UserModelAssembler userModelAssembler;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService, userModelAssembler);
    }

    @Test
    void getUser_ReturnUser() throws EntityNotFoundException {
        UserDto user = TestUtil.getUserList(1).get(0);
        when(userService.find(user.getId())).thenReturn(user);
        when(userModelAssembler.assembleModel(user)).thenReturn(EntityModel.of(user));
        ResponseEntity<EntityModel<UserDto>> expected = new ResponseEntity<>(EntityModel.of(user), HttpStatus.OK);
        ResponseEntity<EntityModel<UserDto>> actual = userController.getUser(user.getId());
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getUsers_ReturnListOfUsers() throws EntityNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<UserDto> users = TestUtil.getUserList(3);
        Page<UserDto> page = new PageImpl<>(users, pageable, 3);
        when(userService.findAll(pageable)).thenReturn(page);
        when(userModelAssembler.assemblePagedModel(page)).thenReturn(EntityModel.of(page));
        ResponseEntity<EntityModel<Page<UserDto>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<UserDto>>> actual = userController.getUsers(0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }
}
