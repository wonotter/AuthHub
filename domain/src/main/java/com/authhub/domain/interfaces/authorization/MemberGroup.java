package com.authhub.domain.interfaces.authorization;

import com.authhub.domain.interfaces.authentication.Member;

public interface MemberGroup {
    Long getId();
    Member getMember();
    Group getGroup();
}
