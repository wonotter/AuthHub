package com.authsphere.authhub.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import com.authsphere.authhub.domain.JwtAuthenticationToken;
import com.authsphere.authhub.domain.oauth2.ClaimsUser;
import com.authsphere.authhub.domain.oauth2.OAuth2Provider;
import com.authsphere.authhub.domain.oauth2.OAuth2UserInfo;
import com.authsphere.authhub.domain.oauth2.OAuth2UserPrincipal;
import com.authsphere.authhub.service.domain.JwtTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements AuthenticationProvider {
    private final JwtTokenService jwtTokenService;

    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            String token = authentication.getPrincipal().toString();
            Claims claims = jwtTokenService.verifyAndGetClaims(token);
            Long id = claims.get("id", Long.class);
            String providerStr = claims.get("provider", String.class);
            OAuth2Provider provider = (providerStr != null) ? OAuth2Provider.valueOf(providerStr.toUpperCase()) : OAuth2Provider.DEFAULT;
            List<String> authorityStrings = (List<String>) claims.get("authorities", List.class);
            if(authorityStrings == null) authorityStrings = Collections.emptyList();
            Collection<? extends GrantedAuthority> authorities = authorityStrings.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            
            OAuth2UserInfo userInfo = new ClaimsUser(id, provider, authorities);
            OAuth2UserPrincipal principal = new OAuth2UserPrincipal(userInfo, authorities);
            return new OAuth2AuthenticationToken(principal, principal.getAuthorities(), provider.getRegistrationId());
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid JWT token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
