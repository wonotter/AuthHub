package com.authhub.implementation.exception.commonexception;

import com.authhub.implementation.exception.domain.ExceptionType;

public class GroupAlreadyAssignedException extends SecurityException {
    public GroupAlreadyAssignedException(String message) {
        super(ExceptionType.GROUP_ALREADY_ASSIGNED.getCode(), message);
    }
}
