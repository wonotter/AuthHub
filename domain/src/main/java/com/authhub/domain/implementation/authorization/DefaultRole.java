package com.authhub.domain.implementation.authorization;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

import com.authhub.domain.implementation.common.BaseTimeEntity;
import com.authhub.domain.interfaces.authorization.Role;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class DefaultRole extends BaseTimeEntity implements Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DefaultMemberRole> userRoles;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DefaultRolePermission> rolePermissions;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DefaultGroupRole> groupRoles;

    protected DefaultRole() {}

    public DefaultRole(String roleName) {
        this.roleName = roleName;
        this.userRoles = new HashSet<>();
        this.rolePermissions = new HashSet<>();
        this.groupRoles = new HashSet<>();
    }

    public void addMemberRole(DefaultMemberRole memberRole) {
        this.userRoles.add(memberRole);
        memberRole.setRole(this);
    }

    public void removeMemberRole(DefaultMemberRole memberRole) {
        this.userRoles.remove(memberRole);
        memberRole.setRole(null);
    }

    public void addRolePermission(DefaultRolePermission rolePermission) {
        this.rolePermissions.add(rolePermission);
        rolePermission.setRole(this);
    }

    public void removeRolePermission(DefaultRolePermission rolePermission) {
        this.rolePermissions.remove(rolePermission);
        rolePermission.setRole(null);
    }

    public void addGroupRole(DefaultGroupRole groupRole) {
        this.groupRoles.add(groupRole);
        groupRole.setRole(this);
    }

    public void removeGroupRole(DefaultGroupRole groupRole) {
        this.groupRoles.remove(groupRole);
        groupRole.setRole(null);
    }


    @Override
    public String getAuthority() {
        return this.roleName;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getRoleName() {
        return this.roleName;
    }

    @Override
    public Set<DefaultMemberRole> getUserRoles() {
        return this.userRoles;
    }

    @Override
    public Set<DefaultRolePermission> getRolePermissions() {
        return this.rolePermissions;
    }

    @Override
    public Set<DefaultGroupRole> getGroupRoles() {
        return this.groupRoles;
    }

    public static RoleBuilder builder() {
        return new RoleBuilder();
    }

    public static final class RoleBuilder {
        private String roleName;
        private Set<DefaultMemberRole> userRoles;
        private Set<DefaultRolePermission> rolePermissions;
        private Set<DefaultGroupRole> groupRoles;

        protected RoleBuilder() {}

        public RoleBuilder(String roleName) {
            this.roleName = roleName;
            this.userRoles = new HashSet<>();
            this.rolePermissions = new HashSet<>();
            this.groupRoles = new HashSet<>();
        }

        public RoleBuilder roleName(String roleName) {
            Assert.hasText(roleName, "name cannot be empty");
            this.roleName = roleName;
            return this;
        }
        
        public RoleBuilder userRoles(Set<DefaultMemberRole> userRoles) {
            Assert.notNull(userRoles, "userRoles cannot be null");
            userRoles.forEach(this::addUserRole);
            return this;
        }

        public RoleBuilder addUserRole(DefaultMemberRole userRole) {
            Assert.notNull(userRole, "userRole cannot be null");
            this.userRoles.add(userRole);
            return this;
        }

        public RoleBuilder rolePermissions(Set<DefaultRolePermission> rolePermissions) {
            Assert.notNull(rolePermissions, "rolePermissions cannot be null");
            rolePermissions.forEach(this::addRolePermission);
            return this;
        }

        public RoleBuilder addRolePermission(DefaultRolePermission rolePermission) {
            Assert.notNull(rolePermission, "rolePermission cannot be null");
            this.rolePermissions.add(rolePermission);
            return this;
        }

        public RoleBuilder groupRoles(Set<DefaultGroupRole> groupRoles) {
            Assert.notNull(groupRoles, "groupRoles cannot be null");
            groupRoles.forEach(this::addGroupRole);
            return this;
        }
        
        public RoleBuilder addGroupRole(DefaultGroupRole groupRole) {
            Assert.notNull(groupRole, "groupRole cannot be null");
            this.groupRoles.add(groupRole);
            return this;
        }

        public DefaultRole build() {
            DefaultRole role = new DefaultRole(roleName);
            userRoles.forEach(role::addMemberRole);
            rolePermissions.forEach(role::addRolePermission);
            groupRoles.forEach(role::addGroupRole);
            return role;
        }
    }
}
