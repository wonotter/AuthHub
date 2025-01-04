package com.authhub.domain.interfaces.authorization;

import java.util.List;

import com.authhub.domain.interfaces.authentication.User;

public interface Group {
    /**
     * 그룹의 이름을 반환
     * @return 그룹 이름 문자열
     */
    String getName();

    /**
     * 그룹에 속한 사용자 목록을 반환
     * @return 사용자(User) 객체의 리스트
     */
    List<User> getUsers();

    /**
     * 그룹에 할당된 역할 목록을 반환
     * @return 역할(Role) 객체의 리스트
     */
    List<Role> getRoles();
}
