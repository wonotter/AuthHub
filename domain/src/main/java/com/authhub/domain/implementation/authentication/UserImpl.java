package com.authhub.domain.implementation.authentication;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.authhub.domain.interfaces.authentication.User;
import com.authhub.domain.interfaces.authorization.Group;
import com.authhub.domain.interfaces.authorization.Permission;
import com.authhub.domain.interfaces.authorization.Role;

public class UserImpl implements User {
    private final String id;
    private final String username;
    private final String password;
    private final List<Role> roles;
    private final List<Group> groups;
    private final boolean enabled;
    private final Map<String, Object> attributes;

    public UserImpl(String id, String username, String password, List<Role> roles, List<Group> groups, boolean enabled, Map<String, Object> attributes) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.groups = groups;
        this.enabled = enabled;
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * default true
     * 사용자 계정이 만료되지 않았는지 확인
     * @return true: 만료되지 않음, false: 만료됨
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * default true
     * 사용자 계정이 잠겨있지 않은지 확인
     * @return true: 잠겨있지 않음, false: 잠겨있음
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * default true
     * 사용자 계정의 비밀번호가 만료되지 않았는지 확인
     * @return true: 만료되지 않음, false: 만료됨
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자에게 할당된 권한 목록을 반환
     * 이 메서드는 Spring Security에서 사용자의 권한을 확인하기 위해 호출
     * 
     * 권한은 다음과 같이 변환
     * - 역할(Role)은 "ROLE_" 접두사가 추가되어 변환됩니다 (예: ROLE_ADMIN)
     * - 권한(Permission)은 그대로 변환됩니다
     * 
     * @return 사용자의 모든 권한을 포함하는 GrantedAuthority 컬렉션
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role과 Permission을 GrantedAuthority로 변환
        List<GrantedAuthority> authorities = roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());
        
        authorities.addAll(roles.stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
            .collect(Collectors.toList()));

        return authorities;
    }

    /**
     * 사용자에게 할당된 모든 권한 목록을 반환
     * 사용자가 가진 모든 역할(Role)에 포함된 권한들을 수집하여 중복 제거 후 반환
     * 
     * @return 사용자의 모든 권한을 포함하는 Permission 객체 리스트
     */
    @Override
    public List<Permission> getPermissions() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
