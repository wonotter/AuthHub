package com.authhub.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authhub.domain.implementation.authorization.DefaultGroup;

public interface DefaultGroupRepository extends JpaRepository<DefaultGroup, Long> {
    Optional<DefaultGroup> findByGroupName(String groupName);
}
