package com.epam.esm.web.handler;

import com.epam.esm.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * The exception handler.
 */
@Profile("dev")
@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE_ENTITY_NOT_FOUND = "message.entity.not.found";
    private static final String MESSAGE_INTERNAL_SERVER_ERROR = "message.internal.server.error";
    private static final String MESSAGE_INVALID_ENTITY = "message.invalid.entity";
    private static final String MESSAGE_ILLEGAL_REQUEST_PARAM = "message.bad.request.param";
    private static final String MESSAGE_ENTITY_EXISTS = "message.entity.exists";
    private static final String MESSAGE_FORBIDDEN_ACTION = "message.forbidden.action";
    private static final String MESSAGE_ACCESS_DENIED = "message.access.denied";
    private static final String MESSAGE_USER_ALREADY_EXISTS = "message.user.exists";
    private static final String MESSAGE_USER_NOT_FOUND = "message.user.not.found";
    private static final String MESSAGE_REGISTRATION_EXCEPTION = "message.registration.exception";
    private static final String MESSAGE_UNAUTHORIZED = "message.unauthorized";

    private static final int ERROR_CODE_ENTITY_NOT_FOUND = 40401;
    private static final int ERROR_CODE_ARGUMENT_TYPE_MISMATCH = 40001;
    private static final int ERROR_CODE_ILLEGAL_REQUEST_PARAM = 40402;
    private static final int ERROR_CODE_INTERNAL_SERVER_ERROR = 50001;
    private static final int ERROR_CODE_FORBIDDEN_ACTION = 40404;
    private static final int ERROR_CODE_ACCESS_DENIED = 40301;
    private static final int ERROR_CODE_CONFLICT = 40901;
    private static final int ERROR_CODE_REGISTRATION_EXCEPTION = 50002;
    private static final int ERROR_CODE_UNAUTHORIZED = 40101;

    private final MessageSource messageSource;

    /**
     * Instantiates a new EsmExceptionHandler.
     *
     * @param messageSource the message source
     */
    @Autowired
    public AppExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ARGUMENT_TYPE_MISMATCH, messageSource.getMessage(MESSAGE_INVALID_ENTITY, null, request.getLocale()));
        List<String> errorList = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        errorInfo.setErrors(errorList);
        logger.error(messageSource.getMessage(MESSAGE_INVALID_ENTITY, null, request.getLocale()));
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * Invoked when BadCredentialsException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorInfo> badCredentials(BadCredentialsException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_UNAUTHORIZED, messageSource.getMessage(MESSAGE_UNAUTHORIZED, null, locale));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Invoked when EntityAlreadyExistsException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorInfo> entityAlreadyExistsException(EntityAlreadyExistsException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ILLEGAL_REQUEST_PARAM, messageSource.getMessage(MESSAGE_ENTITY_EXISTS, null, locale));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * Invoked when EntityNotFoundException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> giftCertificateNotFound(EntityNotFoundException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ENTITY_NOT_FOUND, String.format(messageSource.getMessage(MESSAGE_ENTITY_NOT_FOUND, null, locale), e.getEntityId()));
        logger.error(String.format(messageSource.getMessage(MESSAGE_ENTITY_NOT_FOUND, null, locale), e.getEntityId()));
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    /**
     * Invoked when ForbiddenActionException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<ErrorInfo> forbiddenActionException(ForbiddenActionException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_FORBIDDEN_ACTION, String.format(messageSource.getMessage(MESSAGE_FORBIDDEN_ACTION, null, locale), e.getId()));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * Invoked when UserAlreadyExistsException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorInfo> userAlreadyExists(UserAlreadyExistsException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_CONFLICT, String.format(messageSource.getMessage(MESSAGE_USER_ALREADY_EXISTS, null, locale), e.getUsername()));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }

    /**
     * Invoked when UserNotFoundException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorInfo> userNotFound(UserNotFoundException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ENTITY_NOT_FOUND, String.format(messageSource.getMessage(MESSAGE_USER_NOT_FOUND, null, locale), e.getUsername()));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    /**
     * Invoked when UserRegistrationException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ErrorInfo> registrationException(UserRegistrationException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_REGISTRATION_EXCEPTION, messageSource.getMessage(MESSAGE_REGISTRATION_EXCEPTION, null, locale));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Invoked when IllegalArgumentException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> illegalRequestParams(IllegalArgumentException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ILLEGAL_REQUEST_PARAM, messageSource.getMessage(MESSAGE_ILLEGAL_REQUEST_PARAM, null, locale));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * Invoked when AccessDeniedException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorInfo> accessDenied(AccessDeniedException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ACCESS_DENIED, messageSource.getMessage(MESSAGE_ACCESS_DENIED, null, locale));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.FORBIDDEN);
    }

    /**
     * Invoked when Exception thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> unexpectedException(Exception e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_INTERNAL_SERVER_ERROR, messageSource.getMessage(MESSAGE_INTERNAL_SERVER_ERROR, null, locale));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
