package com.authsphere.authhub.domain.oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class ClaimsUser implements OAuth2UserInfo {
    private final Long id; 
    private final OAuth2Provider provider;
    private final Collection<? extends GrantedAuthority> authorities;

    public ClaimsUser(long id, OAuth2Provider provider, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.provider = provider;
        this.authorities = authorities;
    }

    @Override
    public OAuth2Provider getProvider() {
        return provider;
    }

    @Override
    public String getAccessToken() {
        throw new UnsupportedOperationException("JWT 토큰에서는 Access Token을 직접 제공하지 않습니다.");
    }

    @Override
    public Map<String, Object> getAttributes() {
        throw new UnsupportedOperationException("JWT 토큰에서는 Attributes를 제공하지 않습니다.");
    }

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getEmail() {
        throw new UnsupportedOperationException("JWT 토큰에서는 Email을 제공하지 않습니다.");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("JWT 토큰에서는 Name을 제공하지 않습니다.");
    }

    @Override
    public String getFirstName() {
        throw new UnsupportedOperationException("JWT 토큰에서는 FirstName을 제공하지 않습니다.");
    }

    @Override
    public String getLastName() {
        throw new UnsupportedOperationException("JWT 토큰에서는 LastName을 제공하지 않습니다.");
    }

    @Override
    public String getNickname() {
        throw new UnsupportedOperationException("JWT 토큰에서는 Nickname을 제공하지 않습니다.");
    }

    @Override
    public String getProfileImageUrl() {
        throw new UnsupportedOperationException("JWT 토큰에서는 ProfileImageUrl을 제공하지 않습니다.");
    }
}
