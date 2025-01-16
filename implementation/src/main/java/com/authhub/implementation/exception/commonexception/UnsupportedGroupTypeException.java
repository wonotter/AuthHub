package com.authhub.implementation.exception.commonexception;

import com.authhub.implementation.exception.domain.ExceptionType;

public class UnsupportedGroupTypeException extends SecurityException {
    public UnsupportedGroupTypeException(String message) {
        super(ExceptionType.UNSUPPORTED_GROUP_TYPE.getCode(), message);
    }
}
