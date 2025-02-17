package com.bombi.core.domain.member.repository;


import com.bombi.core.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdAndDeleted(Long id, String deleted);

    Optional<Member> findByTel(String tel);

    Optional<Member> findByEmailAndTel(String email, String tel);
    Optional<Member> findByEmail(String email);

}
