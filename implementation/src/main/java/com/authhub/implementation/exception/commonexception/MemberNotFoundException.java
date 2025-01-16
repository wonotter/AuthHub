package com.authhub.implementation.exception.commonexception;

import com.authhub.implementation.exception.domain.ExceptionType;

public class MemberNotFoundException extends SecurityException{
    public MemberNotFoundException(String message) {
        super(ExceptionType.MEMBER_NOT_FOUND.getCode(), message);
    }
}
