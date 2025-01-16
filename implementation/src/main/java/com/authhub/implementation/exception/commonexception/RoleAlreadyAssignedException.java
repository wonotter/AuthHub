package com.authhub.implementation.exception.commonexception;

import com.authhub.implementation.exception.domain.ExceptionType;

public class RoleAlreadyAssignedException extends SecurityException {
    public RoleAlreadyAssignedException(String message) {
        super(ExceptionType.ROLE_ALREADY_ASSIGNED.getCode(), message);
    }
}
