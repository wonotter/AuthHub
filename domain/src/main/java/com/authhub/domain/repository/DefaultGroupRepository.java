package com.authhub.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authhub.domain.implementation.authorization.DefaultGroup;

public interface DefaultGroupRepository extends JpaRepository<DefaultGroup, Long> {
    
}
