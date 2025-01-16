package com.authhub.domain.interfaces.authorization;

public interface RolePermission {
    Long getId();
    Role getRole();
    Permission getPermission();
}
