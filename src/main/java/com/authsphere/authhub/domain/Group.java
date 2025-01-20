package com.authsphere.authhub.domain;

import java.util.HashSet;
import java.util.Set;

import com.authsphere.authhub.common.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "groups")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String groupName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberGroup> userGroups;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupRole> groupRoles;

    public Group(String groupName) {
        this.groupName = groupName;
        this.userGroups = new HashSet<>();
        this.groupRoles = new HashSet<>();
    }

    public boolean hasRole(String roleName) {
        return this.groupRoles.stream()
            .map(GroupRole::getRole)
            .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    public void addToRole(Role role) {
        GroupRole groupRole = new GroupRole(this, role);
        this.groupRoles.add(groupRole);
        role.addGroupRole(groupRole);
    }

    public void addMemberGroup(MemberGroup memberGroup) {
        this.userGroups.add(memberGroup);
        memberGroup.setGroup(this);
    }

    public void removeMemberGroup(MemberGroup memberGroup) {
        this.userGroups.remove(memberGroup);
        memberGroup.setGroup(null);
    }

    public void addGroupRole(GroupRole groupRole) {
        this.groupRoles.add(groupRole);
        groupRole.setGroup(this);
    }

    public void removeGroupRole(GroupRole groupRole) {
        this.groupRoles.remove(groupRole);
        groupRole.setGroup(null);
    }
}
