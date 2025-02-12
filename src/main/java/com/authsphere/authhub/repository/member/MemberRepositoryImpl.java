package com.authsphere.authhub.repository.member;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.authsphere.authhub.domain.Member;
import com.authsphere.authhub.utils.QuerydslRepositorySupport;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

import static com.authsphere.authhub.domain.QMember.member;
import static com.authsphere.authhub.domain.QMemberRole.memberRole;
import static com.authsphere.authhub.domain.QMemberGroup.memberGroup;
import static com.authsphere.authhub.domain.QGroupRole.groupRole;
import static com.authsphere.authhub.domain.QRole.role;
import static com.authsphere.authhub.domain.QGroup.group;

@Repository

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements QueryDslMemberRepository {
    public MemberRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        super(Member.class, queryFactory, entityManager);
    }

    @Override
    public Optional<Member> findByEmailWithRolesAndGroups(String email) {
        return Optional.ofNullable(
            getQueryFactory()
                .selectFrom(member)
                .distinct()
                .leftJoin(member.roles, memberRole).fetchJoin()
                .leftJoin(memberRole.role, role).fetchJoin()
                .leftJoin(member.groups, memberGroup).fetchJoin()
                .leftJoin(memberGroup.group, group).fetchJoin()
                .leftJoin(group.groupRoles, groupRole).fetchJoin()
                .leftJoin(groupRole.role, role).fetchJoin()
                .where(member.email.eq(email))
                .fetchOne()
        );
    }
}
