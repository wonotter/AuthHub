package com.authhub.domain.implementation.authorization;

import com.authhub.domain.implementation.authentication.DefaultMember;
import com.authhub.domain.implementation.common.BaseTimeEntity;
import com.authhub.domain.interfaces.authorization.MemberRole;

import jakarta.persistence.*;

@Entity
@Table(name = "member_role")
public class DefaultMemberRole extends BaseTimeEntity implements MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private DefaultMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private DefaultRole role;

    private DefaultMemberRole(DefaultMember member, DefaultRole role) {
        this.member = member;
        this.role = role;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public DefaultMember getMember() {
        return this.member;
    }

    @Override
    public DefaultRole getRole() {
        return this.role;
    }

    public void setMember(DefaultMember member) {
        this.member = member;
    }

    public void setRole(DefaultRole role) {
        this.role = role;
    }
}
