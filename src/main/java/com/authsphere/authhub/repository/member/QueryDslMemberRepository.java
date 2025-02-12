package com.authsphere.authhub.repository.member;

import java.util.Optional;

import com.authsphere.authhub.domain.Member;

public interface QueryDslMemberRepository {
    // 이메일로 사용자의 권한과 그룹 정보 조회
    Optional<Member> findByEmailWithRolesAndGroups(String email);
}
