package com.bombi.core.domain.product.model;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;
import com.bombi.core.domain.category.model.Category;
import com.bombi.core.domain.cultivation.model.Cultivation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	@Column(columnDefinition = "VARCHAR(255) NOT NULL")
	@Comment("작물 이미지 URL")
	private String imageUrl;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "category_id", columnDefinition = "BIGINT NOT NULL")
	@Comment("카테고리 ID")
	private Category category;

	@OneToOne(mappedBy = "product", fetch = LAZY)
	private Cultivation cultivation;

	@Builder
	private Product(String imageUrl, Category category) {
		this.imageUrl = imageUrl;
		this.category = category;
	}


}
