package com.authhub.domain.interfaces.authorization;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

/**
 * 역할(Role) 인터페이스
 * - "ROLE_ADMIN", "ROLE_USER" 등 식별자를 반환
 */
public interface Role extends GrantedAuthority {
    Long getId();
    String getRoleName();
    Set<? extends MemberRole> getUserRoles();
    Set<? extends RolePermission> getRolePermissions();
    Set<? extends GroupRole> getGroupRoles();
}
