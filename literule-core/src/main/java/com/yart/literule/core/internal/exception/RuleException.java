package com.yart.literule.core.internal.exception;

public class RuleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public RuleException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
