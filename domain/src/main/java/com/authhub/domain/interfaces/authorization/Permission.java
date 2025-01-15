package com.authhub.domain.interfaces.authorization;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.authhub.domain.implementation.authorization.DefaultRolePermission;

public interface Permission extends GrantedAuthority {
    long getId();
    Set<DefaultRolePermission> getRolePermissions();
}
