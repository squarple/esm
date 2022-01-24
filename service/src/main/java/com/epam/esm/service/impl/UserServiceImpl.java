package com.epam.esm.service.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.AuthenticationRequestDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.*;
import com.epam.esm.service.util.KeycloakUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final KeycloakUtil keycloakUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, KeycloakUtil keycloakUtil) {
        this.userRepository = userRepository;
        this.keycloakUtil = keycloakUtil;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserDto::fromUser);
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserDto.fromUser(user);
    }

    @Override
    public UserDto find(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return UserDto.fromUser(user);
    }

    @Override
    public String login(String username, String password) throws UserNotFoundException, BadCredentialsException {
        if (!isUserExists(username)) {
            throw new UserNotFoundException(username);
        }
        return keycloakUtil.getAccessToken(username, password);
    }

    @Override
    public String register(AuthenticationRequestDto authenticationRequestDto) throws UserRegistrationException, UserAlreadyExistsException, BadCredentialsException {
        User user;
        if (isUserExists(authenticationRequestDto.getUsername())) {
            if (keycloakUtil.isUserExists(authenticationRequestDto.getUsername())) {
                throw new UserAlreadyExistsException(authenticationRequestDto.getUsername());
            } else {
                user = userRepository.findByUsername(authenticationRequestDto.getUsername());
                if (!keycloakUtil.addUserToKeycloak(user, authenticationRequestDto.getPassword())) {
                    throw new UserRegistrationException();
                }
                return keycloakUtil.getAccessToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword());
            }
        }
        user = new User();
        user.setUsername(authenticationRequestDto.getUsername());
        user.setEmail(authenticationRequestDto.getEmail());
        user = userRepository.save(user);
        if (!keycloakUtil.addUserToKeycloak(user, authenticationRequestDto.getPassword())) {
            throw new UserRegistrationException();
        }
        return keycloakUtil.getAccessToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword());
    }

    private boolean isUserExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
