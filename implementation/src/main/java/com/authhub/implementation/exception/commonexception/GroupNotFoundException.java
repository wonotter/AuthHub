package com.authhub.implementation.exception.commonexception;

import com.authhub.implementation.exception.domain.ExceptionType;

public class GroupNotFoundException extends SecurityException {
    public GroupNotFoundException(String message) {
        super(ExceptionType.GROUP_NOT_FOUND.getCode(), message);
    }
}
