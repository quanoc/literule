package com.yart.literule.core.internal.exception;

public class RuleStorageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public RuleStorageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
