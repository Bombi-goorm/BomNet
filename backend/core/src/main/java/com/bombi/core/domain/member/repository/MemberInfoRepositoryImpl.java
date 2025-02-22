package com.bombi.core.domain.member.repository;

import static com.bombi.core.domain.member.model.QMember.*;
import static com.bombi.core.domain.member.model.QMemberInfo.*;

import java.util.Optional;
import java.util.UUID;

import com.bombi.core.domain.member.model.MemberInfo;
import com.bombi.core.domain.member.model.QMember;
import com.bombi.core.domain.member.model.QMemberInfo;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class MemberInfoRepositoryImpl implements MemberInfoRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public MemberInfoRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public MemberInfo findMemberInfoByMemberId(UUID memberId) {
		return queryFactory
			.selectFrom(QMemberInfo.memberInfo)
			.join(QMemberInfo.memberInfo.member, member).fetchJoin()
			.where(member.id.eq(memberId))
			.fetchOne();
	}
}
