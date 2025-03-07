package com.bombi.core.domain.member.repository;


import com.bombi.core.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID>, MemberRepositoryCustom {

	Optional<Member> findMemberAndInfoById(UUID memberId);

}
