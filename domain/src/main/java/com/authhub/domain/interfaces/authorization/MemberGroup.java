package com.authhub.domain.interfaces.authorization;

import com.authhub.domain.implementation.authentication.DefaultMember;
import com.authhub.domain.implementation.authorization.DefaultGroup;

public interface MemberGroup {
    Long getId();
    DefaultMember getMember();
    DefaultGroup getGroup();
}
