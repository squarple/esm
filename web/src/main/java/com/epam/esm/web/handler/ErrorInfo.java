package com.epam.esm.web.handler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * The Error info.
 */
public class ErrorInfo {
    private int errorCode;
    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    /**
     * Instantiates a new Error info.
     */
    public ErrorInfo() {

    }

    /**
     * Instantiates a new Error info.
     *
     * @param errorCode    the error code
     * @param errorMessage the error message
     */
    public ErrorInfo(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Sets errors.
     *
     * @param errors the errors
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
