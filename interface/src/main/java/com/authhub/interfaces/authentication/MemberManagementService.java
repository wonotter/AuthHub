package com.authhub.interfaces.authentication;

import java.util.Optional;

import com.authhub.domain.interfaces.authentication.Member;
import com.authhub.domain.interfaces.authorization.Group;
import com.authhub.domain.interfaces.authorization.Role;

public interface MemberManagementService {
    Member createMember(Member member);
    Member updateMember(Member member);
    void deleteMember(String username);
    Optional<Member> findMemberByUsername(String username);

    Role createRole(Role role);
    void assignRoleToMember(String username, String roleName);

    Group createGroup(Group group);
    void addMemberToGroup(String username, String groupName);
    void addRoleToGroup(String roleName, String groupName);
}
