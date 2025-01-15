package com.authhub.domain.implementation.authorization;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

import com.authhub.domain.implementation.common.BaseTimeEntity;
import com.authhub.domain.interfaces.authorization.Group;

import jakarta.persistence.*;

@Entity
@Table(name = "group")
public class DefaultGroup extends BaseTimeEntity implements Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String groupName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DefaultMemberGroup> userGroups;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DefaultGroupRole> groupRoles;

    private DefaultGroup(String groupName) {
        this.groupName = groupName;
        this.userGroups = new HashSet<>();
        this.groupRoles = new HashSet<>();
    }

    public void addMemberGroup(DefaultMemberGroup memberGroup) {
        this.userGroups.add(memberGroup);
        memberGroup.setGroup(this);
    }

    public void removeMemberGroup(DefaultMemberGroup memberGroup) {
        this.userGroups.remove(memberGroup);
        memberGroup.setGroup(null);
    }

    public void addGroupRole(DefaultGroupRole groupRole) {
        this.groupRoles.add(groupRole);
        groupRole.setGroup(this);
    }

    public void removeGroupRole(DefaultGroupRole groupRole) {
        this.groupRoles.remove(groupRole);
        groupRole.setGroup(null);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    @Override
    public Set<DefaultMemberGroup> getMemberGroups() {
        return this.userGroups;
    }

    @Override
    public Set<DefaultGroupRole> getGroupRoles() {
        return this.groupRoles;
    }

    public static GroupBuilder builder() {
        return new GroupBuilder();
    }

    public static final class GroupBuilder {
        private String groupName;
        private Set<DefaultMemberGroup> userGroups;
        private Set<DefaultGroupRole> groupRoles;

        private GroupBuilder() {}

        public GroupBuilder(String groupName) {
            this.groupName = groupName;
            this.userGroups = new HashSet<>();
            this.groupRoles = new HashSet<>();
        }

        public GroupBuilder groupName(String groupName) {
            Assert.hasText(groupName, "name cannot be empty");
            this.groupName = groupName;
            return this;
        }

        public GroupBuilder userGroups(Set<DefaultMemberGroup> userGroups) {
            Assert.notNull(userGroups, "userGroups cannot be null");
            userGroups.forEach(this::addUserGroup);
            return this;
        }

        public GroupBuilder addUserGroup(DefaultMemberGroup userGroup) {
            Assert.notNull(userGroup, "userGroup cannot be null");
            this.userGroups.add(userGroup);
            return this;
        }

        public GroupBuilder groupRoles(Set<DefaultGroupRole> groupRoles) {
            Assert.notNull(groupRoles, "groupRoles cannot be null");
            groupRoles.forEach(this::addGroupRole);
            return this;
        }

        public GroupBuilder addGroupRole(DefaultGroupRole groupRole) {
            Assert.notNull(groupRole, "groupRole cannot be null");
            this.groupRoles.add(groupRole);
            return this;
        }

        public DefaultGroup build() {
            DefaultGroup group = new DefaultGroup(groupName);
            userGroups.forEach(group::addMemberGroup);
            groupRoles.forEach(group::addGroupRole);
            return group;
        }
    }
}
