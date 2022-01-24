package com.epam.esm.service;

import com.epam.esm.service.dto.AuthenticationRequestDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface UserService.
 */
public interface UserService {
    /**
     * Find user.
     *
     * @param id the id
     * @return the user
     * @throws EntityNotFoundException if user not found
     */
    UserDto find(Long id) throws EntityNotFoundException;

    /**
     * Find user by username.
     *
     * @param username the username
     * @return the user
     */
    UserDto findByUsername(String username);

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<UserDto> findAll(Pageable pageable);

    /**
     * Login.
     *
     * @param username the username
     * @param password the password
     * @return the access token
     * @throws UserNotFoundException if user not found
     */
    String login(String username, String password) throws UserNotFoundException, BadCredentialsException;

    /**
     * Register user.
     *
     * @param authenticationRequestDto the authentication request
     * @return the access token
     * @throws UserRegistrationException the user registration exception
     */
    String register(AuthenticationRequestDto authenticationRequestDto) throws UserRegistrationException, UserAlreadyExistsException, BadCredentialsException;
}
