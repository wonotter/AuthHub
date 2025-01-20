package com.authsphere.authhub.domain;

import com.authsphere.authhub.common.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "group_role")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupRole extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public GroupRole(Group group, Role role) {
        this.group = group;
        this.role = role;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
