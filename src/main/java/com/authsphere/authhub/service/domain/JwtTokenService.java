package com.authsphere.authhub.service.domain;

import com.authsphere.authhub.domain.Member;
import com.authsphere.authhub.domain.oauth2.OAuth2Provider;
import com.authsphere.authhub.service.TokenProviderTemplate;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.token.Tokens;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenService {
    private static final String ID = "id";
    private static final String PROVIDER = "provider";
    private static final String AUTHORITIES = "authorities";
    private final TokenProviderTemplate tokenProviderTemplate;

    public TokenResponse createTokenResponse(Member member, OAuth2Provider provider) {
        String accessToken = tokenProviderTemplate.createAccessToken(
            jwtBuilder -> jwtBuilder
                .claims()
                .add(ID, member.getId())
                .add(PROVIDER, provider.getRegistrationId())
                .add(AUTHORITIES, member.getAuthorityStrings())
                .and()
        );

        String refreshToken = tokenProviderTemplate.createRefreshToken(
            jwtBuilder -> jwtBuilder
                .claims()
                .add(ID, member.getId())
                .add(PROVIDER, provider.getRegistrationId())
                .add(AUTHORITIES, member.getAuthorityStrings())
                .and()
        );
        
        Tokens tokens = new Tokens(
            new BearerAccessToken(accessToken),
            new RefreshToken(refreshToken)
        );

        return new AccessTokenResponse(tokens);
    }

    public Claims verifyAndGetClaims(String token) {
        return tokenProviderTemplate.verifyAndGetClaims(token);
    }
}
