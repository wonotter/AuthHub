package com.authsphere.authhub.service;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.authsphere.authhub.exception.commonexception.LoginAuthenticationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final String OAUTH2_FAILURE_URL = "/oauth2/login?error=oauth2-failure";
    private static final String FORM_LOGIN_FAILURE_URL = "/login?error=login-failure";
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // OAuth2 인증 실패와 일반 로그인 실패 구분
        if (exception instanceof OAuth2AuthenticationException) {
            setDefaultFailureUrl(OAUTH2_FAILURE_URL);
        } else if(exception instanceof LoginAuthenticationException){
            setDefaultFailureUrl(FORM_LOGIN_FAILURE_URL);
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
