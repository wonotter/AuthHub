package com.authhub.implementation.exception.domain;

public enum ExceptionType {
    UNSUPPORTED_MEMBER_TYPE("E001", "Only DefaultMember is supported in this implementation."),
    MEMBER_NOT_FOUND("E002", "Member not found"),
    UNSUPPORTED_ROLE_TYPE("E003", "Only DefaultRole is supported in this implementation."),
    ROLE_NOT_FOUND("E004", "Role not found"),
    ROLE_ALREADY_ASSIGNED("E005", "Role already assigned to the member"),
    UNSUPPORTED_GROUP_TYPE("E006", "Only DefaultGroup is supported in this implementation."),
    GROUP_NOT_FOUND("E007", "Group not found"),
    GROUP_ALREADY_ASSIGNED("E008", "Group already assigned to the member"),
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
