package com.bombi.auth.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bombi.auth.domain.member.MemberRole;
import com.bombi.auth.domain.member.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(String memberRole);
}
