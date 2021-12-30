package com.epam.esm.web.handler;

import com.epam.esm.persistence.exception.ForbiddenActionException;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class EsmExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE_ENTITY_NOT_FOUND = "message.entity.not.found";
    private static final String MESSAGE_INTERNAL_SERVER_ERROR = "message.internal.server.error";
    private static final String MESSAGE_INVALID_ENTITY = "message.invalid.entity";
    private static final String MESSAGE_ILLEGAL_REQUEST_PARAM = "message.bad.request.param";
    private static final String MESSAGE_ENTITY_EXISTS = "message.entity.exists";
    private static final String MESSAGE_RESOURCE_NOT_FOUND = "message.resource.not.found";
    private static final String MESSAGE_FORBIDDEN_ACTION = "message.forbidden.action";

    private static final int ERROR_CODE_ENTITY_NOT_FOUND = 40401;
    private static final int ERROR_CODE_ARGUMENT_TYPE_MISMATCH = 40001;
    private static final int ERROR_CODE_ILLEGAL_REQUEST_PARAM = 40402;
    private static final int ERROR_CODE_INTERNAL_SERVER_ERROR = 50001;
    private static final int ERROR_CODE_RESOURCE_NOT_FOUND = 40403;
    private static final int ERROR_CODE_FORBIDDEN_ACTION = 40404;

    private final MessageSource messageSource;

    /**
     * Instantiates a new EsmExceptionHandler.
     *
     * @param messageSource the message source
     */
    @Autowired
    public EsmExceptionHandler(MessageSource messageSource) {
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
     * Invoked when ResourceNotFoundException thrown.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> resourceNotFoundException(ResourceNotFoundException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_RESOURCE_NOT_FOUND, messageSource.getMessage(MESSAGE_RESOURCE_NOT_FOUND, null, locale));
        logger.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
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
