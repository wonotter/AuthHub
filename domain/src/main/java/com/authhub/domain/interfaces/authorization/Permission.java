package com.authhub.domain.interfaces.authorization;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.authhub.domain.interfaces.authentication.User;

public interface Permission extends GrantedAuthority {
    /**
     * 권한의 이름을 반환
     * @return 권한 이름 문자열
     */
    String getName();

    /**
     * 이 권한이 할당된 사용자 목록을 반환
     * @return 사용자(User) 객체의 리스트
     */
    List<User> getUsers();

    /**
     * 이 권한이 포함된 역할 목록을 반환
     * @return 역할(Role) 객체의 리스트
     */
    List<Role> getRoles();

    /**
     * 권한의 설명을 반환
     * @return 권한 설명 문자열
     */
    String getDescription();
}
