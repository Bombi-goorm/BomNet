package com.bombi.core.domain.notificationcondition.model;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;
import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.product.model.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationCondition extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_condition_id")
	private Long id;

	@Column(columnDefinition = "VARCHAR(50) NOT NULL")
	@Comment("지정가")
	private int targetPrice;

	@Column(columnDefinition = "VARCHAR(1) NOT NULL")
	@Comment("가격 방향성(U/D)")
	private int priceDirection;

	@Column(columnDefinition = "VARCHAR(1) NOT NULL")
	@Comment("알림 조건 활성화 여부")
	private String active;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	@Comment("멤버 ID")
	private Member member;

//	@ManyToOne(fetch = LAZY)
//	@JoinColumn(name = "product_id", columnDefinition = "BIGINT NOT NULL")
//	@Comment("작물 ID")
//	private Product product;

	// 가격알림용 임시속성
	private String category;
	private String item;
	private String variety;
	private String region;

	public boolean isActive() {
		return "T".equals(this.active);
	}
}
