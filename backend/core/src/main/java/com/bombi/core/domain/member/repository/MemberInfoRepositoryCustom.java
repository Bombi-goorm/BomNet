package com.bombi.core.domain.member.repository;

import java.util.Optional;

import com.bombi.core.domain.member.model.MemberInfo;

public interface MemberInfoRepositoryCustom {

	Optional<MemberInfo> findMemberInfoByMemberId();
}
