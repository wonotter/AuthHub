package com.authsphere.authhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authsphere.authhub.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupName(String groupName);
}
