package com.bombi.core.domain.category.model;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
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

	@Column(columnDefinition = "VARCHAR(10) NOT NULL")
	@Comment("카테고리 코드")
	private String code;

	@Column(columnDefinition = "VARCHAR(40) NOT NULL")
	@Comment("카테고리 이름")
	private String name;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "category_id")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> children = new ArrayList<>();

	@Builder
	private Category(Long level, String code, String name, Category parent) {
		this.level = level;
		this.code = code;
		this.name = name;
		this.parent = parent;
	}
}
