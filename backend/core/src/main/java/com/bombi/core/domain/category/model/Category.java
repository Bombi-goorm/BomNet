package com.bombi.core.domain.category.model;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;

	@Column(columnDefinition = "BIGINT NOT NULL")
	@Comment("카테고리 레벨")
	private Long level;

	@Column(columnDefinition = "VARCHAR(40) NOT NULL")
	@Comment("카테고리 이름")
	private String name;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "category_id")
	private Category parentCategory;
}
