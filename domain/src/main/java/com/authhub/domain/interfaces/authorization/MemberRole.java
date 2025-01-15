package com.authhub.domain.interfaces.authorization;

import com.authhub.domain.implementation.authentication.DefaultMember;
import com.authhub.domain.implementation.authorization.DefaultRole;

public interface MemberRole {
    Long getId();
    DefaultMember getMember();
    DefaultRole getRole();
}
