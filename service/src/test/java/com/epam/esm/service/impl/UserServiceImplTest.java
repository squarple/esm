package com.epam.esm.service.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.impl.UserDaoImpl;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Profile("test")
@SpringBootTest(classes = TestServiceConfig.class)
class UserServiceImplTest {
    @Mock
    private UserDaoImpl userDao;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    void save_ReturnCreatedUser() throws EntityAlreadyExistsDaoException, EntityAlreadyExistsException {
        User expectedUser = TestUtil.getUserList(1).get(0);
        when(userDao.create(expectedUser)).thenReturn(expectedUser);
        User actualUser = userService.save(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void get_ExistedUserId_ReturnFoundedUser() throws EntityNotFoundDaoException, EntityNotFoundException {
        User expectedUser = TestUtil.getUserList(1).get(0);
        when(userDao.find(expectedUser.getId())).thenReturn(expectedUser);
        User actualUser = userService.get(expectedUser.getId());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getAll_ReturnListOfTags() throws ResourceNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<User> userList = TestUtil.getUserList(3);
        Page<User> expectedPage = new PageImpl<>(userList, pageable, userList.size());
        when(userDao.findAll(pageable)).thenReturn(expectedPage);
        Page<User> actualPage = userService.getAll(pageable);
        assertEquals(expectedPage, actualPage);
    }
}
