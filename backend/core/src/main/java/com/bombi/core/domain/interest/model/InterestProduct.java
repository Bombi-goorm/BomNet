package com.bombi.core.domain.interest.model;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;
import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.product.model.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestProduct extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interest_product_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id", columnDefinition = "VARCHAR(40) NOT NULL")
	@Comment("멤버 ID")
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id", columnDefinition = "BIGINT NOT NULL")
	@Comment("작물 ID")
	private Product product;

	public InterestProduct(Member member, Product product) {
		this.member = member;
		this.product = product;
	}
}
