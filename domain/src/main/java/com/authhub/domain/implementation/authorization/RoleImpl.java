package com.authhub.domain.implementation.authorization;

import java.util.ArrayList;
import java.util.List;

import com.authhub.domain.interfaces.authorization.Permission;
import com.authhub.domain.interfaces.authorization.Role;

public class RoleImpl implements Role {
    private final String name;
    private final List<Permission> permissions;

    public RoleImpl(String name) {
        this.name = name;
        this.permissions = new ArrayList<>();
    }

    public RoleImpl(String name, List<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Permission> getPermissions() {
        return permissions;
    }
    
    /**
     * Spring Security에서 사용하는 권한 문자열을 반환
     * 역할 이름에 "ROLE_" 접두사를 추가하여 반환
     * @return "ROLE_" 접두사가 붙은 역할 이름 문자열
     */
    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }

    /**
     * 역할에 새로운 권한을 추가
     * @param permission 추가할 권한 객체
     */
    public void addPermission(Permission permission){
        this.permissions.add(permission);
    }
}
