package com.authsphere.authhub.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> authorities = new HashSet<>();

        //직접 할당된 Role 처리
        for (MemberRole memberRole : roles) {
            collectRoleAndPermission(memberRole.getRole(), authorities); // memberRole.getRole() 이 시점에서 추가 쿼리가 많이 발생할 수 있음..
        }

        // 그룹을 통해 할당된 Role 처리
        for(MemberGroup memberGroup : groups) {
            Group group = memberGroup.getGroup();
            for(GroupRole groupRole : group.getGroupRoles()) {
                collectRoleAndPermission(groupRole.getRole(), authorities);
            }
        }

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    /**
     * 주어진 Role 및 해당 Role의 Permission들을 authorities에 추가
     * - 'ROLE_' prefix를 붙여서 RoleName을 추가
     * - Role이 가지는 Permission 권한들도 함께 추가
     */
    private void collectRoleAndPermission(Role role, Set<String> authorities) {
        //ex) ROLE_ADMIN 등
        authorities.add("ROLE_" + role.getRoleName());
        // Role에 속한 Permission들도 추가
        for(RolePermission rolePermission : role.getRolePermissions()) {
            // Permission은 그대로 권한 문자열로 사용
            authorities.add(rolePermission.getPermission().getPermissionName());
        }
    }

    public List<String> getAuthorityStrings() {
        return getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    }
}
