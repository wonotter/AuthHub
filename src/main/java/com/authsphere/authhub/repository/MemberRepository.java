package com.authsphere.authhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authsphere.authhub.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByUsernameWithRolesAndGroups(String username);
}
