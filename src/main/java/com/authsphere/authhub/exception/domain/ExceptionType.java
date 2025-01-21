package com.authsphere.authhub.exception.domain;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExceptionType {
    UNSUPPORTED_MEMBER_TYPE(BAD_REQUEST, "E001", "Only DefaultMember is supported in this implementation."),
    MEMBER_NOT_FOUND(NOT_FOUND, "E002", "Member not found"),
    UNSUPPORTED_ROLE_TYPE(BAD_REQUEST, "E003", "Only DefaultRole is supported in this implementation."),
    ROLE_NOT_FOUND(NOT_FOUND, "E004", "Role not found"),
    ROLE_ALREADY_ASSIGNED(BAD_REQUEST, "E005", "Role already assigned to the member"),
    UNSUPPORTED_GROUP_TYPE(BAD_REQUEST, "E006", "Only DefaultGroup is supported in this implementation."),
    GROUP_NOT_FOUND(NOT_FOUND, "E007", "Group not found"),
    GROUP_ALREADY_ASSIGNED(BAD_REQUEST, "E008", "Group already assigned to the member"),
    LOGIN_AUTHENTICATION_EXCEPTION(BAD_REQUEST, "E009", "Login authentication exception"),
    AUTHENTICATION_ENTRY_POINT_EXCEPTION(UNAUTHORIZED, "E010", "Authentication entry point exception"),
    ACCESS_DENIED_EXCEPTION(FORBIDDEN, "E011", "Access denied exception"),
    TOKEN_EXPIRED_EXCEPTION(UNAUTHORIZED, "E012", "Token expired exception"),
    JWT_REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "E013", "Jwt refresh token not found"),
    LOGOUT_SUCCESS(OK, "E014", "Logout success");
    
    private final HttpStatus status;
    private final String code;
    private final String message;
}
