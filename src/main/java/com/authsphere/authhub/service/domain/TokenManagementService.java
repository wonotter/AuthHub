package com.authsphere.authhub.service.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authsphere.authhub.domain.JwtRefreshToken;
import com.authsphere.authhub.exception.commonexception.NotFoundException;
import com.authsphere.authhub.repository.JwtRefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.authsphere.authhub.exception.domain.ExceptionType.JWT_REFRESH_TOKEN_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TokenManagementService {
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    
    public JwtRefreshToken getOrElseThrow(Long id) {
        return jwtRefreshTokenRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(JWT_REFRESH_TOKEN_NOT_FOUND));
    }

    @Transactional
    public void saveRefreshToken(long memberId, String refreshToken) {
        JwtRefreshToken jwtRefreshToken = JwtRefreshToken.builder()
            .memberId(memberId)
            .refreshToken(refreshToken)
            .build();

        jwtRefreshTokenRepository.save(jwtRefreshToken);
    }

    @Transactional
    public void deleteRefreshToken(Long memberId) {
        jwtRefreshTokenRepository.deleteById(memberId);
    }
}
