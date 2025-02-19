package com.bombi.auth.domain.member.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bombi.auth.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

	Optional<Member> findByAuthEmail(String authEmail);

	boolean existsByAuthEmail(String kakaoEmail);
}
