package com.bombi.core.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bombi.core.domain.member.model.MemberInfo;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long>, MemberRepositoryCustom {


}
