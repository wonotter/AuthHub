package com.authhub.domain.implementation.authorization;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

import com.authhub.domain.implementation.common.BaseTimeEntity;
import com.authhub.domain.interfaces.authorization.Permission;

import jakarta.persistence.*;


@Entity
@Table(name = "permission")
public class DefaultPermission extends BaseTimeEntity implements Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private long id;

    @Column(name = "permission_name", nullable = false, unique = true)
    private String permissionName;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DefaultRolePermission> rolePermissions;

    protected DefaultPermission() {}

    public DefaultPermission(String permissionName) {
        this.permissionName = permissionName;
        this.rolePermissions = new HashSet<>();
    }

    public void addRolePermission(DefaultRolePermission rolePermission) {
        this.rolePermissions.add(rolePermission);
        rolePermission.setPermission(this);
    }

    public void removeRolePermission(DefaultRolePermission rolePermission) {
        this.rolePermissions.remove(rolePermission);
        rolePermission.setPermission(null);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getAuthority() {
        return this.permissionName;
    }

    @Override
    public Set<DefaultRolePermission> getRolePermissions() {
        return this.rolePermissions;
    }

    public static PermissionBuilder builder() {
        return new PermissionBuilder();
    }

    public static final class PermissionBuilder {
        private String permissionName;
        private Set<DefaultRolePermission> rolePermissions;
        
        protected PermissionBuilder() {}

        public PermissionBuilder(String permissionName) {
            this.permissionName = permissionName;
            this.rolePermissions = new HashSet<>();
        }

        public PermissionBuilder name(String permissionName) {
            Assert.hasText(permissionName, "name cannot be empty");
            this.permissionName = permissionName;
            return this;
        }

        public PermissionBuilder rolePermissions(Set<DefaultRolePermission> rolePermissions) {
            Assert.notNull(rolePermissions, "rolePermissions cannot be null");
            rolePermissions.forEach(this::addRolePermission);
            return this;
        }

        public PermissionBuilder addRolePermission(DefaultRolePermission rolePermission) {
            Assert.notNull(rolePermission, "rolePermission cannot be null");
            this.rolePermissions.add(rolePermission);
            return this;
        }

        public DefaultPermission build() {
            DefaultPermission permission = new DefaultPermission(permissionName);
            rolePermissions.forEach(permission::addRolePermission);
            return permission;
        }
    }
}
