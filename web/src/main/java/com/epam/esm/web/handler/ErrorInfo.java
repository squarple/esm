package com.epam.esm.web.handler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ErrorInfo {
    private int errorCode;
    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ErrorInfo() {

    }

    public ErrorInfo(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
