package com.authsphere.authhub.domain;

import java.util.HashSet;
import java.util.Set;

import com.authsphere.authhub.common.BaseTimeEntity;
import com.authsphere.authhub.domain.oauth2.OAuth2UserInfo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "member")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberRole> roles;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberGroup> groups;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
        this.groups = new HashSet<>();
    }

    public void updateFrom(Member other) {
        // 비밀번호 업데이트
        if (other.getPassword() != null && !other.getPassword().isEmpty()) {
            this.setPassword(other.getPassword());
        }

        // 역할 업데이트
        if (other.getRoles() != null) {
            this.roles.clear();
            other.getRoles().forEach(role -> {
                if (role instanceof MemberRole) {
                    this.addMemberRole((MemberRole) role);
                }
            });
        }

        // 그룹 업데이트
        if (other.getGroups() != null) {
            this.groups.clear();
            other.getGroups().forEach(group -> {
                if (group instanceof MemberGroup) {
                    this.addMemberGroup((MemberGroup) group);
                }
            });
        }
    }

    public void addToRole(Role role) {
        MemberRole memberRole = new MemberRole(this, role);
        this.roles.add(memberRole);
        role.addMemberRole(memberRole);
    }

    public boolean hasGroup(String groupName) {
        return this.groups.stream()
            .map(MemberGroup::getGroup)
            .anyMatch(g -> g.getGroupName().equals(groupName));
    }

    public boolean hasRole(String roleName) {
        return this.roles.stream()
            .map(MemberRole::getRole)
            .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    public void addToGroup(Group group) {
        MemberGroup memberGroup = new MemberGroup(this, group);
        this.groups.add(memberGroup);
        group.addMemberGroup(memberGroup);
    }

    public void addMemberRole(MemberRole role) {
        this.roles.add(role);
        role.setMember(this);
    }

    public void removeMemberRole(MemberRole role) {
        this.roles.remove(role);
        role.setMember(null);
    }

    public void addMemberGroup(MemberGroup group) {
        this.groups.add(group);
        group.setMember(this);
    }

    public void removeMemberGroup(MemberGroup group) {
        this.groups.remove(group);
        group.setMember(null);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Member fromUserInfo(OAuth2UserInfo oAuth2UserInfo) {
        return new Member(oAuth2UserInfo.getName(), null);
    }

}
