package com.authsphere.authhub.exception.commonexception;

import com.authsphere.authhub.exception.domain.ExceptionType;

public class NotFoundException extends BusinessException {
    public NotFoundException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
