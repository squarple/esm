package com.epam.esm.web.handler;

import com.epam.esm.service.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * The type Esm exception handler.
 */
@Profile("dev")
@RestControllerAdvice
public class EsmExceptionHandler extends ResponseEntityExceptionHandler {
    public static final Logger LOGGER = LogManager.getLogger(EsmExceptionHandler.class);

    private static final String MESSAGE_ENTITY_NOT_FOUND = "message.entity.not.found";
    private static final String MESSAGE_INTERNAL_SERVER_ERROR = "message.internal.server.error";
    private static final String MESSAGE_INVALID_ENTITY = "message.invalid.entity";
    private static final String MESSAGE_ILLEGAL_REQUEST_PARAM = "message.bas.request.param";

    private static final int ERROR_CODE_ENTITY_NOT_FOUND = 40401;
    private static final int ERROR_CODE_ARGUMENT_TYPE_MISMATCH = 40001;
    private static final int ERROR_CODE_ILLEGAL_REQUEST_PARAM = 40402;
    private static final int ERROR_CODE_INTERNAL_SERVER_ERROR = 50001;

    private final MessageSource messageSource;

    /**
     * Instantiates a new Esm exception handler.
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
        LOGGER.error(messageSource.getMessage(MESSAGE_INVALID_ENTITY, null, request.getLocale()));
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gift certificate not found response entity.
     *
     * @param e      the e
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> giftCertificateNotFound(EntityNotFoundException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ENTITY_NOT_FOUND, String.format(messageSource.getMessage(MESSAGE_ENTITY_NOT_FOUND, null, locale), e.getEntityId()));
        LOGGER.error(String.format(messageSource.getMessage(MESSAGE_ENTITY_NOT_FOUND, null, locale), e.getEntityId()));
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    /**
     * Illegal request params response entity.
     *
     * @param e      the e
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> illegalRequestParams(IllegalArgumentException e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ILLEGAL_REQUEST_PARAM, messageSource.getMessage(MESSAGE_ILLEGAL_REQUEST_PARAM, null, locale));
        LOGGER.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * Unexpected exception response entity.
     *
     * @param e      the e
     * @param locale the locale
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> unexpectedException(Exception e, Locale locale) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_INTERNAL_SERVER_ERROR, messageSource.getMessage(MESSAGE_INTERNAL_SERVER_ERROR, null, locale));
        LOGGER.error(e);
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
