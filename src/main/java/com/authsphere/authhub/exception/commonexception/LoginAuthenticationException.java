package com.authsphere.authhub.exception.commonexception;

import org.springframework.security.core.AuthenticationException;

import com.authsphere.authhub.exception.domain.ExceptionType;

public class LoginAuthenticationException extends AuthenticationException {
    public LoginAuthenticationException() {
        super(ExceptionType.LOGIN_AUTHENTICATION_EXCEPTION.getMessage());
    }
}

