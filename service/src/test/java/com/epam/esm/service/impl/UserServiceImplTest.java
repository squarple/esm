package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.util.KeycloakUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Profile("test")
@SpringBootTest(classes = TestServiceConfig.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private KeycloakUtil keycloakUtil;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, keycloakUtil);
    }

    @Test
    void find_ExistedUserId_ReturnFoundedUser() throws EntityNotFoundException {
        UserDto expectedUserDto = TestUtil.getUserDtoList(1).get(0);
        when(userRepository.findById(expectedUserDto.getId())).thenReturn(Optional.of(expectedUserDto.toUser()));
        UserDto actualUserDto = userService.find(expectedUserDto.getId());
        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void findAll_ReturnListOfTags() {
        Pageable pageable = TestUtil.getPageable();
        List<UserDto> userList = TestUtil.getUserDtoList(3);
        Page<UserDto> expectedPage = new PageImpl<>(userList, pageable, userList.size());
        when(userRepository.findAll(pageable)).thenReturn(expectedPage.map(UserDto::toUser));
        Page<UserDto> actualPage = userService.findAll(pageable);
        assertEquals(expectedPage, actualPage);
    }
}
