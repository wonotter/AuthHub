package com.authsphere.authhub.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authsphere.authhub.domain.JwtAuthenticationToken;
import com.authsphere.authhub.exception.commonexception.ErrorResponse;
import com.authsphere.authhub.exception.commonexception.ExpiredTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static com.authsphere.authhub.exception.domain.ExceptionType.TOKEN_EXPIRED_EXCEPTION;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            getToken(request)
                .<Authentication>map(token -> authenticationManager.authenticate(new JwtAuthenticationToken(token)))
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        } catch (AuthenticationException e) {
            if(e instanceof ExpiredTokenException) {
                response.setStatus(TOKEN_EXPIRED_EXCEPTION.getStatus().value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                objectMapper.writeValue(response.getWriter(), new ErrorResponse(TOKEN_EXPIRED_EXCEPTION));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
            .filter(header -> header.startsWith("Bearer "))
            .map(header -> header.substring(7));
    }
}
