package com.authhub.domain.interfaces.authorization;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public interface Permission extends GrantedAuthority {
    long getId();
    Set<? extends RolePermission> getRolePermissions();
}
