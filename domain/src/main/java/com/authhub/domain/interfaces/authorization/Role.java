package com.authhub.domain.interfaces.authorization;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public interface Role extends GrantedAuthority {
    /**
     * 역할의 이름을 반환
     * @return 역할 이름 문자열
     */
    String getName();

    /**
     * 역할에 할당된 권한 목록을 반환
     * @return 권한(Permission) 객체의 리스트
     */
    List<Permission> getPermissions();
}
