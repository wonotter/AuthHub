package com.authsphere.authhub.exception.commonexception;

import com.authsphere.authhub.exception.domain.ExceptionType;

public class SuccessResponse extends BusinessException{
    public SuccessResponse(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
