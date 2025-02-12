package com.authsphere.authhub.exception.commonexception;

import com.authsphere.authhub.exception.domain.ExceptionType;

public class DuplicateException extends BusinessException {
    public DuplicateException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
