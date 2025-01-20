package com.authsphere.authhub.domain;

import com.authsphere.authhub.common.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "member_group")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public MemberGroup(Member member, Group group) {
        this.member = member;
        this.group = group;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
