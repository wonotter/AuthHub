package com.authhub.domain.interfaces.authorization;

import com.authhub.domain.interfaces.authentication.Member;

public interface MemberRole {
    Long getId();
    Member getMember();
    Role getRole();
}
