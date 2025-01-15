package com.authhub.domain.implementation.authorization;

import com.authhub.domain.implementation.authentication.DefaultMember;
import com.authhub.domain.implementation.common.BaseTimeEntity;
import com.authhub.domain.interfaces.authorization.MemberGroup;

import jakarta.persistence.*;

@Entity
@Table(name = "member_group")
public class DefaultMemberGroup extends BaseTimeEntity implements MemberGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private DefaultMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private DefaultGroup group;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public DefaultMember getMember() {
        return this.member;
    }

    @Override
    public DefaultGroup getGroup() {
        return this.group;
    }

    public void setMember(DefaultMember member) {
        this.member = member;
    }

    public void setGroup(DefaultGroup group) {
        this.group = group;
    }
}
