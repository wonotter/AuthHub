package com.authsphere.authhub.domain;

import java.util.HashSet;
import java.util.Set;

import com.authsphere.authhub.common.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "permission")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Permission extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long id;

    @Column(name = "permission_name", nullable = false, unique = true)
    private String permissionName;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePermission> rolePermissions;

    public Permission(String permissionName) {
        this.permissionName = permissionName;
        this.rolePermissions = new HashSet<>();
    }

    public void addRolePermission(RolePermission rolePermission) {
        this.rolePermissions.add(rolePermission);
        rolePermission.setPermission(this);
    }

    public void removeRolePermission(RolePermission rolePermission) {
        this.rolePermissions.remove(rolePermission);
        rolePermission.setPermission(null);
    }
}

