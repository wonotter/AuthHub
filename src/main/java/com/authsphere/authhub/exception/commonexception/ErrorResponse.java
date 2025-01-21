package com.authsphere.authhub.exception.commonexception;

import com.authsphere.authhub.exception.domain.ExceptionType;

public class ErrorResponse extends BusinessException {
    public ErrorResponse(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
