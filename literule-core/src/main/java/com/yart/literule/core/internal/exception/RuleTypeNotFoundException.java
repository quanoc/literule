package com.yart.literule.core.internal.exception;

public class RuleTypeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /** 异常信息 */
    private String message;

    public RuleTypeNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
