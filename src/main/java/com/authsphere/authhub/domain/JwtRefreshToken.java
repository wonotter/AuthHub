package com.authsphere.authhub.domain;

import org.springframework.data.domain.Persistable;

import com.authsphere.authhub.common.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Table(name = "jwt_refresh_token")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtRefreshToken extends BaseTimeEntity implements Persistable<Long> {
    @Id
    @Column(name = "jwt_refresh_token_id")
    private Long memberId;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;


    @Builder
    public JwtRefreshToken(@NonNull Long memberId, @NonNull String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }

    @Override
    public Long getId() {
        return memberId;
    }
}
