package com.authsphere.authhub.service.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authsphere.authhub.domain.Member;
import com.authsphere.authhub.domain.oauth2.OAuth2Provider;
import com.authsphere.authhub.dto.LoginRequest;
import com.authsphere.authhub.dto.SignUpRequest;
import com.authsphere.authhub.service.domain.JwtTokenService;
import com.authsphere.authhub.service.domain.MemberService;
import com.authsphere.authhub.service.domain.TokenManagementService;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.token.Tokens;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberService memberService;
    private final JwtTokenService jwtTokenService;
    private final TokenManagementService tokenManagementService;

    @Transactional
    public Long signUp(final SignUpRequest signUpRequest) {
        Member member = Member.builder()
            .email(signUpRequest.email())
            .password(signUpRequest.password())
            .build();
        Member savedMember = memberService.registerMember(member);
        return savedMember.getId();
    }

    @Transactional
    public Tokens login(final LoginRequest loginRequest) {
        Member member = memberService.findMemberByEmail(loginRequest.email());
        memberService.verifyPassword(member, loginRequest.password());
        Tokens tokens = jwtTokenService.createTokenResponse(member, OAuth2Provider.DEFAULT);
        RefreshToken refreshTokenObject = tokens.getRefreshToken();
        String refreshTokenValue = refreshTokenObject.getValue();
        tokenManagementService.saveRefreshToken(member.getId(), refreshTokenValue);
        return tokens;
    }
}
