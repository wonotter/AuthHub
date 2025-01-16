package com.authhub.domain.implementation.authorization;

import com.authhub.domain.implementation.common.BaseTimeEntity;
import com.authhub.domain.interfaces.authorization.RolePermission;

import jakarta.persistence.*;

@Entity
@Table(name = "role_permission")
public class DefaultRolePermission extends BaseTimeEntity implements RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_permission_id")
    private Long id;

    // role_permissions.role_id -> roles.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private DefaultRole role;

    // role_permissions.permission_id -> permissions.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private DefaultPermission permission;

    protected DefaultRolePermission() {}

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public DefaultRole getRole() {
        return this.role;
    }

    @Override
    public DefaultPermission getPermission() {
        return this.permission;
    }

    public void setRole(DefaultRole role) {
        this.role = role;
    }

    public void setPermission(DefaultPermission permission) {
        this.permission = permission;
    }
}
