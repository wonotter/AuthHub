package com.authhub.implementation.authentication;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authhub.domain.implementation.authentication.DefaultMember;
import com.authhub.domain.implementation.authorization.DefaultMemberGroup;
import com.authhub.domain.implementation.authorization.DefaultMemberRole;
import com.authhub.domain.interfaces.authentication.Member;
import com.authhub.domain.interfaces.authorization.Group;
import com.authhub.domain.interfaces.authorization.Role;
import com.authhub.domain.repository.DefaultGroupRepository;
import com.authhub.domain.repository.DefaultMemberRepository;
import com.authhub.domain.repository.DefaultRoleRepository;
import com.authhub.implementation.exception.commonexception.MemberNotFoundException;
import com.authhub.implementation.exception.commonexception.UnsupportedMemberTypeException;
import com.authhub.implementation.exception.domain.ExceptionType;
import com.authhub.interfaces.authentication.MemberManagementService;

@Service
@Transactional(readOnly = true)
public class DefaultMemberManagementService implements MemberManagementService {
    private final DefaultMemberRepository memberRepository;
    private final DefaultRoleRepository roleRepository;
    private final DefaultGroupRepository groupRepository;

    public DefaultMemberManagementService(DefaultMemberRepository memberRepository, DefaultRoleRepository roleRepository, DefaultGroupRepository groupRepository) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public Member createMember(Member member) {
        if (!(member instanceof DefaultMember)) {
            throw new UnsupportedMemberTypeException(ExceptionType.UNSUPPORTED_MEMBER_TYPE.getMessage());
        }
        DefaultMember defaultMember = (DefaultMember) member;
        DefaultMember savedMember = memberRepository.save(defaultMember);
        return savedMember;
    }

    @Override
    @Transactional
    public Member updateMember(Member member) {
        if (!(member instanceof DefaultMember)) {
            throw new UnsupportedMemberTypeException(ExceptionType.UNSUPPORTED_MEMBER_TYPE.getMessage());
        }

        DefaultMember defaultMember = (DefaultMember) member;
        // DB에 이미 있는지 확인
        DefaultMember existingMember = memberRepository.findByUsername(defaultMember.getUsername())
                .orElseThrow(() -> new MemberNotFoundException(ExceptionType.MEMBER_NOT_FOUND.getMessage()));
        
        if (defaultMember.getPassword() != null && !defaultMember.getPassword().isEmpty()) {
            existingMember.setPassword(defaultMember.getPassword());
        }

        if (defaultMember.getRoles() != null) {
            existingMember.getRoles().clear();
            defaultMember.getRoles().forEach(role -> {
                if (role instanceof DefaultMemberRole) {
                    existingMember.addRole((DefaultMemberRole) role);
                }
            });
        }

        if (defaultMember.getGroups() != null) {
            existingMember.getGroups().clear();
            defaultMember.getGroups().forEach(group -> {
                if (group instanceof DefaultMemberGroup) {
                    existingMember.addGroup((DefaultMemberGroup) group);
                }
            });
        }

        DefaultMember updatedMember = memberRepository.save(existingMember);
        return updatedMember;
    }

    @Override
    public void deleteMember(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMember'");
    }

    @Override
    public Optional<Member> findMemberByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findMemberByUsername'");
    }

    @Override
    public Role createRole(Role role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRole'");
    }

    @Override
    public void assignRoleToMember(String username, String roleName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assignRoleToMember'");
    }

    @Override
    public Group createGroup(Group group) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createGroup'");
    }

    @Override
    public void addMemberToGroup(String username, String groupName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMemberToGroup'");
    }

    @Override
    public void addRoleToGroup(String roleName, String groupName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRoleToGroup'");
    }
}
