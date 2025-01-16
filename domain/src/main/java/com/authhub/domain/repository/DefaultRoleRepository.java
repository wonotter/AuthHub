package com.authhub.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authhub.domain.implementation.authorization.DefaultRole;

public interface DefaultRoleRepository extends JpaRepository<DefaultRole, Long> {
    Optional<DefaultRole> findByRoleName(String roleName);
}
