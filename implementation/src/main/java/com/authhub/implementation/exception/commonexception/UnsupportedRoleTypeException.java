package com.authhub.implementation.exception.commonexception;

import com.authhub.implementation.exception.domain.ExceptionType;

public class UnsupportedRoleTypeException extends SecurityException {
    public UnsupportedRoleTypeException(String message) {
        super(ExceptionType.UNSUPPORTED_ROLE_TYPE.getCode(), message);
    }
}
