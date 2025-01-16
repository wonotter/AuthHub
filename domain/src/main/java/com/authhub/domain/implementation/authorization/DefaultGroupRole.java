package com.authhub.domain.implementation.authorization;

import com.authhub.domain.implementation.common.BaseTimeEntity;
import com.authhub.domain.interfaces.authorization.GroupRole;

import jakarta.persistence.*;

@Entity
@Table(name = "group_role")
public class DefaultGroupRole extends BaseTimeEntity implements GroupRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private DefaultGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private DefaultRole role;

    protected DefaultGroupRole() {}

    public DefaultGroupRole(DefaultGroup group, DefaultRole role) {
        this.group = group;
        this.role = role;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public DefaultGroup getGroup() {
        return this.group;
    }

    @Override
    public DefaultRole getRole() {
        return this.role;
    }

    public void setGroup(DefaultGroup group) {
        this.group = group;
    }

    public void setRole(DefaultRole role) {
        this.role = role;
    }
}
