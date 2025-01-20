package com.authsphere.authhub.domain;

import java.util.HashSet;
import java.util.Set;

import com.authsphere.authhub.common.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "role")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberRole> userRoles;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePermission> rolePermissions;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupRole> groupRoles;

    public Role(String roleName) {
        this.roleName = roleName;
        this.userRoles = new HashSet<>();
        this.rolePermissions = new HashSet<>();
        this.groupRoles = new HashSet<>();
    }

    public void addMemberRole(MemberRole memberRole) {
        this.userRoles.add(memberRole);
        memberRole.setRole(this);
    }

    public void removeMemberRole(MemberRole memberRole) {
        this.userRoles.remove(memberRole);
        memberRole.setRole(null);
    }

    public void addRolePermission(RolePermission rolePermission) {
        this.rolePermissions.add(rolePermission);
        rolePermission.setRole(this);
    }

    public void removeRolePermission(RolePermission rolePermission) {
        this.rolePermissions.remove(rolePermission);
        rolePermission.setRole(null);
    }

    public void addGroupRole(GroupRole groupRole) {
        this.groupRoles.add(groupRole);
        groupRole.setRole(this);
    }

    public void removeGroupRole(GroupRole groupRole) {
        this.groupRoles.remove(groupRole);
        groupRole.setRole(null);
    }
}

