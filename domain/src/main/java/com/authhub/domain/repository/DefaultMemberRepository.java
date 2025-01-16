package com.authhub.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authhub.domain.implementation.authentication.DefaultMember;

public interface DefaultMemberRepository extends JpaRepository<DefaultMember, Long> {
    Optional<DefaultMember> findByUsername(String username);
}
