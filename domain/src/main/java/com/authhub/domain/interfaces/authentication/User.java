package com.authhub.domain.interfaces.authentication;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.authhub.domain.interfaces.authorization.Group;
import com.authhub.domain.interfaces.authorization.Permission;
import com.authhub.domain.interfaces.authorization.Role;

public interface User extends UserDetails {
    /**
     * 사용자의 고유 식별자를 반환
     * @return 사용자의 고유 ID 문자열
     */
    String getId();

    /**
     * 사용자의 로그인 아이디를 반환
     * @return 사용자의 로그인 아이디 문자열
     */
    String getUsername();

    /**
     * 사용자의 암호화된 비밀번호를 반환
     * @return 암호화된 비밀번호 문자열
     */
    String getPassword();

    /**
     * 사용자에게 할당된 역할 목록을 반환
     * @return 역할(Role) 객체의 리스트
     */
    List<Role> getRoles();

    /**
     * 사용자에게 할당된 그룹 목록을 반환
     * @return 그룹(Group) 객체의 리스트
     */
    List<Group> getGroups();

    /**
     * 사용자 계정의 활성화 상태를 반환
     * @return true: 활성화됨, false: 비활성화됨
     */
    boolean isEnabled();

    /**
     * 사용자와 관련된 추가 속성들을 반환
     * @return 키-값 쌍으로 구성된 사용자 속성 맵
     */
    Map<String, Object> getAttributes();

    /**
     * 사용자에게 할당된 권한 목록을 반환
     * @return 권한(Permission) 객체의 리스트
     */
    List<Permission> getPermissions();
}
