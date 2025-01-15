package com.authhub.domain.interfaces.authentication;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.authhub.domain.interfaces.authorization.MemberGroup;
import com.authhub.domain.interfaces.authorization.MemberRole;

public interface Member extends UserDetails {
    Long getId();
    String getUsername();
    String getPassword();
    Set<? extends MemberRole> getRoles();
    Set<? extends MemberGroup> getGroups();
}
