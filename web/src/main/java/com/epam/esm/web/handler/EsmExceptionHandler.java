package com.epam.esm.web.handler;

import com.epam.esm.service.exception.EntityNotFoundException;
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
import java.util.stream.Collectors;

@RestControllerAdvice
public class EsmExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE_ENTITY_NOT_FOUND = "Requested resource not found (id = %d)";
    private static final String MESSAGE_INTERNAL_SERVER_ERROR = "Internal server error";
    private static final String MESSAGE_INVALID_ENTITY = "Invalid entity";
    private static final String MESSAGE_ILLEGAL_REQUEST_PARAM = "Bad request params";

    private static final int ERROR_CODE_ENTITY_NOT_FOUND = 40401;
    private static final int ERROR_CODE_ILLEGAL_REQUEST_PARAM = 40402;
    private static final int ERROR_CODE_ARGUMENT_TYPE_MISMATCH = 40001;
    private static final int ERROR_CODE_INTERNAL_SERVER_ERROR = 50001;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> giftCertificateNotFound(EntityNotFoundException e) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ENTITY_NOT_FOUND, String.format(MESSAGE_ENTITY_NOT_FOUND, e.getEntityId()));
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> unexpectedException(Exception e) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_INTERNAL_SERVER_ERROR, MESSAGE_INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> illegalRequestParams(IllegalArgumentException e) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ILLEGAL_REQUEST_PARAM, MESSAGE_ILLEGAL_REQUEST_PARAM);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(ERROR_CODE_ARGUMENT_TYPE_MISMATCH, MESSAGE_INVALID_ENTITY);
        List<String> errorList = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        errorInfo.setErrors(errorList);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
