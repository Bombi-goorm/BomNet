package com.bombi.core.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class MemberRepositoryImpl {

	private final JPAQueryFactory queryFactory;

	public MemberRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}
}
