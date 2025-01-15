package com.authhub.domain.interfaces.authorization;

import com.authhub.domain.implementation.authorization.DefaultPermission;
import com.authhub.domain.implementation.authorization.DefaultRole;

public interface RolePermission {
    Long getId();
    DefaultRole getRole();
    DefaultPermission getPermission();
}
