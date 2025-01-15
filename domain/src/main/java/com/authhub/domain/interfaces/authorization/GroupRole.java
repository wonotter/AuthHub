package com.authhub.domain.interfaces.authorization;

import com.authhub.domain.implementation.authorization.DefaultGroup;
import com.authhub.domain.implementation.authorization.DefaultRole;

public interface GroupRole {
    Long getId();
    DefaultGroup getGroup();
    DefaultRole getRole();
}
