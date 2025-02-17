package com.bombi.auth.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bombi.auth.domain.auth.AuthEntity;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
}
