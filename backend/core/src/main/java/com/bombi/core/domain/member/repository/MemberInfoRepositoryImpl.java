package com.bombi.core.domain.member.repository;

import java.util.Optional;

import com.bombi.core.domain.member.model.MemberInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class MemberInfoRepositoryImpl implements MemberInfoRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public MemberInfoRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Optional<MemberInfo> findMemberInfoByMemberId() {

		return Optional.empty();
	}
}
