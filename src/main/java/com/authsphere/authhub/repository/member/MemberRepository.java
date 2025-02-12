package com.authsphere.authhub.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authsphere.authhub.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, QueryDslMemberRepository {
    Optional<Member> findByEmail(String email);
}
