package com.authsphere.authhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authsphere.authhub.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
