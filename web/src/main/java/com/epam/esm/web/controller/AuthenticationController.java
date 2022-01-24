package com.epam.esm.web.controller;

import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.AuthenticationRequestDto;
import com.epam.esm.service.exception.BadCredentialsException;
import com.epam.esm.service.exception.UserAlreadyExistsException;
import com.epam.esm.service.exception.UserNotFoundException;
import com.epam.esm.service.exception.UserRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Authentication controller.
 */
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {
    private final UserService userService;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param userService the user service
     */
    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Login.
     *
     * @param authenticationRequestDto the authentication request
     * @return the response entity
     * @throws UserNotFoundException if user not found
     */
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequestDto authenticationRequestDto)
            throws UserNotFoundException, BadCredentialsException {
        String token = userService.login(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Register.
     *
     * @param authenticationRequest the authentication request
     * @return the response entity
     * @throws UserRegistrationException  unexpected exception during user registration
     * @throws UserAlreadyExistsException if user already exists
     */
    @PreAuthorize("permitAll()")
    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Validated AuthenticationRequestDto authenticationRequest)
            throws UserRegistrationException, UserAlreadyExistsException, BadCredentialsException {
        String token = userService.register(authenticationRequest);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
