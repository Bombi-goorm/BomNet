package com.bombi.core.domain.cultivation.model;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;
import com.bombi.core.domain.product.model.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cultivation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cultivation_id")
	private Long id;

	@Column(columnDefinition = "VARCHAR(255) NOT NULL")
	@Comment("재배 특정")
	private String cultivationFeatures;

	@Column(columnDefinition = "VARCHAR(255) NOT NULL")
	@Comment("재배 팁")
	private String cultivationTip;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "product_id", columnDefinition = "BIGINT NOT NULL")
	@Comment("작물 ID")
	private Product product;

}
