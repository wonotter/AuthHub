package com.authhub.implementation.exception.domain;

public enum ExceptionType {
    UNSUPPORTED_MEMBER_TYPE("E001", "Only DefaultMember is supported in this implementation."),
    MEMBER_NOT_FOUND("E002", "Member not found")
    ;
    
    private final String code;
    private final String message;

    ExceptionType(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
