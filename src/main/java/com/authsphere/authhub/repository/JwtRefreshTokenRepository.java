package com.authsphere.authhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authsphere.authhub.domain.JwtRefreshToken;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, Long> {
    
}
