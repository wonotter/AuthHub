package com.authhub.domain.interfaces.authentication;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.authhub.domain.implementation.authorization.DefaultMemberGroup;
import com.authhub.domain.implementation.authorization.DefaultMemberRole;

public interface Member extends UserDetails {
    Long getId();
    String getUsername();
    String getPassword();
    Set<DefaultMemberRole> getRoles();
    Set<DefaultMemberGroup> getGroups();
}
