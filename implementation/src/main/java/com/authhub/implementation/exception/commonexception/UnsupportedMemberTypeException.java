package com.authhub.implementation.exception.commonexception;

import com.authhub.implementation.exception.domain.ExceptionType;

public class UnsupportedMemberTypeException extends SecurityException {
    public UnsupportedMemberTypeException(String message) {
        super(ExceptionType.UNSUPPORTED_MEMBER_TYPE.getCode(), message);
    }
}
