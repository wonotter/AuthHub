package com.authsphere.authhub.exception.commonexception;

import com.authsphere.authhub.exception.domain.ExceptionType;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ExceptionType exceptionType;

    protected BusinessException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    protected BusinessException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }
}

