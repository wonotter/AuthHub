package com.authsphere.authhub.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.authsphere.authhub.domain.oauth2.OAuth2UserPrincipal;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String ADMIN_HOME_URL = "/admin";
    private static final String USER_HOME_URL = "/home";
    private final HttpSession httpSession;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        
        // 세션에 사용자 정보 저장
        saveAuthenticationToSession(authentication);
        
        // 이전 요청 정보가 있다면 저장
        clearAuthenticationAttributes(request);
        
        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        // 권한에 따른 리다이렉트 URL 결정
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .filter(authority -> authority.startsWith("ROLE_"))
            .findFirst()
            .map(this::getRoleBasedTargetUrl)
            .orElse(USER_HOME_URL);
    }

    private String getRoleBasedTargetUrl(String role) {
        return switch (role) {
            case "ROLE_ADMIN" -> ADMIN_HOME_URL;
            case "ROLE_USER" -> USER_HOME_URL;
            default -> USER_HOME_URL;
        };
    }

    private void saveAuthenticationToSession(Authentication authentication) {
        if (authentication.getPrincipal() instanceof OAuth2UserPrincipal principal) {
            httpSession.setAttribute("user", principal.getUserInfo());
            httpSession.setAttribute("authorities", principal.getAuthorities());
        }
    }
}
