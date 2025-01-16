package com.authhub.implementation.exception.commonexception;

import com.authhub.implementation.exception.domain.ExceptionType;

public class RoleNotFoundException extends SecurityException {
    public RoleNotFoundException(String message) {
        super(ExceptionType.ROLE_NOT_FOUND.getCode(), message);
    }
}
