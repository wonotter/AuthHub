package com.authsphere.authhub.exception.commonexception;

import com.authsphere.authhub.exception.domain.ExceptionType;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
