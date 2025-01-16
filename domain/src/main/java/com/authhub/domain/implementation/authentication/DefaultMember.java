package com.authhub.domain.implementation.authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import com.authhub.domain.implementation.authorization.DefaultGroup;
import com.authhub.domain.implementation.authorization.DefaultGroupRole;
import com.authhub.domain.implementation.authorization.DefaultMemberGroup;
import com.authhub.domain.implementation.authorization.DefaultMemberRole;
import com.authhub.domain.implementation.authorization.DefaultRole;
import com.authhub.domain.implementation.authorization.DefaultRolePermission;
import com.authhub.domain.implementation.common.BaseTimeEntity;
import com.authhub.domain.interfaces.authentication.Member;

import jakarta.persistence.*;


@Entity
@Table(name = "member")
public class DefaultMember extends BaseTimeEntity implements Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DefaultMemberRole> roles;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DefaultMemberGroup> groups;

    protected DefaultMember() {}

    public DefaultMember(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
        this.groups = new HashSet<>();
    }

    public void updateFrom(DefaultMember other) {
        // 비밀번호 업데이트
        if (other.getPassword() != null && !other.getPassword().isEmpty()) {
            this.setPassword(other.getPassword());
        }

        // 역할 업데이트
        if (other.getRoles() != null) {
            this.roles.clear();
            other.getRoles().forEach(role -> {
                if (role instanceof DefaultMemberRole) {
                    this.addMemberRole((DefaultMemberRole) role);
                }
            });
        }

        // 그룹 업데이트
        if (other.getGroups() != null) {
            this.groups.clear();
            other.getGroups().forEach(group -> {
                if (group instanceof DefaultMemberGroup) {
                    this.addMemberGroup((DefaultMemberGroup) group);
                }
            });
        }
    }

    public void addToRole(DefaultRole role) {
        DefaultMemberRole memberRole = new DefaultMemberRole(this, role);
        this.roles.add(memberRole);
        role.addMemberRole(memberRole);
    }

    public boolean hasGroup(String groupName) {
        return this.groups.stream()
            .map(DefaultMemberGroup::getGroup)
            .anyMatch(g -> g.getGroupName().equals(groupName));
    }

    public boolean hasRole(String roleName) {
        return this.roles.stream()
            .map(DefaultMemberRole::getRole)
            .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    public void addToGroup(DefaultGroup group) {
        DefaultMemberGroup memberGroup = new DefaultMemberGroup(this, group);
        this.groups.add(memberGroup);
        group.addMemberGroup(memberGroup);
    }

    public void addMemberRole(DefaultMemberRole role) {
        this.roles.add(role);
        role.setMember(this);
      }
    
      public void removeMemberRole(DefaultMemberRole role) {
        this.roles.remove(role);
        role.setMember(null);
      }
    
      public void addMemberGroup(DefaultMemberGroup group) {
        this.groups.add(group);
        group.setMember(this);
      }
    
      public void removeMemberGroup(DefaultMemberGroup group) {
        this.groups.remove(group);
        group.setMember(null);
      }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> authorities = new HashSet<>();

        //직접 할당된 Role 처리
        for (DefaultMemberRole memberRole : roles) {
            collectRoleAndPermission(memberRole.getRole(), authorities); // memberRole.getRole() 이 시점에서 추가 쿼리가 많이 발생할 수 있음..
        }

        // 그룹을 통해 할당된 Role 처리
        for(DefaultMemberGroup memberGroup : groups) {
            DefaultGroup group = memberGroup.getGroup();
            for(DefaultGroupRole groupRole : group.getGroupRoles()) {
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
    private void collectRoleAndPermission(DefaultRole role, Set<String> authorities) {
        //ex) ROLE_ADMIN 등
        authorities.add("ROLE_" + role.getRoleName());
        // Role에 속한 Permission들도 추가
        for(DefaultRolePermission rolePermission : role.getRolePermissions()) {
            // Permission은 그대로 권한 문자열로 사용
            authorities.add(rolePermission.getPermission().getAuthority());
        }
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Set<DefaultMemberRole> getRoles() {
        return this.roles;
    }

    @Override
    public Set<DefaultMemberGroup> getGroups() {
        return this.groups;
    }

    /**
     * 새로운 MemberBuilder 인스턴스를 생성합니다.
     */
    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Member 객체 생성을 위한 빌더 클래스
     * 복잡한 Member 객체의 생성 프로세스를 단순화하고 가독성을 향상
     */
    public static final class MemberBuilder {
        private String username;
        private String password;
        private Set<DefaultMemberRole> roles;
        private Set<DefaultMemberGroup> groups;

        /**
         * 기본 생성자
         */
        private MemberBuilder() {}

        public MemberBuilder(String username, String password) {
            this.username = username;
            this.password = password;
            this.roles = new HashSet<>();
            this.groups = new HashSet<>();
        }

        /**
         * 사용자 이름을 설정합니다.
         *
         * @param username 사용자 이름 (필수)
         * @return MemberBuilder 객체
         */
        public MemberBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        /**
         * 사용자 비밀번호를 설정합니다.
         *
         * @param password 사용자 비밀번호 (필수)
         * @return MemberBuilder 객체
         */
        public MemberBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }


        /**
         * 사용자 역할 ID 목록을 설정합니다.
         *
         * @param roleIds 역할 ID 목록
         * @return UserBuilder 객체
         */
        public MemberBuilder roles(Set<DefaultMemberRole> roles) {
            Assert.notNull(roles, "roles cannot be null");
            this.roles.clear();
            roles.forEach(this::addRole);
            return this;
        }

        /**
         * 사용자 역할 ID를 추가합니다.
         *
         * @param roleId 역할 ID
         * @return UserBuilder 객체
         */
        public MemberBuilder addRole(DefaultMemberRole role) {
            Assert.notNull(role, "role cannot be null");
            this.roles.add(role);
            return this;
        }

        /**
         * 사용자 그룹 ID 목록을 설정합니다.
         *
         * @param groupIds 그룹 ID 목록
         * @return UserBuilder 객체
         */
        public MemberBuilder groups(Set<DefaultMemberGroup> groups) {
            Assert.notNull(groups, "groups cannot be null");
            groups.forEach(this::addGroup);
            return this;
        }

        /**
         * 사용자 그룹 ID를 추가합니다.
         *
         * @param groupId 그룹 ID
         * @return UserBuilder 객체
         */
        public MemberBuilder addGroup(DefaultMemberGroup group) {
            Assert.notNull(group, "group cannot be null");
            this.groups.add(group);
            return this;
        }

        /**
         * Member 객체를 생성합니다.
         *
         * @return Member 객체
         * @throws IllegalStateException 필수 필드가 누락된 경우
         */
        public DefaultMember build() {
            DefaultMember member = new DefaultMember(username, password);
            roles.forEach(member::addMemberRole);
            groups.forEach(member::addMemberGroup);
            return member;
        }
    }
}
