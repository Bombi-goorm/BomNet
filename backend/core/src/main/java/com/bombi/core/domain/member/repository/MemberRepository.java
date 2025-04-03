package com.bombi.core.domain.member.repository;


import com.bombi.core.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID>, MemberRepositoryCustom {

	@Query("select m from Member m join fetch m.memberInfo where m.id = :memberId")
	Optional<Member> findMemberAndInfoById(UUID memberId);

	@Query("select m from Member m join fetch m.role r where m.id = :id")
	Optional<Member> findMemberAndRoleById(UUID id);

}
