package com.bombi.core.domain.member.repository;

import java.util.Optional;
import java.util.UUID;

import com.bombi.core.domain.member.model.MemberInfo;

public interface MemberInfoRepositoryCustom {

	MemberInfo findMemberInfoByMemberId(UUID memberId);
}
